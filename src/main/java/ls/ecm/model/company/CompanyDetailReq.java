package ls.ecm.model.company;

import lombok.Data;

@Data
public class CompanyDetailReq {
    private String companyName;
    private String licenseNo;
    private String companyType;
    private String channelId;
}
