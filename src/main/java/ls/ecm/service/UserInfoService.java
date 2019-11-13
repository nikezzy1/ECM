package ls.ecm.service;

import ls.ecm.model.TestUserInfo;

public interface UserInfoService {

    TestUserInfo selectById(Integer id);

}