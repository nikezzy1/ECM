package ls.ecm.model.vo;

import lombok.Data;

@Data
public class AuthorizationPersonVO {
    private String idCardNumber;
    private String phone;
    private String title;
    private String name;
    private int rights;
}
