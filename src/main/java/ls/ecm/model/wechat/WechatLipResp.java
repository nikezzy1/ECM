package ls.ecm.model.wechat;

import lombok.Data;

@Data
public class WechatLipResp {
    private String code;
    private String msg;
    private String bizSeqNo;
    private WechatLip result;
    private String app_id;
    private String transactionTime;
}
