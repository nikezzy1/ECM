package ls.ecm.model.db;

import lombok.Data;

@Data
public class CompanyRecord {
    private long id;
    private String companyName;
    private String licenseNo;
    private Long bankId;
    private String cfcaUserId;
    private String channelId;
}
