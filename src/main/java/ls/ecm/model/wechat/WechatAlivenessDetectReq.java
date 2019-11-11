package ls.ecm.model.wechat;

import lombok.Data;

@Data
public class WechatAlivenessDetectReq {
    private String videoStr;
    private String orderId;
}
