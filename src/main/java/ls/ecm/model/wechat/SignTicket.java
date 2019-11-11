package ls.ecm.model.wechat;

import lombok.Data;

import java.util.List;

@Data
public class SignTicket {
    private int code;
    private List<Ticket> tickets;
}
