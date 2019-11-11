package ls.ecm.model.req;

import lombok.Data;

@Data
public class RegisterReq {
    private String phone;
    private String password;
    private String code;
    private boolean forget;
    private String channelId;
}
