package ls.ecm.model;

import ls.ecm.model.enums.AlivenessDetectStatus;
import lombok.Data;


@Data
public class ExtraInfo {
    private AlivenessDetectStatus alivenessDetect;
    private long naturalPersonId;
    private int status;
    private String gender;
    private String birthDate;
    private String address;
    private String authority;
    private String validDate;
    private String emblemImagePath;
    private String portraitImagePath;
}
