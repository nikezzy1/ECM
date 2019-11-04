package com.example.demo.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;


@Data
public class UserInfo {

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    private String password;


}

