package server.chatbot.questions;

import server.chatbot.ParamValue;
import server.chatbot.Phrase;

public class AswerQuestion extends Question {

    private final String output;

    public AswerQuestion(Phrase phrase, String output) {
        super(phrase);
        this.output = output;
    }

    @Override
    public String action(ParamValue... params) {
        return getOutput();
    }

    public String getOutput() {
        return output;
    }
}
