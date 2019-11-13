package ls.ecm.model;

public enum Rights {
    AUTHORIZED_PERSON(0, "被授权人"),
    PRE_PUBLIC_COMPANY_LEGAL_PERSON(1, "法人流程授信流程"),
    PUBLIC_COMPANY_LEGAL_PERSON(2, "法人"),
    PENDING(3, "等待确认授权"),
    DENIED(4, "拒绝授权"),
    PRE_NATURAL_PERSON(5, "个人授信流程"),
    NATURAL_PERSON(6, "个人"),
    PRE_PRIVATE_COMPANY_LEGAL_PERSON(7, "个体工商户授信流程"),
    PRIVATE_COMPANY_LEGAL_PERSON(8, "个体工商户法人");

    private final int code;
    private final String description;

    private Rights(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return Integer.toString(code) + ": " + description;
    }
}
