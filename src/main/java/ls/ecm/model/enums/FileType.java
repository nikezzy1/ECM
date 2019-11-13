package ls.ecm.model.enums;

public enum FileType {
    ID_CARD(0, "id_card"),
    COMPANY_LICENSE(1, "license"),
    IMAGE(2, "image"),
    VIDEO(3, "video"),
    CONTRACT(4, "contract"),
    TEST_CONTRACT(5, "contract/test");

    private final int code;
    private final String msg;

    private FileType(int code, String msg) {
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