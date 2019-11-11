package com.example.demo.dao;

import com.example.demo.exception.ErrorCode;
import com.example.demo.exception.ServiceException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.db.UserRecord;
import com.example.demo.model.enums.NaturalPersonStatus;
import com.example.demo.utils.EncryptUtil;
import com.example.demo.utils.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.utils.DBUtil.checkDBResult;


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


    public UserRecord getByPhone(String phone, String channelId) throws ServiceException {
        return userMapper.getOneByPhone(phone, channelId);
    }


    @Transactional
    public void updateLoginPassword(long naturalPersonId, String salt,
                                    String loginPassword) throws ServiceException {
        String encryptedPassword = EncryptUtil.SHA256(loginPassword + salt);
        userMapper.updateLoginPassword(naturalPersonId, encryptedPassword);
    }

}
