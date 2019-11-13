package ls.ecm.service.impl;

import ls.ecm.dao.NaturalPersonDao;
import ls.ecm.dao.PrivilegeDao;
import ls.ecm.exception.ErrorCode;
import ls.ecm.exception.ServiceException;
import ls.ecm.model.Privilege;
import ls.ecm.model.Rights;
import ls.ecm.model.db.UserRecord;
import ls.ecm.service.personIdentityService;
import ls.ecm.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PersonIdentityServiceImpl implements personIdentityService {

    @Autowired
    private NaturalPersonDao naturalPersonDao;

    @Override
    public long getNaturalPersonIdByPhone(String phone, String channelId) throws ServiceException {
        UserRecord userRecord = naturalPersonDao.getByPhone(phone, channelId);
        return userRecord.getId();
    }
}
