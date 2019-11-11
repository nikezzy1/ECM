package ls.ecm.model.req;

import lombok.Data;

@Data
public class AuthorizeContractReq {
    private long companyId;
    private String name;
    private String idCardNumber;
    private String title;
    private String phone;
    private String providerName;
    private String productName;
}
