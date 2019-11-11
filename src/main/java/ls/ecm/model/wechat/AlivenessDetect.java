package ls.ecm.model.wechat;

import lombok.Data;

@Data
public class AlivenessDetect {
    private String code;
    private String msg;
    private String bizSeqNo;
    private String order_no;
    private String app_id;
    private String transactionTime;
    private WechatAlivenessDetect result;
}
