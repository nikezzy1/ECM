package com.example.demo.service.impl;


import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.db.UserRecord;
import com.example.demo.model.req.RegisterReq;
import com.example.demo.model.vo.UserVO;
import com.example.demo.service.NaturalPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NaturalPersonServiceImpl implements NaturalPersonService {

//    @Autowired
//    private NaturalPersonDao naturalPersonDao;


    @Override
    public UserVO register(RegisterReq registerReq) throws ServiceException {
        //redisService.checkSMSCode(registerReq.getChannelId(), registerReq.getPhone(), registerReq.getCode());
//        UserRecord userRecord = naturalPersonDao.getByPhone(registerReq.getPhone(), registerReq.getChannelId());
//
//         if (userRecord == null) {
//           userRecord = naturalPersonDao.add(registerReq.getPhone(), registerReq.getPassword(), registerReq.getChannelId());
//        } else if (userRecord.getStatus() != NaturalPersonStatus.PENDING && userRecord.getPassword() != null) {
//            // 已经注册过
//           throw new ServiceException(ErrorCode.DuplicateException);
//       } else {
//            naturalPersonDao.updateLoginPassword(userRecord.getId(),
//                    userRecord.getSalt(), registerReq.getPassword());
//        }
//
//       naturalPersonDao.updateNaturalPersonStatus(userRecord.getId(), NaturalPersonStatus.DONE);
//        extraInfoDao.initNaturalPersonExtraInfo(userRecord.getId());

        UserVO userVO = new UserVO();
//        userVO.setPhone(userRecord.getPhone());
//        userVO.setNaturalPersonId(userRecord.getId());
//        userVO.setToken(JWTUtil.generateToken(userRecord.getId(), registerReq.getChannelId()));
        return userVO;
    }


}
