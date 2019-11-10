package com.example.demo.service.impl;


import com.example.demo.dao.UserInfoDao;
import com.example.demo.mapper.UserInfoMapper;
import com.example.demo.model.UserInfo;
import com.example.demo.service.UserInfoService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Component
public class UserInfoServiceImpl implements UserInfoService{

    @Resource
    private UserInfoDao userInfoDao;

    @Override
    public UserInfo selectById(Integer id){
      return userInfoDao.test(id);
       // return userInfoMapper.selectById(id);
    }
}
