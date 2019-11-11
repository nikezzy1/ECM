package ls.ecm.model.wechat;

import lombok.Data;

@Data
public class WechatFaceCompare {
    private String webankAppId;
    private String nonce;
    private String version;
    private String sign;
    private String orderNo;
    private String antiAttack;
    private String idNo;
    private String name;
    private String videoStr;
}
