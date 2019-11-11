package ls.ecm.model.wechat;

import lombok.Data;

@Data
public class AccessToken {
    private String access_token;
    private int code;
}
