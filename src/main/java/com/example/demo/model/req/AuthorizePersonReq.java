package com.example.demo.model.req;

import lombok.Data;

@Data
public class AuthorizePersonReq {
    private String projectCode;
    private String contractNo;
    private String smsCode;
    private String providerName;
    private long companyId;

    private String name;
    private String idCardNumber;
    private String title;
    private String phone;
}
