package ls.ecm.model.enums;

public enum BankStatus {
    AUDIT(0, "等待对公打款"),
    PUBLIC_COMPANY_CARD(1, "银行添加成功"),
    INVALID(2, "对公验证失败"),
    PRIVATE_COMPANY_CARD(3, "个体工商户银行添加成功"),
    NATURAL_PERSON_CARD(4, "个人绑卡");

    private final int code;
    private final String msg;

    private BankStatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return code + ": " + msg;
    }
}
