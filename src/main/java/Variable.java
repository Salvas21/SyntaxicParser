public class Variable {
    private String key;
    private String value;

    public Variable(String key, String value) {
        this.key = key;
        this.value = value;
        // TODO : add var type ???
        // TODO : cant know value when declare
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
