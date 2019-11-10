package com.example.demo.model.enums;

public enum AuditFlowRole {
    PrivateCompany(0, "个体工商户"),
    PublicCompany(1, "企业"),
    NaturalPerson(2, "自然人");

    private final int code;
    private final String msg;

    private AuditFlowRole(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return code + ": " + msg;
    }
}
