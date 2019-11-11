package ls.ecm.service.impl;

import com.alibaba.fastjson.JSON;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import ls.ecm.constants.WechatConstants;
import ls.ecm.exception.ErrorCode;
import ls.ecm.exception.ServiceException;
import ls.ecm.model.vo.ProfileVO;
import ls.ecm.model.wechat.*;
import ls.ecm.service.NaturalPersonService;
import ls.ecm.service.RedisService;
import ls.ecm.service.WechatService;
import ls.ecm.utils.ExceptionUtil;
import ls.ecm.utils.OrderGenerator;
import ls.ecm.utils.RandomString;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import static ls.ecm.constants.WechatConstants.WECHAT_APP_ID;


@Slf4j

@Component
public class WechatServiceImpl implements WechatService {
    @Autowired
    private RedisService redisService;


    @Autowired
    private NaturalPersonService naturalPersonService;

    @Override
    public String getSignTicket(String appId) throws ServiceException {
        try {
            String accessToken = redisService.getAccessToken(appId);
            HttpResponse<JsonNode> signTicketObject = Unirest.get(WechatConstants.SIGN_TICKET)
                    .queryString("app_id", WECHAT_APP_ID)
                    .queryString("access_token", accessToken)
                    .queryString("type", WechatConstants.WECHAT_TICKET_TYPE)
                    .queryString("version", WechatConstants.WECHAT_VERSION).asJson();
            SignTicket signTicket = JSON.parseObject(signTicketObject.getBody().getObject().toString(), SignTicket.class);
            return signTicket.getTickets().get(0).getValue();
        } catch (Exception e) {
            log.error("计算活体校验SignTicket失败_Calculating the LivingBody check"+ ExceptionUtil.getStackTrace(e));
            throw new ServiceException(ErrorCode.WechatAPIException);
        }
    }

    @Override
    public String getNonceTicket(long naturalPersonId, String appId) throws ServiceException {
        try {
            String accessToken = redisService.getAccessToken(appId);

            HttpResponse<JsonNode> signTicketObject = Unirest.get(WechatConstants.SIGN_TICKET)
                    .queryString("app_id", WECHAT_APP_ID)
                    .queryString("access_token", accessToken)
                    .queryString("type", WechatConstants.WECHAT_TICKET_NONCE_TYPE)
                    .queryString("user_id", String.valueOf(naturalPersonId))
                    .queryString("version", WechatConstants.WECHAT_VERSION).asJson();
            SignTicket signTicket = JSON.parseObject(signTicketObject.getBody().getObject().toString(), SignTicket.class);
            return signTicket.getTickets().get(0).getValue();
        } catch (Exception e) {
            log.error("获得Nonce门票失败_getNonceTicketFail"+ExceptionUtil.getStackTrace(e));
            throw new ServiceException(ErrorCode.WechatAPIException);
        }
    }

    @Override
    public WechatLip getLips(String appId, String name, String idNo) throws ServiceException {
        String randomStr = RandomString.generateCode(32);
        String orderId = OrderGenerator.generateOrderId();
        String ticket = getSignTicket(appId);

        ArrayList<String> values = new ArrayList<>();
        values.add(WECHAT_APP_ID);
        values.add(WechatConstants.WECHAT_VERSION);
        values.add(randomStr);
        values.add(orderId);
        String sign = calculateAPISign(values, ticket);

        WechatLipReq wechatLipReq = new WechatLipReq();
        wechatLipReq.setIdNo(idNo);
        wechatLipReq.setName(name);
        wechatLipReq.setNonce(randomStr);
        wechatLipReq.setOrderNo(orderId);
        wechatLipReq.setSign(sign);
        wechatLipReq.setWebankAppId(WECHAT_APP_ID);
        wechatLipReq.setVersion(WechatConstants.WECHAT_VERSION);

        try {
            HttpResponse<String> object = Unirest.post(WechatConstants.WECHAT_LIPS_URL)
                    .header("Content-Type", "application/json").body(JSON.toJSONString(wechatLipReq))
                    .asString();
            log.info("唇语报文打印_Lip language :  "+object.getBody());
            return JSON.parseObject(object.getBody(), WechatLipResp.class).getResult();
        } catch (Exception ex) {
            ExceptionUtil.getStackTrace(ex);
            throw new ServiceException(ErrorCode.HTTPIOException);
        }
    }

    @Override
    public void alivenessDetect(String appId, String orderId, String name, String idNo, String videoStr) throws ServiceException {
        String randomStr = RandomString.generateCode(32);
        String ticket = getSignTicket(appId);

        ArrayList<String> values = new ArrayList<>();
        values.add(WECHAT_APP_ID);
        values.add(WechatConstants.WECHAT_VERSION);
        values.add(randomStr);
        values.add(orderId);
        String sign = calculateAPISign(values, ticket);
        WechatFaceCompare wechatFaceCompare = new WechatFaceCompare();
        wechatFaceCompare.setWebankAppId(WECHAT_APP_ID);
        wechatFaceCompare.setVersion(WechatConstants.WECHAT_VERSION);
        wechatFaceCompare.setSign(sign);
        wechatFaceCompare.setOrderNo(orderId);
        wechatFaceCompare.setAntiAttack("true");
        wechatFaceCompare.setName(name);
        wechatFaceCompare.setIdNo(idNo);
        wechatFaceCompare.setVideoStr(videoStr);
        wechatFaceCompare.setNonce(randomStr);
        HttpResponse<String> object = post(WechatConstants.WECHAT_ALIVENESS_DETECT_API_URL, JSON.toJSONString(wechatFaceCompare));
        log.info("打印活体检测报文_Print a live test message:"+object.getBody());
        WechatCompareResp wechatCompareResp = JSON.parseObject(object.getBody(), WechatCompareResp.class);
//        if(wechatCompareResp.getCode().equals("1")){
//            log.info("打印活体检测失败报文_Print a live test message_fail:"+object.getBody());
//        }else if(wechatCompareResp.getCode().equals("2")){
//            log.info("打印活体检测成功报文_Print a live test message_success:"+object.getBody());
//        }
        if (!wechatCompareResp.getCode().equals("0")) {
            throw new ServiceException(ErrorCode.WechatAPIException.getCode(), wechatCompareResp.getMsg());
        }
    }

    public HttpResponse<String> post(String url, String body) throws ServiceException {
        try {
            return Unirest.post(url)
                    .header("Content-Type", "application/json").body(body)
                    .asString();
        } catch (UnirestException e) {
            ExceptionUtil.getStackTrace(e);
            throw new ServiceException(ErrorCode.HTTPIOException);
        }
    }

    @Override
    public WechatSign calculateSign(long naturalPersonId, String name, String idNo, String appId) throws ServiceException {
        Map<String, String> map = new TreeMap<>();
        String randomStr = RandomString.generateCode(32);
        String orderId = OrderGenerator.generateOrderId();
        String ticket = getSignTicket(appId);

        map.put(WechatConstants.WECHAT_VERSION, WechatConstants.WECHAT_VERSION);
        map.put(WECHAT_APP_ID, WECHAT_APP_ID);
        map.put(orderId, orderId);
        map.put(name, name);
        map.put(idNo, idNo);
        map.put(String.valueOf(naturalPersonId), String.valueOf(naturalPersonId));

        map.put(ticket, ticket);

        StringBuilder sb = new StringBuilder();
        for (Iterator<String> it = map.keySet().iterator(); it.hasNext(); ) {
            String key = it.next();
            sb.append(map.get(key));
        }
        String sign = DigestUtils.sha1Hex(sb.toString()).toUpperCase();
        WechatSign wechatSign = new WechatSign();
        wechatSign.setOrderNo(orderId);
        wechatSign.setNonce(randomStr);
        wechatSign.setSign(sign);
        return wechatSign;
    }

    @Override
    public MiniProgramAlivenessDetectVO getFaceId(long naturalPersonId,
                                                  String name, String idNo, String appId) throws ServiceException {
        WechatSign wechatSign = calculateSign(naturalPersonId, name, idNo, appId);

        FaceIdReq faceIdReq = new FaceIdReq();
        faceIdReq.setWebankAppId(WECHAT_APP_ID);
        faceIdReq.setOrderNo(wechatSign.getOrderNo());
        faceIdReq.setName(name);
        faceIdReq.setUserId(String.valueOf(naturalPersonId));
        faceIdReq.setIdNo(idNo);
        faceIdReq.setSourcePhotoType("1");
        faceIdReq.setVersion(WechatConstants.WECHAT_VERSION);
        faceIdReq.setSign(wechatSign.getSign());
        FaceIdVO faceIdVO = null;

        try {
            HttpResponse<String> object = Unirest.post(WechatConstants.WECHAT_FACE_ID)
                    .header("Content-Type", "application/json").body(JSON.toJSONString(faceIdReq))
                    .asString();
            faceIdVO = JSON.parseObject(object.getBody(), FaceIdVO.class);
        } catch (Exception ex) {
            log.info("计算活体FaceId");
            throw new ServiceException(ErrorCode.HTTPIOException);
        }
        if (faceIdVO.getCode().equals("0")) {
            String h5faceId = faceIdVO.getResult().get("h5faceId");
            MiniProgramAlivenessDetectVO miniProgramAlivenessDetectVO = new MiniProgramAlivenessDetectVO();
            miniProgramAlivenessDetectVO.setSign(faceIdReq.getSign());
            miniProgramAlivenessDetectVO.setNonce(wechatSign.getNonce());
            miniProgramAlivenessDetectVO.setOrderNo(wechatSign.getOrderNo());
            miniProgramAlivenessDetectVO.setH5faceId(h5faceId);
            miniProgramAlivenessDetectVO.setWebankAppId(WECHAT_APP_ID);
            String ticket = getNonceTicket(naturalPersonId, appId);

            Map<String, String> map = new TreeMap<>();
            map.put(WECHAT_APP_ID, WECHAT_APP_ID);
            map.put(String.valueOf(naturalPersonId), String.valueOf(naturalPersonId));

            map.put(wechatSign.getNonce(), wechatSign.getNonce());
            map.put(WechatConstants.WECHAT_VERSION, WechatConstants.WECHAT_VERSION);
            map.put(h5faceId, h5faceId);
            map.put(wechatSign.getOrderNo(), wechatSign.getOrderNo());
            map.put(ticket, ticket);

            StringBuilder sb = new StringBuilder();
            for (Iterator<String> it = map.keySet().iterator(); it.hasNext(); ) {
                String key = it.next();
                sb.append(map.get(key));
            }
            String sign = DigestUtils.sha1Hex(sb.toString()).toUpperCase();
            miniProgramAlivenessDetectVO.setSign(sign);
            miniProgramAlivenessDetectVO.setNaturalPersonId(String.valueOf(naturalPersonId));
            return miniProgramAlivenessDetectVO;
        }
        throw new ServiceException(Integer.valueOf(faceIdVO.getCode()), faceIdVO.getMsg());
    }

    @Override
    public WechatIdCardInfo recognizeIdCard(@RequestHeader String token, String cardType, String fileContent, String appId) throws ServiceException {
        ProfileVO profileVO = naturalPersonService.getProfile(token);
        Map<String, String> map = new TreeMap<>();
        String randomStr = RandomString.generateChar(32);
        String orderId = OrderGenerator.generateOrderId();
        String ticket = getSignTicket(appId);

        map.put(WECHAT_APP_ID, WECHAT_APP_ID);
        map.put(randomStr, randomStr);
        map.put(orderId, orderId);
        map.put(WechatConstants.WECHAT_VERSION, WechatConstants.WECHAT_VERSION);
        map.put(ticket, ticket);

        StringBuilder sb = new StringBuilder();
        for (Iterator<String> it = map.keySet().iterator(); it.hasNext(); ) {
            String key = it.next();
            sb.append(map.get(key));
        }
        String sign = DigestUtils.sha1Hex(sb.toString()).toUpperCase();

        IdCardDetect idCardDetect = new IdCardDetect();
        idCardDetect.setWebankAppId(WECHAT_APP_ID);
        idCardDetect.setVersion(WechatConstants.WECHAT_VERSION);
        idCardDetect.setNonce(randomStr);
        idCardDetect.setSign(sign);
        idCardDetect.setOrderNo(orderId);
        idCardDetect.setCardType(cardType);
        idCardDetect.setIdcardStr(fileContent);

        try {
            HttpResponse<String> object = Unirest.post(WechatConstants.ID_CARD_DETECT_API)
                    .header("Content-Type", "application/json").body(JSON.toJSONString(idCardDetect))
                    .asString();
                log.info("vx -> 识别身份证 req:" + JSON.toJSONString(idCardDetect) + " \n resp: " + object.getBody());
                //AlertExceptionUtil.alert("  "+"自然人ID  ："+profileVO.getNaturalPersonId()+"  "+"正在扫描身份证 \n");
            return JSON.parseObject(object.getBody(), WechatIdCardInfo.class);
        } catch (Exception ex) {
                log.error("识别身份证失败");
            throw new ServiceException(ErrorCode.HTTPIOException);
        }
    }
}
