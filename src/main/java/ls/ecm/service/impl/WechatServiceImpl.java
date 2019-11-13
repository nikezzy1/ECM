package ls.ecm.service.impl;

import com.alibaba.fastjson.JSON;
import com.lingsi.platform.identity.dto.*;
import com.lingsi.platform.identity.exception.IdentityServiceException;
import com.lingsi.platform.identity.service.IdentityService;
import com.lingsi.platform.identity.service.impl.IdentityServiceImpl;
import lombok.extern.slf4j.Slf4j;
import ls.ecm.exception.ErrorCode;
import ls.ecm.exception.ServiceException;
import ls.ecm.model.wechat.*;
import ls.ecm.service.WechatService;
import ls.ecm.utils.ExceptionUtil;
import org.springframework.stereotype.Component;


@Slf4j

@Component
public class WechatServiceImpl implements WechatService {

    IdentityService identityService = new IdentityServiceImpl();

    @Override
    public WechatLip getLips(String name, String idNo) throws ServiceException {

        try {
            VerifyMsgReq verifyMsgReq=new VerifyMsgReq();
            verifyMsgReq.setName(name);
            verifyMsgReq.setIdCardNo(idNo);
            log.info("test唇语获得");

            VerifyMsgResp verifyMsgResp = identityService.getVerifyMsg(verifyMsgReq);
            log.info("唇语结果打印_Lip language :  "+verifyMsgResp.getSuccess());
            log.info("唇语报文打印_Lip language :  "+verifyMsgResp.getVerifyMsg());
            return JSON.parseObject(verifyMsgResp.getVerifyMsg(), WechatLipResp.class).getResult();
        } catch (IdentityServiceException ex) {
            ex.getStackTrace();
            ExceptionUtil.getStackTrace(ex);
            throw new ServiceException(ErrorCode.HTTPIOException);
        }
    }

    @Override
    public void alivenessDetect(String orderId, String name, String idNo, String videoStr) throws ServiceException {
        try {
            LiveDetectReq liveDetectReq = new LiveDetectReq();
            liveDetectReq.setOrderNo(orderId);
            liveDetectReq.setName(name);
            liveDetectReq.setIdCardNo(idNo);
            liveDetectReq.setVideoStr(videoStr);

            LiveDetectResp liveDetectResp = identityService.liveDetect(liveDetectReq);
            log.info("打印活体检测结果" + liveDetectResp.getPass());
            log.info("打印活体检测信息" + liveDetectResp.getMsg());
        } catch(IdentityServiceException e){
            e.printStackTrace();
            throw new ServiceException(ErrorCode.WechatAPIException);
        }

    }

}
