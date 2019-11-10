package com.example.demo.model.req;

import lombok.Data;

@Data
public class TransferValidateReq {
    private long companyId;
    private double amount;
}
