package ls.ecm.model.req;

import lombok.Data;

@Data
public class WechatCardReq {
    private String name;
    private String gender;
    private String nation;
    private String birthDate;
    private String address;
    private String idCardNumber;
    private String authority;
    private String validDate;
}
