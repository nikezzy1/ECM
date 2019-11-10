package com.example.demo.service;

import com.example.demo.model.UserInfo;
import org.springframework.stereotype.Service;

public interface UserInfoService {

    UserInfo selectById(Integer id);

}