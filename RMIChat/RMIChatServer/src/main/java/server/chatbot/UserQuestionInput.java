package server.chatbot;

import server.chatbot.questions.Question;

import java.util.ArrayList;

public class UserQuestionInput {

    private final Question question;

    private final ArrayList<ParamValue> params = new ArrayList<>();

    private int index;

    public UserQuestionInput(Question question, int index) {
        this.question = question;
        this.index = index;
    }

    public Question getQuestion() {
        return question;
    }

    public ArrayList<ParamValue> getParams() {
        return params;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
