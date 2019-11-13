package ls.ecm.service;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.alibaba.fastjson.JSON;
import ls.ecm.constants.WechatConstants;
import ls.ecm.exception.ServiceException;
import ls.ecm.model.wechat.*;

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

    WechatLip getLips(String name, String idNo) throws ServiceException;

    void alivenessDetect(String orderId, String name, String idNo, String videoStr) throws ServiceException;

}
