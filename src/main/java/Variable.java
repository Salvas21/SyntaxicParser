public class Variable {
    private final String key;
    private final String type;
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

    public boolean isRightType(String value) {
        boolean status = true;
        if (type.equals("entier")) {
            try {
                Integer.parseInt(value);
            } catch(NumberFormatException e) {
                status = false;
            }
        } else if (type.equals("reel")) {
            try {
                Float.parseFloat(value);
            } catch(NumberFormatException e) {
                status = false;
            }
        }
        return status;
    }
}
