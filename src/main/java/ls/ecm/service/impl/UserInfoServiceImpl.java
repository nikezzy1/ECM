package ls.ecm.service.impl;


import ls.ecm.dao.TestUserInfoDao;
import ls.ecm.model.TestUserInfo;
import ls.ecm.service.UserInfoService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private TestUserInfoDao testUserInfoDao;

    @Override
    public TestUserInfo selectById(Integer id){
      return testUserInfoDao.test(id);
       // return userInfoMapper.selectById(id);
    }
}
