package ls.ecm.service.impl;

import ls.ecm.exception.ErrorCode;
import ls.ecm.exception.ServiceException;
import ls.ecm.service.RedisService;
import ls.ecm.service.WechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;


@Component
public class RedisServiceImpl implements RedisService {
    @Autowired
    private Environment environment;

//    @Value("redis.port")
//    private String port;
//
//    @Value("redis.host")
//    private String host;
//
//    @Value("redis.password")
//    private String password;

    @Autowired
    private WechatService wechatService;

    private final String SMS_PREFIX_CODE = "SMS:PHONE:%s:%s";
    private final String WECHAT_ALIVENESS_DETECT_COUNTER = "WECHAT:ALIVENESS_DETECT:%s:%s";
    private final String WECHAT_ACCESS_TOKEN = "WECHAT:ACCESS_TOKEN:%s";

    private final String AUTHORIZE_PERSON = "AUTHORIZE:%s:%s";
    private final String APPLY_CONTRACT = "CONTRACT:%s:%s";


    public Jedis getJedis() {
        String host = environment.getProperty("redis.host");
        String port = environment.getProperty("redis.port");
        String password = environment.getProperty("redis.password");

        Jedis jedis = new Jedis(host, Integer.valueOf(port));
        jedis.auth(password);

        return jedis;
    }

    @Override
    public String getAccessToken(String appId) throws Exception {
        String accessTokenKey = String.format(WECHAT_ACCESS_TOKEN, appId);
        String accessToken = getJedis().get(accessTokenKey);
        getJedis().set(accessTokenKey, wechatService.wechatGetAccessToken());
        getJedis().expire(accessTokenKey, 7100);

        if (accessToken == null) {
            getJedis().set(accessTokenKey, wechatService.wechatGetAccessToken());
            getJedis().expire(accessTokenKey, 7100);
        }
        return getJedis().get(accessTokenKey);
    }

    @Override
    public void setSMSCode(String phone, String channelId, String code, int ttl) {
        String key = String.format(SMS_PREFIX_CODE, channelId, phone);
        getJedis().set(key, code);
        getJedis().expire(key, ttl);
    }

    @Override
    public boolean checkSMSCode(String channelId, String phone, String code) throws ServiceException {
         String key = String.format(SMS_PREFIX_CODE, channelId, phone);
         String sentCode = getJedis().get(key);
         if (sentCode != null && sentCode.equals(code)) {
            getJedis().del(key);
            return true;
        }
        throw new ServiceException(ErrorCode.CaptchaErrorException);
    }
}
