package ls.ecm.model.wechat;

import lombok.Data;

@Data
public class WechatIdCardInfo {
    private String code;
    private String msg;
    private WechatIdCardResult result;
}
