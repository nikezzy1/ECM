package ls.ecm.model.wechat;

import lombok.Data;

@Data
public class WechatLip {
    private String bizSeqNo;
    private String transactionTime;
    private String orderNo;
    private String lips;
    private String success;
}
