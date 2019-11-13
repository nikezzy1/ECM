package ls.ecm.controller;


import ls.ecm.core.ret.RetResponse;
import ls.ecm.core.ret.RetResult;
import ls.ecm.model.TestUserInfo;
import ls.ecm.service.UserInfoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("userInfo")
public class TestUserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @PostMapping("/hello")
    public String hello(){
        return "hello SpringBoot";
    }

    @PostMapping("/selectById")
    public RetResult<TestUserInfo> selectById(Integer id){
        TestUserInfo testUserInfo = userInfoService.selectById(id);
        return RetResponse.makeOKRsp(testUserInfo);
    }

    @PostMapping("/testException")
    public RetResult<TestUserInfo> testException(Integer id){
        List a = null;
        a.size();
        TestUserInfo testUserInfo = userInfoService.selectById(id);
        return RetResponse.makeOKRsp(testUserInfo);
    }

}
