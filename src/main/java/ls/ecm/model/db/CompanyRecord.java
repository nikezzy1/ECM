package ls.ecm.model.db;

import lombok.Data;

@Data
public class CompanyRecord {
    private long id;
    private String companyName;
    private String licenseNo;
    private String cfcaUserId;
    private String channelId;
}
