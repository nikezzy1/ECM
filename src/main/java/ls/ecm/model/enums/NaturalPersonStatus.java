package ls.ecm.model.enums;

public enum NaturalPersonStatus {
    PENDING(1, "被授权中"),
    DONE(2, "注册完成"),
    ACCEPTED(3, "接受授权");

    private int type;
    private String desc;

    NaturalPersonStatus(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
