package ls.ecm.service.impl;


import ls.ecm.dao.UserInfoDao;
import ls.ecm.model.UserInfo;
import ls.ecm.service.UserInfoService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


@Component
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    private UserInfoDao userInfoDao;

    @Override
    public UserInfo selectById(Integer id){
      return userInfoDao.test(id);
       // return userInfoMapper.selectById(id);
    }
}
