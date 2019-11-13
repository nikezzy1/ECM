package ls.ecm.service.impl;


import com.lingsi.platform.identity.dto.IdCardDetectReq;
import com.lingsi.platform.identity.dto.IdCardDetectResp;
import com.lingsi.platform.identity.service.IdentityService;
import com.lingsi.platform.identity.service.impl.IdentityServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ls.ecm.constants.EcmConstants;
import ls.ecm.dao.ExtraInfoDao;
import ls.ecm.dao.NaturalPersonDao;
import ls.ecm.dao.PrivilegeDao;
import ls.ecm.exception.ErrorCode;
import ls.ecm.exception.ServiceException;
import ls.ecm.model.ExtraInfo;
import ls.ecm.model.Rights;
import ls.ecm.model.company.CompanyVO;
import ls.ecm.model.db.UserRecord;
import ls.ecm.model.enums.AuditFlowRole;
import ls.ecm.model.enums.NaturalPersonStatus;
import ls.ecm.model.req.*;
import ls.ecm.model.vo.ProfileVO;
import ls.ecm.model.vo.UploadEmblemVO;
import ls.ecm.model.vo.UploadPortraitVO;
import ls.ecm.model.vo.UserVO;
import ls.ecm.service.NaturalPersonService;
import ls.ecm.service.RedisService;
import ls.ecm.service.WechatService;
import ls.ecm.utils.EncryptUtil;
import ls.ecm.utils.JWTUtil;
import ls.ecm.utils.OSSUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class NaturalPersonServiceImpl implements NaturalPersonService {

    @Resource
    private NaturalPersonDao naturalPersonDao;

    @Resource
    private ExtraInfoDao extraInfoDao;

    @Resource
    private RedisService redisService;

    @Resource
    private WechatService wechatService;

    @Resource
    private NaturalPersonService naturalPersonService;

    @Resource
    private PrivilegeDao privilegeDao;

    IdentityService identityService = new IdentityServiceImpl();

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

//    @Override
//    public ProfileVO getProfile(String token) throws ServiceException {
//        long naturalPersonId = JWTUtil.getNaturalPersonId(token);
//        return getProfileById(naturalPersonId);
//    }
    @Override
    public ProfileVO getProfile() throws ServiceException {
        long naturalPersonId = 2L;
        return getProfileById(naturalPersonId);
    }

    @Override
    public ProfileVO getProfile(long naturalPersonId) throws ServiceException {
        return getProfileById(naturalPersonId);
    }

    private ProfileVO getProfileById(long naturalPersonId) throws ServiceException {
        UserRecord userRecord = naturalPersonDao.getById(naturalPersonId);
        ExtraInfo extraInfo = extraInfoDao.getById(naturalPersonId);

        ProfileVO profileVO = new ProfileVO();
        BeanUtils.copyProperties(userRecord, profileVO);
        profileVO.setNaturalPersonId(userRecord.getId());
        profileVO.setAddr(extraInfo.getAddress());

        return Optional.ofNullable(profileVO).orElseThrow(() -> new ServiceException(ErrorCode.UserNotFoundException));
    }

    @Override
    public UploadEmblemVO uploadEmblem(long naturalPersonId, UploadEmblemReq uploadEmblemReq) throws ServiceException {
        IdCardDetectReq idCardDetectReq=new IdCardDetectReq();
        idCardDetectReq.setCardType(EcmConstants.EMBLEM);
        idCardDetectReq.setFileContent(uploadEmblemReq.getEmblem());
        try {
            //调用ecm_platform身份证检测
            IdCardDetectResp idCardDetectResp = identityService.detectIdCard(idCardDetectReq);

            UploadEmblemVO uploadEmblemVO = new UploadEmblemVO();
            uploadEmblemVO.setValidDate(idCardDetectResp.getValidDate());
            uploadEmblemVO.setAuthority(idCardDetectResp.getAuthority());
            String fileName = OSSUtil.uploadIdCard(uploadEmblemReq.getEmblem());
            extraInfoDao.updateEmblem(naturalPersonId, fileName);
            return uploadEmblemVO;
        } catch (Exception e){
            e.getStackTrace();
            throw new ServiceException(ErrorCode.WechatRecognizeException);
        }

    }

    @Override
    public UploadPortraitVO uploadPortrait(long naturalPersonId, UploadPortraitReq uploadPortraitReq) throws ServiceException {
        IdCardDetectReq idCardDetectReq=new IdCardDetectReq();
        idCardDetectReq.setCardType(EcmConstants.PORTRAIT);
        idCardDetectReq.setFileContent(uploadPortraitReq.getPortrait());

        try {
            //调用ecm_platform身份证检测
            IdCardDetectResp idCardDetectResp = identityService.detectIdCard(idCardDetectReq);

            UploadPortraitVO uploadPortraitVO = new UploadPortraitVO();
            BeanUtils.copyProperties(idCardDetectResp, uploadPortraitVO);

            String fileName = OSSUtil.uploadIdCard(uploadPortraitReq.getPortrait());
            extraInfoDao.updatePortrait(naturalPersonId, fileName);
            return uploadPortraitVO;
        }catch (Exception e){
            e.getStackTrace();
            throw new ServiceException(ErrorCode.WechatRecognizeException);
        }
    }

    @Override
    public void confirmIdCardInfo(long naturalPersonId, WechatCardReq card) throws ServiceException {
        ProfileVO profileVO = naturalPersonService.getProfile();
        log.info("  "+"nature_person.id  ："+profileVO.getNaturalPersonId()+"  "+"正在确认其身份证信息 \n");
        UserRecord userRecord = naturalPersonDao.getById(naturalPersonId);
        UserRecord userEntity = naturalPersonDao.getByIdCardNumber(card.getIdCardNumber(), userRecord.getChannelId());
        if (userEntity != null && !userEntity.getPhone().equals(userRecord.getPhone())) {
            throw new ServiceException(ErrorCode.ExistIdCardException.getCode(), String.format("该身份证已经被手机号%s注册使用，如有问题，请联系客服。", userEntity.getPhone()));
        }
        naturalPersonDao.updateIdCardInfo(naturalPersonId, card.getName(), card.getIdCardNumber());
        extraInfoDao.updateIdCardInfo(naturalPersonId, card);
    }

    @Override
    public List<CompanyVO> getRelatedCompanies(long naturalPersonId) throws ServiceException {
        return privilegeDao.getRelatedCompanies(naturalPersonId);
    }

    @Override
    public void setForgetLoginPassword(long naturalPersonId, PasswordReq passwordReq) throws ServiceException {
        UserRecord userEntity = naturalPersonDao.getById(naturalPersonId);
        if (check(passwordReq.getOldPassword() + userEntity.getSalt(), userEntity.getPassword())) {
            naturalPersonDao.updateLoginPassword(naturalPersonId,
                    userEntity.getSalt(), passwordReq.getNewPassword());
        } else {
            throw new ServiceException(ErrorCode.PasswordErrorException);
        }
    }
    private boolean check(String rawPassword, String encryptedPassword) {
        return EncryptUtil.SHA256(rawPassword).equals(encryptedPassword);
    }

    @Override
    public void setLoginPassword(long naturalPersonId, String password) throws ServiceException {
        UserRecord userEntity = naturalPersonDao.getById(naturalPersonId);
        naturalPersonDao.updateLoginPassword(naturalPersonId,
                userEntity.getSalt(), password);
    }

    @Override
    public void setAuditFlowRole(long naturalPersonId, int auditFlowRole) throws ServiceException {
        naturalPersonDao.updateAuditFlowRole(naturalPersonId, auditFlowRole);
        if (auditFlowRole == AuditFlowRole.NaturalPerson.getCode()) {
            privilegeDao.addPrivilege(naturalPersonId, 0L, Rights.PRE_NATURAL_PERSON.getDescription(), Rights.PRE_NATURAL_PERSON);
        }
    }


}
