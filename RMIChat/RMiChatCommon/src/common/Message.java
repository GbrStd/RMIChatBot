package common;

import java.io.Serializable;

public class Message implements Serializable {

    private final String owner;

    private final String content;

    public Message(String owner, String content) {
        this.owner = owner;
        this.content = content;
    }

    public String getOwner() {
        return owner;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "owner='" + owner + '\'' +
                ", message='" + content + '\'' +
                '}';
    }

}
