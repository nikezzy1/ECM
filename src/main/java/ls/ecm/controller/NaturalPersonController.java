package ls.ecm.controller;

import com.lingsi.platform.identity.service.IdentityService;
import com.lingsi.platform.identity.service.impl.IdentityServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ls.ecm.core.response.CommonRes;
import ls.ecm.exception.ServiceException;
import ls.ecm.model.Rights;
import ls.ecm.model.req.RoleReq;
import ls.ecm.model.company.CompanyVO;
import ls.ecm.model.company.FinBody;
import ls.ecm.model.req.*;
import ls.ecm.model.vo.ProfileVO;
import ls.ecm.model.vo.UploadEmblemVO;
import ls.ecm.model.vo.UploadPortraitVO;
import ls.ecm.model.vo.UserVO;
import ls.ecm.model.wechat.WechatAlivenessDetectReq;
import ls.ecm.model.wechat.WechatLip;
import ls.ecm.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class NaturalPersonController {

    @Autowired
    private NaturalPersonService naturalPersonService;

    @Autowired
    private WechatService wechatService;

    @Autowired
    private personIdentityService personIdentityService;

    @Autowired
    private RedisService redisService;

    IdentityService identityService=new IdentityServiceImpl();



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
    public CommonRes profile() {
        try {
            ProfileVO profileVO = naturalPersonService.getProfile();
            return CommonRes.http_ok("获取个人信息成功",profileVO).success();
        } catch (ServiceException e) {
            e.printStackTrace();
            return CommonRes.http_error("获取个人信息失败"+e.getMessage()).fail();
        }
    }

    // 上传国徽面身份证信息
    @RequestMapping("/uploadEmblem")
    public CommonRes uploadEmblem( @RequestBody UploadEmblemReq uploadEmblemReq) {
        try {
            ProfileVO profileVO = naturalPersonService.getProfile();
            UploadEmblemVO uploadEmblemVO = naturalPersonService.uploadEmblem(profileVO.getNaturalPersonId(),
                    uploadEmblemReq);
            return CommonRes.http_ok("上传国徽面成功",uploadEmblemVO).success();
        } catch (ServiceException e) {
            e.getStackTrace();
            return CommonRes.http_error("上传国徽面失败"+e.getMessage()).fail();
        }
    }

    // 上传人像面身份证信息
    @RequestMapping("/uploadPortrait")
    public CommonRes uploadPortrait(@RequestBody UploadPortraitReq uploadPortraitReq) {
        try {
            ProfileVO profileVO = naturalPersonService.getProfile();
            log.info("test获取个人信息:"+profileVO.getPhone());
            UploadPortraitVO uploadPortraitVO = naturalPersonService.uploadPortrait(profileVO.getNaturalPersonId(),
                    uploadPortraitReq);
            log.info("test获取身份证人像面信息:"+uploadPortraitVO.getIdcard());
            return CommonRes.http_ok("上传人像面成功",uploadPortraitVO).success();
        } catch (ServiceException e) {
            e.getStackTrace();
            return CommonRes.http_error("上传人像面失败"+e.getMessage()).fail();
        }
    }

    // 确认身份证信息
    @RequestMapping("/confirmIdCardInfo")
    public CommonRes confirmIdCardInfo(@RequestBody WechatCardReq wechatCardReq) throws ServiceException {
        ProfileVO profileVO = naturalPersonService.getProfile();
        try {
            naturalPersonService.confirmIdCardInfo(profileVO.getNaturalPersonId(), wechatCardReq);
            return CommonRes.http_ok("确认身份证信息成功",null).success();
        } catch (ServiceException e) {
            e.getStackTrace();
            return CommonRes.http_error("确认身份证信息失败"+e.getMessage()).fail();
        }
    }

    // 获取唇语
    @RequestMapping("/getLips")
    public CommonRes getLips() {
        try {
            ProfileVO profileVO = naturalPersonService.getProfile();
            log.info("个人信息:"+profileVO.getName()+profileVO.getIdCardNumber());
            WechatLip wechatLip = wechatService.getLips(profileVO.getName(), profileVO.getIdCardNumber());
            log.info("唇语豹纹:"+wechatLip.getSuccess());
            return CommonRes.http_ok("获取唇语成功",wechatLip).success();
        } catch (ServiceException e) {
            e.getStackTrace();
            return CommonRes.http_error("获取唇语失败"+e.getMessage()).fail();
        }
    }


    // 上传人脸识别视频
    @RequestMapping(value = "/uploadVideo", method = RequestMethod.POST)
    public CommonRes uploadVideo(@RequestBody WechatAlivenessDetectReq wechatAlivenessDetectReq) {
        try {
            ProfileVO profile = naturalPersonService.getProfile();
            wechatService.alivenessDetect(wechatAlivenessDetectReq.getOrderId(),
                    profile.getName(), profile.getIdCardNumber(), wechatAlivenessDetectReq.getVideoStr());
            List<CompanyVO> companies = naturalPersonService.getRelatedCompanies(profile.getNaturalPersonId());
            long bandingCompany = companies.stream().filter(companyVO -> companyVO.getRights() == Rights.PENDING.getCode()).count();
            if (bandingCompany > 0) {
                log.info("该用户暂未绑定公司");
            }
            return CommonRes.http_ok("上传人脸识别视频成功",null).success();
        } catch (ServiceException e) {
            e.getStackTrace();
            return CommonRes.http_error("上传人脸识别视频失败"+e.getMessage()).fail();
        }
    }

    // 设置登陆密码
    @RequestMapping("/setLoginPassword")
    public CommonRes setLoginPassword(@RequestBody PasswordReq passwordReq) {
        try {
            ProfileVO profileVO = naturalPersonService.getProfile();
            long naturalPersonId = personIdentityService.getNaturalPersonIdByPhone(profileVO.getPhone(), profileVO.getChannelId());
            naturalPersonService.setForgetLoginPassword(naturalPersonId, passwordReq);
            return CommonRes.http_ok("设置登陆密码成功",null).success();
        } catch (ServiceException e) {
            e.getStackTrace();
            return CommonRes.http_error("设置登陆密码失败"+e.getMessage()).fail();
        }
    }

    // 忘记登陆密码
    @RequestMapping("/forgetLoginPassword")
    public CommonRes forgetLoginPassword(@RequestBody ForgetPasswordReq forgetPasswordReq) {
        try {
            long naturalPersonId = personIdentityService.getNaturalPersonIdByPhone(forgetPasswordReq.getPhone(), forgetPasswordReq.getChannelId());
            redisService.checkSMSCode(forgetPasswordReq.getChannelId(), forgetPasswordReq.getPhone(), forgetPasswordReq.getSmsCode());

            naturalPersonService.setLoginPassword(naturalPersonId,forgetPasswordReq.getNewPassword());
            return CommonRes.http_ok("忘记登陆密码调用成功",null).success();
        } catch (ServiceException e) {
            e.getStackTrace();
            return CommonRes.http_error("忘记登陆密码接口异常"+e.getMessage()).fail();
        }
    }

    // 获取法人相关公司
    @RequestMapping("/relatedCompanies")
    public CommonRes relatedCompanies() {
        try {
            //long naturalPersonId = JWTUtil.getNaturalPersonId(token);
            long naturalPersonId = 1L;
            List<CompanyVO> companies = naturalPersonService.getRelatedCompanies(naturalPersonId);
            FinBody finBody = new FinBody();
            finBody.setRelatedCompanyVOList(companies);
            ProfileVO profileVO = naturalPersonService.getProfile(naturalPersonId);
            finBody.setProfile(profileVO);
            return CommonRes.http_ok("获取法人相关公司成功",finBody).success();
        } catch (ServiceException e) {
            e.getStackTrace();
            return CommonRes.http_error("获取法人相关公司失败"+e.getMessage()).fail();
        }
    }

    // 登陆的角色，比如个体工商户
    @RequestMapping("/role")
    public CommonRes role(@RequestBody RoleReq roleReq) {
        try {
            ProfileVO profileVO = naturalPersonService.getProfile();
            naturalPersonService.setAuditFlowRole(profileVO.getNaturalPersonId(), roleReq.getAuditFlowRole());
            return CommonRes.http_ok("获取登陆的角色成功").success();
        } catch (ServiceException e) {
            e.getStackTrace();
            return CommonRes.http_error("获取登陆的角色失败"+e.getMessage()).fail();
        }
    }




}
