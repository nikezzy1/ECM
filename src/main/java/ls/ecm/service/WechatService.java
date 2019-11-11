package ls.ecm.service;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import ls.ecm.constants.WechatConstants;
import ls.ecm.exception.ServiceException;
import ls.ecm.model.wechat.*;


import java.util.Collections;
import java.util.List;

public interface WechatService {
    default String wechatGetAccessToken() throws Exception {
        HttpResponse<JsonNode> accessTokenObject = Unirest.get(WechatConstants.ACCESS_TOKEN_URL)
                .queryString("app_id", WechatConstants.WECHAT_APP_ID)
                .queryString("secret", WechatConstants.WECHAT_SECRET)
                .queryString("grant_type", WechatConstants.WECHAT_GRANT_TYPE)
                .queryString("version", WechatConstants.WECHAT_VERSION).asJson();
        AccessToken accessToken = JSON.parseObject(accessTokenObject.getBody().getObject().toString(), AccessToken.class);
        return accessToken.getAccess_token();
    }

    String getSignTicket(String appId) throws ServiceException;

    String getNonceTicket(long naturalPersonId, String appId) throws ServiceException;

    default String calculateAPISign(List<String> values, String ticket) {

        if (values == null) {
            throw new NullPointerException("values is null");
        }

        // remove null
        values.removeAll(Collections.singleton(null));
        values.add(ticket);
        Collections.sort(values);

        StringBuilder sb = new StringBuilder();
        for (String s : values) {
            sb.append(s);
        }

        return Hashing.sha1().hashString(sb, Charsets.UTF_8).toString().toUpperCase();
    }

    WechatLip getLips(String appId, String name, String idNo) throws ServiceException;

    void alivenessDetect(String appId, String orderId, String name, String idNo, String videoStr) throws ServiceException;

    WechatSign calculateSign(long naturalPersonId, String name, String idNo, String appId) throws ServiceException;

    MiniProgramAlivenessDetectVO getFaceId(long naturalPersonId, String name, String idNo, String appId) throws ServiceException;

    WechatIdCardInfo recognizeIdCard(String token, String cardType, String fileContent, String appId) throws ServiceException;
}
