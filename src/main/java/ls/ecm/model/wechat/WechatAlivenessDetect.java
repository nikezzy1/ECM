package ls.ecm.model.wechat;

import lombok.Data;

@Data
public class WechatAlivenessDetect {
    private String bizSeqNo;
    private String transactionTime;
    private String orderNo;
    private String idNo;
    private String idType;
    private String name;
    private String liveRate;
    private String occurredTime;
    private String success;
}
