package ls.ecm.service;


import ls.ecm.exception.ServiceException;
import org.springframework.stereotype.Service;


public interface RedisService {

    String getAccessToken(String appId) throws Exception;

    void setSMSCode(String phone, String channelId, String code, int ttl);

    boolean checkSMSCode(String channelId, String phone, String code) throws ServiceException;
}
