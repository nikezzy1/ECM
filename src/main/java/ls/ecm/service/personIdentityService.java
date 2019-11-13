package ls.ecm.service;


import ls.ecm.exception.ServiceException;


public interface personIdentityService {
    /**
     * 通过手机号获取自然人ID
     * @param phone
     * @param channelId
     * @return
     * @throws ServiceException
     */
    long getNaturalPersonIdByPhone(String phone, String channelId) throws ServiceException;

}
