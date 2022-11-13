public class Variable {
    private String key;
    private String type;
    private String value;

    public Variable(String key, String type) {
        this.key = key;
        this.type = type;
        this.value = "";
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
