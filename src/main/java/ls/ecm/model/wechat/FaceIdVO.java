package ls.ecm.model.wechat;

import lombok.Data;

import java.util.Map;

@Data
public class FaceIdVO {
    private String code;
    private String msg;
    private Map<String, String> result;
}
