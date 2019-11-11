package ls.ecm.model.vo;

import lombok.Data;

@Data
public class UserVO {
    private long naturalPersonId;
    private String token;
    private String phone;
}
