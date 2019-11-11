package ls.ecm.dao;

import ls.ecm.exception.ServiceException;
import ls.ecm.mapper.UserInfoMapper;
import ls.ecm.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserInfoDao {
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Transactional
    public UserInfo test(Integer id) throws ServiceException {
        return userInfoMapper.selectById(id);
    }
}
