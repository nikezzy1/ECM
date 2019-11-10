package com.example.demo.controller;

import com.example.demo.core.ret.RetResult;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.req.RegisterReq;
import com.example.demo.model.vo.ProfileVO;
import com.example.demo.model.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

public class NaturalPersonController {

    @Autowired
    private NaturalPersonService naturalPersonService;

    // 注册
    @RequestMapping("/register")
    public RetResult<UserVO> register(@RequestBody RegisterReq registerReq) throws ServiceException {
        ProfileVO profileVO = new ProfileVO();
        BeanUtils.copyProperties(registerReq, profileVO);
        try {
            UserVO userVO = naturalPersonService.register(registerReq);
            reqLogService.logRegisterNaturalPerson(registerReq.getPhone(), ReqLogStatus.SUCCESS, registerReq.getChannelId());
            return new HTTPResponse<>(new HTTPStatus(), Pages.ROLE_PAGE, userVO);
        } catch (ServiceException e) {
            reqLogService.logRegisterNaturalPerson(registerReq.getPhone(), ReqLogStatus.FAILED, registerReq.getChannelId());
            if (e.getCode() != ErrorCode.DuplicateException.getCode()) {
                return new HTTPResponse<>(new HTTPStatus(e), Pages.REGISTER_PAGE, null);
            }
            return new HTTPResponse<>(new HTTPStatus(e), Pages.SIGN_IN_PAGE, null);
        }
    }
}
