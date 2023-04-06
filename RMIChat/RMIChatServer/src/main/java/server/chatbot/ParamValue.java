package server.chatbot;

public class ParamValue {

    private final Class<?> type;
    private String value;

    public ParamValue(String value, Class<?> type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Class<?> getType() {
        return type;
    }

}
