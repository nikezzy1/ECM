package ls.ecm.model.enums;

public enum AlivenessDetectStatus {
    NULL(0, "未检验"),
    FAILED(1, "活体检测检验失败"),
    SUCCESS(2, "活体检测检验成功"),
    STAND_HOLD_FAILED(3, "手持身份证校验失败"),
    STAND_HOLD_SUCCESS(4, "手持身份证校验成功"),
    MANUAL_AUDIT(5, "人工审核通过");


    private final int code;
    private final String msg;

    private AlivenessDetectStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isPassed() {
        if (code == SUCCESS.getCode()
                || code == STAND_HOLD_SUCCESS.getCode()
                || code == MANUAL_AUDIT.getCode()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return code + ": " + msg;
    }
}