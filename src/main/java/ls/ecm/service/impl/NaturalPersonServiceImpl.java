package ls.ecm.service.impl;


import ls.ecm.dao.ExtraInfoDao;
import ls.ecm.dao.NaturalPersonDao;
import ls.ecm.exception.ErrorCode;
import ls.ecm.exception.ServiceException;
import ls.ecm.model.ExtraInfo;
import ls.ecm.model.db.UserRecord;
import ls.ecm.model.enums.NaturalPersonStatus;
import ls.ecm.model.req.BankRecord;
import ls.ecm.model.req.RegisterReq;
import ls.ecm.model.req.SignInReq;
import ls.ecm.model.vo.ProfileVO;
import ls.ecm.model.vo.UserVO;
import ls.ecm.service.NaturalPersonService;
import ls.ecm.service.RedisService;
import ls.ecm.utils.JWTUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

@Component
public class NaturalPersonServiceImpl implements NaturalPersonService {

    @Resource
    private NaturalPersonDao naturalPersonDao;

    @Resource
    private ExtraInfoDao extraInfoDao;

    @Resource
    private RedisService redisService;

    @Override
    public UserVO register(RegisterReq registerReq) throws ServiceException {
        redisService.checkSMSCode(registerReq.getChannelId(), registerReq.getPhone(), registerReq.getCode());
        UserRecord userRecord = naturalPersonDao.getByPhone(registerReq.getPhone(), registerReq.getChannelId());

        if (userRecord == null) {
            userRecord = naturalPersonDao.add(registerReq.getPhone(), registerReq.getPassword(), registerReq.getChannelId());
        } else if (userRecord.getStatus() != NaturalPersonStatus.PENDING && userRecord.getPassword() != null) {
            // 已经注册过
            throw new ServiceException(ErrorCode.DuplicateException);
        } else {
            naturalPersonDao.updateLoginPassword(userRecord.getId(),
                    userRecord.getSalt(), registerReq.getPassword());
        }

        naturalPersonDao.updateNaturalPersonStatus(userRecord.getId(), NaturalPersonStatus.DONE);
        extraInfoDao.initNaturalPersonExtraInfo(userRecord.getId());

        UserVO userVO = new UserVO();
        userVO.setPhone(userRecord.getPhone());
        userVO.setNaturalPersonId(userRecord.getId());
        userVO.setToken(JWTUtil.generateToken(userRecord.getId(), registerReq.getChannelId()));
        return userVO;
    }

    @Override
    public UserVO signIn(SignInReq signInReq) throws ServiceException {
        if (signInReq.getChannelId() == null) {
            throw new ServiceException(ErrorCode.ChannelIdNotFoudnException);
        }
        UserRecord userEntity = naturalPersonDao.checkUser(signInReq.getPhone(), signInReq.getPassword(), signInReq.getChannelId());
        UserVO userVO = new UserVO();
        userVO.setPhone(userEntity.getPhone());
        userVO.setNaturalPersonId(userEntity.getId());
        userVO.setToken(JWTUtil.generateToken(userEntity.getId(), signInReq.getChannelId()));
        return userVO;
    }

    @Override
    public ProfileVO getProfile(String token) throws ServiceException {
        long naturalPersonId = JWTUtil.getNaturalPersonId(token);
        return getProfileById(naturalPersonId);
    }

    private ProfileVO getProfileById(long naturalPersonId) throws ServiceException {
        UserRecord userRecord = naturalPersonDao.getById(naturalPersonId);
        //BankRecord bankRecord = bankDao.getBankById(userRecord.getBankId());
        ExtraInfo extraInfo = extraInfoDao.getById(naturalPersonId);

        ProfileVO profileVO = new ProfileVO();
        BeanUtils.copyProperties(userRecord, profileVO);
        //BeanUtils.copyProperties(bankRecord, profileVO);
        profileVO.setNaturalPersonId(userRecord.getId());
        profileVO.setAddr(extraInfo.getAddress());
        //profileVO.setBankId(bankRecord.getId());

        return Optional.ofNullable(profileVO).orElseThrow(() -> new ServiceException(ErrorCode.UserNotFoundException));
    }


}
