package ls.ecm.model.req;

import lombok.Data;

@Data
public class TransferValidateReq {
    private long companyId;
    private double amount;
}
