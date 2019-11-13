package ls.ecm.model.company;

import lombok.Data;

@Data
public class LegalPersonInfo {
    private Long id;
    private String phone;
    private String name;
    private String idCardNumber;
    private String idCardImagePath;
    private String channelId;
    private String address;
    private String auditFlowRole;
    private String selfPhone;
    private int status;
}
