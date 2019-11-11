package ls.ecm.model.wechat;

import lombok.Data;

@Data
public class Ticket {
    private String value;
    private String expire_in;
    private String expire_time;
}
