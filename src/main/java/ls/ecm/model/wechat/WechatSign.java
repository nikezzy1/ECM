package ls.ecm.model.wechat;

import lombok.Data;

@Data
public class WechatSign {
    private String orderNo;
    private String nonce;
    private String sign;
}
