package ls.ecm.model.wechat;

import lombok.Data;

@Data
public class AlivenessDetectReq {
    private String webankAppId;
    private String version;
    private String nonce;
    private String orderNo;
    private String h5faceId;
    private String resultType;
    private String userId;
    private String sign;
}
