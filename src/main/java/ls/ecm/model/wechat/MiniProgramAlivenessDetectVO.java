package ls.ecm.model.wechat;

import lombok.Data;

@Data
public class MiniProgramAlivenessDetectVO {
    private String webankAppId;
    private String nonce;
    private String orderNo;
    private String h5faceId;
    private String naturalPersonId;
    private String sign;
}
