package com.example.demo.model.req;

import lombok.Data;

@Data
public class CacheAuthorizeContract {
    private long naturalPersonId;
    private long companyId;
    private String contractNo;
    private String projectCode;

    private String name;
    private String idCardNumber;
    private String title;
    private String phone;
}
