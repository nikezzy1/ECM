package ls.ecm.model.req;

import lombok.Data;

@Data
public class PasswordReq {
    private String oldPassword;
    private String newPassword;
}
