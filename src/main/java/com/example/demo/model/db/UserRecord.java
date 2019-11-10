package com.example.demo.model.db;

import com.example.demo.model.enums.AuditFlowRole;
import com.example.demo.model.enums.NaturalPersonStatus;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserRecord implements Serializable {
    private Long id;
    private String phone;
    private String password;
    private String name;
    private String salt;
    private String idCardNumber;
    private String idCardImagePath;
    private String transactionPassword;
    private long bankId;
    private NaturalPersonStatus status;
    private String channelId;
    private AuditFlowRole auditFlowRole;
    private String selfPhone;
    private String cfcaUserId;

    public UserRecord() {
        super();
    }

    public UserRecord(Long id, String phone, String name, String salt, String password,
                      String idCardNumber, String idCardImagePath, String transactionPassword,
                      long bankId, NaturalPersonStatus status, String channelId, AuditFlowRole auditFlowRole,
                      String selfPhone, String cfcaUserId) {
        super();
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.salt = salt;
        this.password = password;
        this.transactionPassword = transactionPassword;
        this.idCardNumber = idCardNumber;
        this.idCardImagePath = idCardImagePath;
        this.bankId = bankId;
        this.status = status;
        this.channelId = channelId;
        this.auditFlowRole = auditFlowRole;
        this.selfPhone = selfPhone;
        this.cfcaUserId = cfcaUserId;
    }

    @Override
    public String toString() {
        return "id: " + id.toString() + " phone: " + phone;
    }
}
