package com.example.demo.controller;

import com.example.demo.core.ret.RetResponse;
import com.example.demo.core.ret.RetResult;
import com.example.demo.exception.ServiceException;
import com.example.demo.model.req.RegisterReq;
import com.example.demo.model.vo.ProfileVO;
import com.example.demo.model.vo.UserVO;
import com.example.demo.service.NaturalPersonService;
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
            return RetResponse.makeOKRsp(userVO);
        } catch (ServiceException e) {
                return RetResponse.makeErrRsp("注册失败");
        }
    }
}
