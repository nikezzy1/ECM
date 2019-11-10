package com.example.demo.model.req;

import lombok.Data;

@Data
public class BankReq {
    private Long id;
    private String bankNo;
    private String bankName;
    private String bankCity;
    private String reservedPhone;
    private long companyId;
}
