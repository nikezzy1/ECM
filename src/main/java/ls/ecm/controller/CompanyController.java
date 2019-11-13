package ls.ecm.controller;

import com.lingsi.platform.ecm.dto.AddAccountEnterpriseReq;
import com.lingsi.platform.ecm.dto.AddAccountResp;
import com.lingsi.platform.ecm.model.EcmUser;
import com.lingsi.platform.ecm.service.EcmService;
import com.lingsi.platform.ecm.service.impl.EcmServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ls.ecm.core.response.CommonRes;
import ls.ecm.dao.NaturalPersonDao;
import ls.ecm.exception.ServiceException;
import ls.ecm.model.company.CompanyDetailReq;
import ls.ecm.model.db.CompanyRecord;
import ls.ecm.model.db.UserRecord;
import ls.ecm.model.vo.ProfileVO;
import ls.ecm.service.CompanyService;
import ls.ecm.service.NaturalPersonService;
import ls.ecm.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.soap.Name;

@Slf4j
@RestController
public class CompanyController {
    @Autowired
    private NaturalPersonService naturalPersonService;

    @Autowired
    private NaturalPersonDao naturalPersonDao;

    @Autowired
    private CompanyService companyService;

    EcmService ecmService = new EcmServiceImpl();
    //企业四要素检验
    @RequestMapping("/checkCompanyFactors")
    public CommonRes checkCompanyFactors(@RequestBody CompanyDetailReq companyDetailReq) throws ServiceException {
        try {
            ProfileVO profileVO = naturalPersonService.getProfile();
            UserRecord userRecord = naturalPersonDao.getById(profileVO.getNaturalPersonId());
            companyService.checkCompanyFactors(companyDetailReq.getCompanyName(), companyDetailReq.getLicenseNo(),
                    userRecord.getName(), userRecord.getIdCardNumber());
            return CommonRes.http_ok("企业四要素检验成功").success();
        } catch (ServiceException e) {
            e.getStackTrace();
            log.error("绑定新增公司异常: \n" + "堆栈信息 ："+ ExceptionUtil.getStackTrace(e));
            return CommonRes.http_error("企业四要素检验失败"+e.getMessage()).fail();
        }
    }


    // 手机用户绑定新增公司
    @RequestMapping("/phoneAddCompany")
    public CommonRes PhoneAddCompany(@RequestBody CompanyDetailReq companyDetailReq) throws ServiceException {
        try {
            ProfileVO profileVO = naturalPersonService.getProfile();
            CompanyRecord companyRecord = companyService.addCompany(profileVO.getNaturalPersonId(), companyDetailReq);

            log.info("绑定公司的信息："+companyRecord);
            AddAccountEnterpriseReq addAccountEnterpriseReq=new AddAccountEnterpriseReq();
            addAccountEnterpriseReq.setAddAuthCode(false);
            addAccountEnterpriseReq.setChannelId(companyDetailReq.getChannelId());
            //这里先默认绑定人为法人
            addAccountEnterpriseReq.setLegal(true);
            addAccountEnterpriseReq.getContacter().setIndCertNo(profileVO.getIdCardNumber());
            addAccountEnterpriseReq.getContacter().setIndEmail("1795009213@qq.com");
            addAccountEnterpriseReq.getContacter().setIndName(profileVO.getName());
            addAccountEnterpriseReq.getContacter().setIndPhone(profileVO.getPhone());

            addAccountEnterpriseReq.getEnterprise().setEntIdentNo(companyDetailReq.getLicenseNo());
            addAccountEnterpriseReq.getEnterprise().setEntName(companyDetailReq.getCompanyName());
            //企业号码为空时取 法人手机号
            addAccountEnterpriseReq.getEnterprise().setEntTel(profileVO.getPhone());

            // 向CFCA注册公司,电子签章开户
            AddAccountResp addAccountResp=ecmService.addAccountEnterprise(addAccountEnterpriseReq);

//            EcmUser ecmUser =new EcmUser();
//            companyService.updateCFCAUserId(companyRecord.getId(), ecmUser.getAccountId());

            if(addAccountResp.getSmsCheck().equals(false))
            {
                log.info("注册完成，不需要验证码");
            }else if(addAccountResp.getSmsCheck().equals(true))
            {
                log.info("注册需要验证码");
            }
            return CommonRes.http_ok("绑定新增公司成功",addAccountResp).success();
        } catch (Exception e) {
            e.getStackTrace();
            log.error("绑定新增公司异常: \n" + "堆栈信息 ："+ ExceptionUtil.getStackTrace(e));
            return CommonRes.http_error("绑定新增公司失败"+e.getMessage()).fail();
        }
    }

    // 手机用户绑定新增公司【跳过企业四要素】
    @RequestMapping("/phoneAddCompanyWhitoutFactor")
    public CommonRes PhoneAddCompanyWhitoutFactor(@RequestHeader String token, @RequestBody CompanyDetailReq companyDetailReq) throws ServiceException {
        try {
            ProfileVO profileVO = naturalPersonService.getProfile();
            CompanyRecord companyRecord = companyService.addCompanyWhitoutFactor(profileVO.getNaturalPersonId(), companyDetailReq);
            log.info("绑定公司的信息："+companyRecord);
            AddAccountEnterpriseReq addAccountEnterpriseReq=new AddAccountEnterpriseReq();
            addAccountEnterpriseReq.setAddAuthCode(false);
            addAccountEnterpriseReq.setChannelId(companyDetailReq.getChannelId());
            //这里先默认绑定人为法人
            addAccountEnterpriseReq.setLegal(true);
            addAccountEnterpriseReq.getContacter().setIndCertNo(profileVO.getIdCardNumber());
            addAccountEnterpriseReq.getContacter().setIndEmail("1795009213@qq.com");
            addAccountEnterpriseReq.getContacter().setIndName(profileVO.getName());
            addAccountEnterpriseReq.getContacter().setIndPhone(profileVO.getPhone());

            addAccountEnterpriseReq.getEnterprise().setEntIdentNo(companyDetailReq.getLicenseNo());
            addAccountEnterpriseReq.getEnterprise().setEntName(companyDetailReq.getCompanyName());
            //企业号码为空时取 法人手机号
            addAccountEnterpriseReq.getEnterprise().setEntTel(profileVO.getPhone());

            // 向CFCA注册公司,电子签章开户
            AddAccountResp addAccountResp=ecmService.addAccountEnterprise(addAccountEnterpriseReq);

//            EcmUser ecmUser =new EcmUser();
//            companyService.updateCFCAUserId(companyRecord.getId(), ecmUser.getAccountId());

            if(addAccountResp.getSmsCheck().equals(false))
            {
                log.info("注册完成，不需要验证码");
            }else if(addAccountResp.getSmsCheck().equals(true)) {
                log.info("注册需要验证码");
            }
            return CommonRes.http_ok("绑定新增公司成功",addAccountResp).success();
        } catch (Exception e) {
            e.getStackTrace();
            log.error("绑定新增公司异常: \n" + "堆栈信息 ："+ ExceptionUtil.getStackTrace(e));
            return CommonRes.http_error("绑定新增公司失败"+e.getMessage()).fail();
        }
    }

    // PC用户绑定新增公司
    @RequestMapping("/pcAddCompany")
    public CommonRes PcAddCompany(@RequestBody CompanyDetailReq companyDetailReq) throws ServiceException {
        try {
            ProfileVO profileVO = naturalPersonService.getProfile();
            CompanyRecord companyRecord = companyService.addCompany(profileVO.getNaturalPersonId(), companyDetailReq);

            log.info("绑定公司的信息："+companyRecord);
            AddAccountEnterpriseReq addAccountEnterpriseReq=new AddAccountEnterpriseReq();
            addAccountEnterpriseReq.setAddAuthCode(true);
            addAccountEnterpriseReq.setChannelId(companyDetailReq.getChannelId());
            //这里先默认绑定人为法人
            addAccountEnterpriseReq.setLegal(true);
            addAccountEnterpriseReq.getContacter().setIndCertNo(profileVO.getIdCardNumber());
            addAccountEnterpriseReq.getContacter().setIndEmail("1795009213@qq.com");
            addAccountEnterpriseReq.getContacter().setIndName(profileVO.getName());
            addAccountEnterpriseReq.getContacter().setIndPhone(profileVO.getPhone());

            addAccountEnterpriseReq.getEnterprise().setEntIdentNo(companyDetailReq.getLicenseNo());
            addAccountEnterpriseReq.getEnterprise().setEntName(companyDetailReq.getCompanyName());
            //企业号码为空时取 法人手机号
            addAccountEnterpriseReq.getEnterprise().setEntTel(profileVO.getPhone());

            // 向CFCA注册公司,电子签章开户
            AddAccountResp addAccountResp=ecmService.addAccountEnterprise(addAccountEnterpriseReq);

//            EcmUser ecmUser =new EcmUser();
//            companyService.updateCFCAUserId(companyRecord.getId(), ecmUser.getAccountId());

            if(addAccountResp.getSmsCheck().equals(false))
            {
                log.info("注册完成，不需要验证码");
            }else if(addAccountResp.getSmsCheck().equals(true))
            {
                log.info("注册需要验证码");
            }
            return CommonRes.http_ok("绑定新增公司成功",addAccountResp).success();
        } catch (Exception e) {
            e.getStackTrace();
            log.error("绑定新增公司异常: \n" + "堆栈信息 ："+ ExceptionUtil.getStackTrace(e));
            return CommonRes.http_error("绑定新增公司失败"+e.getMessage()).fail();
        }
    }

    // PC用户绑定新增公司【跳过企业四要素】
    @RequestMapping("/pcAddCompanyWhitoutFactor")
    public CommonRes PcAddCompanyWhitoutFactor(@RequestHeader String token, @RequestBody CompanyDetailReq companyDetailReq) throws ServiceException {
        try {
            ProfileVO profileVO = naturalPersonService.getProfile();
            CompanyRecord companyRecord = companyService.addCompanyWhitoutFactor(profileVO.getNaturalPersonId(), companyDetailReq);
            log.info("绑定公司的信息："+companyRecord);
            AddAccountEnterpriseReq addAccountEnterpriseReq=new AddAccountEnterpriseReq();
            addAccountEnterpriseReq.setAddAuthCode(true);
            addAccountEnterpriseReq.setChannelId(companyDetailReq.getChannelId());
            //这里先默认绑定人为法人
            addAccountEnterpriseReq.setLegal(true);
            addAccountEnterpriseReq.getContacter().setIndCertNo(profileVO.getIdCardNumber());
            addAccountEnterpriseReq.getContacter().setIndEmail("1795009213@qq.com");
            addAccountEnterpriseReq.getContacter().setIndName(profileVO.getName());
            addAccountEnterpriseReq.getContacter().setIndPhone(profileVO.getPhone());

            addAccountEnterpriseReq.getEnterprise().setEntIdentNo(companyDetailReq.getLicenseNo());
            addAccountEnterpriseReq.getEnterprise().setEntName(companyDetailReq.getCompanyName());
            //企业号码为空时取 法人手机号
            addAccountEnterpriseReq.getEnterprise().setEntTel(profileVO.getPhone());

            // 向CFCA注册公司,电子签章开户
            AddAccountResp addAccountResp=ecmService.addAccountEnterprise(addAccountEnterpriseReq);

//            EcmUser ecmUser =new EcmUser();
//            companyService.updateCFCAUserId(companyRecord.getId(), ecmUser.getAccountId());

            if(addAccountResp.getSmsCheck().equals(false))
            {
                log.info("注册完成，不需要验证码");
            }else if(addAccountResp.getSmsCheck().equals(true)) {
                log.info("注册需要验证码");
            }
            return CommonRes.http_ok("绑定新增公司成功",addAccountResp).success();
        } catch (Exception e) {
            e.getStackTrace();
            log.error("绑定新增公司异常: \n" + "堆栈信息 ："+ ExceptionUtil.getStackTrace(e));
            return CommonRes.http_error("绑定新增公司失败"+e.getMessage()).fail();
        }
    }

}
