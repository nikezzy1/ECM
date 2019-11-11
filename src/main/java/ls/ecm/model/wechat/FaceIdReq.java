package ls.ecm.model.wechat;

import lombok.Data;

@Data
public class FaceIdReq {
    private String webankAppId;
    private String orderNo;
    private String name;
    private String idNo;
    private String userId;
    private String sourcePhotoType;
    private String version;
    private String sign;
}
