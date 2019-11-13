package ls.ecm.model.company;

import lombok.Data;

@Data
public class CompanyVO {
    private long id;
    private String companyName;
    private String licenseImage;
    private String licenseNo;
    private String bankName;
    private int rights;
}
