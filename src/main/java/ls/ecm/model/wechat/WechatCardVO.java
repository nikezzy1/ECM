package ls.ecm.model.wechat;

import lombok.Data;

@Data
public class WechatCardVO {
    private Long naturalPersonId;
    private String name;
    private String gender;
    private String nation;
    private String birthDate;
    private String address;
    private String idCardNumber;
    private String authority;
    private String validDate;
}
