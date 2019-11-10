package com.example.demo.service;

import com.example.demo.exception.ServiceException;
import com.example.demo.model.req.RegisterReq;
import com.example.demo.model.req.SignInReq;
import com.example.demo.model.vo.UserVO;

public interface NaturalPersonService {
   // UserVO signIn(SignInReq signInReq) throws ServiceException;

    // 检查是否已经有有权操作的企业
   // boolean checkCredit(long naturalPersonId) throws ServiceException;

    UserVO register(RegisterReq registerReq) throws ServiceException;

}
