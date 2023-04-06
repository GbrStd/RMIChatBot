package server.utils;


import server.chatbot.questions.Question;

public class Params {

    public static Question.Param of(String name, Class<?> clazz) {
        return new Question.Param(name, clazz);
    }

}
