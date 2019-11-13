package ls.ecm.model;

import lombok.Data;

@Data
public class Privilege {
    private long naturalPersonId;
    private long objectId;
    private Rights rights;
    private Integer privilegeStatus;
    private String title;
}
