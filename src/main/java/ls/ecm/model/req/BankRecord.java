package ls.ecm.model.req;

import lombok.Data;
import ls.ecm.model.enums.BankStatus;

@Data
public class BankRecord {
    private Long id;
    private String bankNo;
    private String bankName;
    private String bankCity;
    private String reservedPhone;
    private BankStatus bankStatus;
    private Long naturalPersonId;
}
