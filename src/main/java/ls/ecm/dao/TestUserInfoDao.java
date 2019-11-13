package ls.ecm.dao;

import ls.ecm.exception.ServiceException;
import ls.ecm.mapper.TestUserInfoMapper;
import ls.ecm.model.TestUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TestUserInfoDao {
    @Autowired
    private TestUserInfoMapper testUserInfoMapper;

    @Transactional
    public TestUserInfo test(Integer id) throws ServiceException {
        return testUserInfoMapper.selectById(id);
    }
}
