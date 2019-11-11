package ls.ecm.model.wechat;

import lombok.Data;

@Data
public class WechatLipReq {
    private String webankAppId;
    private String nonce;
    private String version;
    private String sign;
    private String orderNo;
    private String name;
    private String idNo;
}
