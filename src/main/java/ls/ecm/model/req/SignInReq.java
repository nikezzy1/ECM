package ls.ecm.model.req;

import lombok.Data;

@Data
public class SignInReq {
    private String phone;
    private String password;
    private String channelId;
}
