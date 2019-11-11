package ls.ecm.controller;

import ls.ecm.core.response.CommonRes;
import ls.ecm.exception.ServiceException;
import ls.ecm.model.req.RegisterReq;
import ls.ecm.model.req.SignInReq;
import ls.ecm.model.vo.ProfileVO;
import ls.ecm.model.vo.UserVO;
import ls.ecm.service.NaturalPersonService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NaturalPersonController {

    @Autowired
    private NaturalPersonService naturalPersonService;

    // 注册
    @RequestMapping("/register")
    public CommonRes register(@RequestBody RegisterReq registerReq) throws ServiceException {
        ProfileVO profileVO = new ProfileVO();
        BeanUtils.copyProperties(registerReq, profileVO);
        try {
            UserVO userVO = naturalPersonService.register(registerReq);
            return CommonRes.http_ok("注册成功",userVO).success();
        } catch (ServiceException e) {
            e.printStackTrace();
                return CommonRes.http_error("注册失败"+e.getMessage()).fail();
        }
    }

    // 登陆
    @RequestMapping("/signIn")
    public CommonRes signIn(@RequestBody SignInReq signInReq) {
        try {
            UserVO userVO = naturalPersonService.signIn(signInReq);
            return CommonRes.http_ok("登陆成功",userVO).success();
        } catch (ServiceException e) {
            e.printStackTrace();
            return CommonRes.http_error("登陆失败"+e.getMessage()).fail();
        }
    }

    // 获取个人信息
    @RequestMapping("/profile")
    public CommonRes profile(@RequestHeader String token) {
        try {
            ProfileVO profileVO = naturalPersonService.getProfile(token);
            return CommonRes.http_ok("获取个人信息成功",profileVO).success();
        } catch (ServiceException e) {
            e.printStackTrace();
            return CommonRes.http_error("获取个人信息失败"+e.getMessage()).fail();
        }
    }

}
