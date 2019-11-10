package com.example.demo.dao;

import com.example.demo.exception.ServiceException;
import com.example.demo.mapper.UserInfoMapper;
import com.example.demo.model.UserInfo;
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
