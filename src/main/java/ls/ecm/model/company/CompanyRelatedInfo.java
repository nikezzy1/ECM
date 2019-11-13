package ls.ecm.model.company;

import lombok.Data;


@Data
public class CompanyRelatedInfo {
    private  CompanyBasicInfo companyBasicInfo;
    private LegalPersonInfo legalPersonInfo;
//    private BankInfo bankInfo;
}
