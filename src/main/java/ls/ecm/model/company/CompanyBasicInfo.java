package ls.ecm.model.company;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CompanyBasicInfo {
    private long id;
    private String companyName;
    private String licenseNo;
    private String cfcaUserId;
    private String channelId;
    private LocalDateTime contractDDL;
}
