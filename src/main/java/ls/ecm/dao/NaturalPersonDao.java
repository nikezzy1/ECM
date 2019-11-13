package ls.ecm.dao;

import ls.ecm.exception.ErrorCode;
import ls.ecm.exception.ServiceException;
import ls.ecm.mapper.UserMapper;
import ls.ecm.model.db.UserRecord;
import ls.ecm.model.enums.NaturalPersonStatus;
import ls.ecm.utils.EncryptUtil;
import ls.ecm.utils.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static ls.ecm.utils.DBUtil.checkDBResult;


@Component
public class NaturalPersonDao {

    @Autowired
    private UserMapper userMapper;


    @Transactional
    public UserRecord add(String phone, String password, String channelId) throws ServiceException {
        UserRecord userRecord = getByPhone(phone, channelId);
        if (userRecord != null) {
            String encryptedPassword = EncryptUtil.SHA256(password + userRecord.getSalt());
            userMapper.updateLoginPassword(userRecord.getId(), encryptedPassword);
            return userRecord;
        }
        String salt = RandomString.generateChar(8);
        String encryptedPassword = EncryptUtil.SHA256(password + salt);
        Integer result = userMapper.insertOne(phone, channelId, encryptedPassword, salt, null);
        checkDBResult(result);
        return getByPhone(phone, channelId);
    }

    @Transactional
    public UserRecord addAuthorizedPerson(String name, String idCardNumber, String phone, String channelId) throws ServiceException {
        UserRecord userEntity = getByIdCardNumber(idCardNumber, channelId);
        if (userEntity != null) {
            return userEntity;
        }
        Integer result = userMapper.insertAuthorizedPerson(name, idCardNumber, phone, NaturalPersonStatus.PENDING.getType(), channelId);
        checkDBResult(result);
        return getByIdCardNumber(idCardNumber, channelId);
    }

    public UserRecord getByPhone(String phone, String channelId) throws ServiceException {
        return userMapper.getOneByPhone(phone, channelId);
    }

    public UserRecord getByIdCardNumber(String idCardNumber, String channelId) throws ServiceException {
        return userMapper.getOneByIdCardNumber(idCardNumber, channelId);
    }

    public UserRecord getById(long naturalPersonId) throws ServiceException {
        return userMapper.getOneById(naturalPersonId);
    }

    @Transactional
    public void updateLoginPassword(long naturalPersonId, String salt,
                                    String loginPassword) throws ServiceException {
        String encryptedPassword = EncryptUtil.SHA256(loginPassword + salt);
        userMapper.updateLoginPassword(naturalPersonId, encryptedPassword);
    }

    @Transactional
    public void updateAuditFlowRole(long naturalPersonId, int auditFlowRole) throws ServiceException {
        Integer result = userMapper.updateAuditFlowRole(naturalPersonId, String.valueOf(auditFlowRole));
        checkDBResult(result);
    }

    @Transactional
    public void updateIdCardInfo(long naturalPersonId, String name, String idCardNumber) throws ServiceException {
        Integer result = userMapper.updateIdCardInfo(naturalPersonId, name, idCardNumber);
        checkDBResult(result);
    }

    public UserRecord getUserByPhone(String phone, String channelId) throws ServiceException {
        UserRecord userRecord = userMapper.getOneByPhone(phone, channelId);
        if (userRecord == null) {
            throw new ServiceException(ErrorCode.EmptyRecordException);
        }
        return userRecord;
    }

    public UserRecord checkUser(String phone, String password, String channelId) throws ServiceException {
        UserRecord userEntity = getUserByPhone(phone, channelId);
        if (userEntity.getPassword() == null) {
            throw new ServiceException(ErrorCode.NeedRegisterException);
        }
        if (!EncryptUtil.SHA256(password + userEntity.getSalt()).equals(userEntity.getPassword())) {
            throw new ServiceException(ErrorCode.PasswordErrorException);
        }
        return userEntity;
    }

    @Transactional
    public void updateNaturalPersonStatus(long naturalPersonId, NaturalPersonStatus naturalPersonStatus) throws ServiceException {
        Integer result = userMapper.updateNaturalPersonStatus(naturalPersonId, naturalPersonStatus.getType());
        checkDBResult(result);
    }

    @Transactional
    public void updateCFCAUserId(Long naturalPersonId, String cfcaUserId) throws ServiceException {
        Integer result = userMapper.updateCFCAUserId(naturalPersonId, cfcaUserId);
        checkDBResult(result);
    }
}
