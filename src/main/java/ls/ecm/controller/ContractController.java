package ls.ecm.controller;

import com.lingsi.platform.ecm.dto.CreateContractReq;
import com.lingsi.platform.ecm.dto.PreviewContractReq;
import com.lingsi.platform.ecm.dto.QueryContractTemplateReq;
import com.lingsi.platform.ecm.dto.SignContractReq;
import com.lingsi.platform.ecm.entity.ContractOperatorEntity;
import com.lingsi.platform.ecm.entity.ContractTemplateEntity;
import com.lingsi.platform.ecm.exception.EcmServiceException;
import com.lingsi.platform.ecm.service.EcmService;
import com.lingsi.platform.ecm.service.impl.EcmServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ls.ecm.core.response.CommonRes;
import ls.ecm.dao.NaturalPersonDao;
import ls.ecm.exception.ServiceException;
import ls.ecm.model.vo.ProfileVO;
import ls.ecm.service.NaturalPersonService;
import ls.ecm.utils.ExceptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class ContractController {

    @Autowired
    NaturalPersonService naturalPersonService;

   EcmService ecmService = new EcmServiceImpl();

    // 查询有权限发起签约的合同模板列表
    @RequestMapping("/queryTempletes")
    public CommonRes queryTempletes() {
        try {
            ProfileVO profileVO = naturalPersonService.getProfile();
            QueryContractTemplateReq queryContractTemplateReq = new QueryContractTemplateReq();
            queryContractTemplateReq.setChannelId(profileVO.getChannelId());
            //queryContractTemplateReq.setRoleType(profileVO.getAuditFlowRole().getMsg());
            //角色这里先取空，后续fix
            queryContractTemplateReq.setRoleType(null);

            //获取到模板列表--result
            List<ContractTemplateEntity> result = ecmService.queryTempletes(queryContractTemplateReq);
            return CommonRes.http_ok("查询有权限发起签约的合同模板列表成功",result).success();
        } catch (ServiceException e) {
            e.getStackTrace();
            log.error("查询有权限发起签约的合同模板列表异常: \n" + "堆栈信息 ："+ ExceptionUtil.getStackTrace(e));
            return CommonRes.http_error("查询有权限发起签约的合同模板列表失败"+e.getMessage()).fail();
        }
    }


    //根据模板和数据，预览填充数据后的合同html
    @RequestMapping("/previewContractHtml")
    public CommonRes previewContractHtml(@RequestBody PreviewContractReq previewContractReq) {
        try {
            String html = ecmService.previewContractHtml(previewContractReq);

            return CommonRes.http_ok("预览模版html成功",html).success();
        } catch (EcmServiceException e) {
            e.getStackTrace();
            log.error("预览模版html异常: \n" + "堆栈信息 ："+ ExceptionUtil.getStackTrace(e));
            return CommonRes.http_error("预览模版html失败"+e.getMessage()).fail();
        }
    }

    //预览合同pdf
    @RequestMapping("/previewContractPdf")
    public CommonRes previewContractPdf(@RequestBody PreviewContractReq previewContractReq) {
        try {
            FileOutputStream pdf = ecmService.previewContractPdf(previewContractReq);

            return CommonRes.http_ok("预览合同pdf成功",pdf).success();
        } catch (EcmServiceException e) {
            e.getStackTrace();
            log.error("预览合同pdf异常: \n" + "堆栈信息 ："+ ExceptionUtil.getStackTrace(e));
            return CommonRes.http_error("预览合同pdf失败"+e.getMessage()).fail();
        }
    }

    //查看模板待录入参数
    @RequestMapping("/queryTemplateParam")
    public CommonRes queryTemplateParam(@RequestParam String templateNo) {
        try {
            Map<String, List> result = ecmService.queryTemplateParam(templateNo);

            return CommonRes.http_ok("查看模板待录入参数成功",result).success();
        } catch (ServiceException e) {
            e.getStackTrace();
            log.error("查看模板待录入参数异常: \n" + "堆栈信息 ："+ ExceptionUtil.getStackTrace(e));
            return CommonRes.http_error("查看模板待录入参数失败"+e.getMessage()).fail();
        }
    }

    //发起签署任务创建电子合同
    @RequestMapping("/createContract")
    public CommonRes createContract(@RequestBody CreateContractReq createContractReq) {
        try {
            ecmService.createContract(createContractReq);
            return CommonRes.http_ok("发起签署任务创建电子合同成功").success();
        } catch (EcmServiceException e) {
            e.getStackTrace();
            log.error("发起签署任务创建电子合同异常: \n" + "堆栈信息 ："+ ExceptionUtil.getStackTrace(e));
            return CommonRes.http_error("发起签署任务创建电子合同失败"+e.getMessage()).fail();
        }
    }

    //查询待签署合同列表
    @RequestMapping("/queryContractToSign")
    public CommonRes queryContractToSign(@RequestBody ContractOperatorEntity contractOperatorEntity) {
        try {
            ecmService.queryContractToSign(contractOperatorEntity);

            return CommonRes.http_ok("查询待签署合同列表成功","result").success();
        } catch (EcmServiceException e) {
            e.getStackTrace();
            log.error("查询待签署合同列表异常: \n" + "堆栈信息 ："+ ExceptionUtil.getStackTrace(e));
            return CommonRes.http_error("查询待签署合同列表失败"+e.getMessage()).fail();
        }
    }

    //查询已签署合同列表
    @RequestMapping("/queryContractSigned")
    public CommonRes queryContractSigned(@RequestBody ContractOperatorEntity contractOperatorEntity) {
        try {
            ecmService.queryContractSigned(contractOperatorEntity);

            return CommonRes.http_ok("查询已签署合同列表成功","result").success();
        } catch (ServiceException e) {
            e.getStackTrace();
            log.error("查询已签署合同列表异常: \n" + "堆栈信息 ："+ ExceptionUtil.getStackTrace(e));
            return CommonRes.http_error("查询已签署合同列表失败"+e.getMessage()).fail();
        }
    }

    //签署合同
    @RequestMapping("/signContract")
    public CommonRes signContract(@RequestBody SignContractReq signContractReq) {
        try {
            ecmService.signContract(signContractReq);

            return CommonRes.http_ok("签署合同成功","result").success();
        } catch (ServiceException e) {
            e.getStackTrace();
            log.error("签署合同异常: \n" + "堆栈信息 ："+ ExceptionUtil.getStackTrace(e));
            return CommonRes.http_error("签署合同失败"+e.getMessage()).fail();
        }
    }

    //查询合同签署详情
    @RequestMapping("/queryContractSignInfo")
    public CommonRes queryContractSignInfo(@RequestParam String contractId) {
        try {
            ecmService.queryContractSignInfo(contractId);

            return CommonRes.http_ok("查询合同签署详情成功","result").success();
        } catch (ServiceException e) {
            e.getStackTrace();
            log.error("查询合同签署详情异常: \n" + "堆栈信息 ："+ ExceptionUtil.getStackTrace(e));
            return CommonRes.http_error("查询合同签署详情失败"+e.getMessage()).fail();
        }
    }


}
