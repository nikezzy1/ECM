package ls.ecm.model.company;

import lombok.Data;

@Data
public class CompanyDetailVO {
    private long id;
    private String companyName;
    private String licenseNo;
    private String legalPersonName;
    private String legalPersonIdCardNumber;
    private String bankNo;
    private String bankName;
}
