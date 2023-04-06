package server.chatbot;

public class Phrase {

    private final String text;

    private final KeyWord[] keyWords;

    public Phrase(String text, KeyWord... keyWords) {
        this.text = text;
        this.keyWords = keyWords;
    }

    public String getText() {
        return text;
    }

    public KeyWord[] getMagicWords() {
        return keyWords;
    }
}