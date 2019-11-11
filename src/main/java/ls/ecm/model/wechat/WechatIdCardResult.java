package ls.ecm.model.wechat;

import lombok.Data;


@Data
public class WechatIdCardResult {
    private String name;
    private String sex;
    private String birth;
    private String idcard;
    private String nation;
    private String address;
    private String authority;
    private String validDate;
}
