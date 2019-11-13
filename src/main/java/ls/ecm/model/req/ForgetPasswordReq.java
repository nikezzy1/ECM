package ls.ecm.model.req;

import lombok.Data;

@Data
public class ForgetPasswordReq {
    private String newPassword;
    private String smsCode;
    private String channelId;
    private String phone;
}
