package ls.ecm.model.vo;

import lombok.Data;
import ls.ecm.model.enums.AuditFlowRole;

@Data
public class ProfileVO {
    private Long naturalPersonId;
    private String name;
    private String idCardNumber;
    private String phone;
    private AuditFlowRole auditFlowRole;
    // 经过实名认证的手机号
    private String realNamePhone;
    private String bankNo;

    private int status;
    private String addr;
    private String cfcaUserId;
    private String channelId;
}
