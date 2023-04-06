package server.chatbot;

public class KeyWord {

    private final String word;

    private final int weight;

    public KeyWord(String word, int weight) {
        this.word = word;
        this.weight = weight;
    }

    public String getWord() {
        return word;
    }

    public int getWeight() {
        return weight;
    }

}