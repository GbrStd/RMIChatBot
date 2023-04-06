package server.chatbot.questions;


import server.chatbot.ParamValue;
import server.chatbot.Phrase;

public abstract class Question {

    private final Phrase phrase;

    private final Param[] params;

    protected Question(Phrase phrase, Param... params) {
        this.phrase = phrase;
        this.params = params;
    }

    public abstract String action(ParamValue... params);

    public Phrase getPhrase() {
        return phrase;
    }

    public Param[] getParams() {
        return params;
    }

    public boolean haveParams() {
        return params != null && params.length > 0;
    }

    public static class Param {
        private final String name;
        private final Class<?> clazz;

        public Param(String name, Class<?> clazz) {
            this.name = name;
            this.clazz = clazz;
        }

        public String getName() {
            return name;
        }

        public Class<?> getClazz() {
            return clazz;
        }
    }

}
