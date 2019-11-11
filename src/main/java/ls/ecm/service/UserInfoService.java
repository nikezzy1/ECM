package ls.ecm.service;

import ls.ecm.model.UserInfo;

public interface UserInfoService {

    UserInfo selectById(Integer id);

}