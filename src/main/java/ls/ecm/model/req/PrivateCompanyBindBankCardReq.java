package ls.ecm.model.req;

import lombok.Data;

@Data
public class PrivateCompanyBindBankCardReq {
    private String bankNo;
    private String bankName;
    private String bankCity;
    private String reservedPhone;
    private long companyId;
    private int status;
}