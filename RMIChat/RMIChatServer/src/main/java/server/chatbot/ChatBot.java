package server.chatbot;


import server.chatbot.questions.AswerQuestion;
import server.chatbot.questions.Query;
import server.chatbot.questions.QueryQuestion;
import server.chatbot.questions.Question;
import server.utils.Params;
import server.utils.StringUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;

public class ChatBot {

    private static final Question[] QUESTIONS = {
            new QueryQuestion(
                    new Phrase("Qual a minha nota?", new KeyWord("nota?", 100)),
                    new Query("SELECT AVG(CAST(valor as FLOAT)) AS media FROM nota WHERE idAluno in (SELECT idAluno FROM aluno where RA = ?);"), "Sua nota é %s1",
                    Params.of("Qual seu R.A?", String.class)
            ),
            new QueryQuestion(
                    new Phrase("Quantos tenho de falta?", new KeyWord("falta?", 100)),
                    new Query(""), "Você tem %s1 faltas",
                    Params.of("Qual seu R.A?", String.class)
            ),
            new AswerQuestion(
                    new Phrase("O que é um chatbot?", new KeyWord("chatbot", 100)),
                    "Um chatbot é um programa de computador que simula conversas com humanos, normalmente por meio de texto ou voz."
            ),

    };
    private static final float SIMILAR_PERCENT = 80f;
    private static final int WEIGHT_PER_WORDS = 10;
    private final HashMap<String, UserQuestionInput> userInputs = new HashMap<>();

    public Optional<Question> findQuestion(String userInput, int minWeight) {

        HashMap<Question, Integer> map = new HashMap<>();

        String[] strings = userInput.split(" ");

        for (Question question : QUESTIONS) {
            Phrase phrase = question.getPhrase();
            map.put(question, 0);
            for (String word : strings) {
                for (KeyWord keyWord : phrase.getMagicWords())
                    if (StringUtils.isSimilarWord(word, keyWord.getWord(), SIMILAR_PERCENT))
                        map.put(question, map.get(question) + keyWord.getWeight());
                if (phrase.getText().contains(word))
                    map.put(question, map.get(question) + WEIGHT_PER_WORDS);
            }
        }

        return map.keySet()
                .stream()
                .filter(f -> map.get(f) > minWeight)
                .max(Comparator.comparingInt(map::get));
    }

    public void awaitUserInput(String username, Question question) {
        // add the username to the list of users waiting for a question answer
        userInputs.put(username, new UserQuestionInput(question, 0));
    }

    public boolean checkUserHaveInputs(String username) {
        // check if the user is in the list of users waiting for a question answer
        return userInputs.containsKey(username);
    }

    public void processUserInput(String username, String message) {
        // process the user input and remove the answered question from the list of remaining questions
        UserQuestionInput userQuestionInput = userInputs.get(username);
        if (userQuestionInput == null)
            return;
        Question question = userQuestionInput.getQuestion();
        if (userQuestionInput.getIndex() < question.getParams().length) {
            Question.Param param = question.getParams()[userQuestionInput.getIndex()];
            userQuestionInput.getParams().add(new ParamValue(message, param.getClazz()));
            userQuestionInput.setIndex(userQuestionInput.getIndex() + 1);
        }
    }

    public Question.Param getRemainingUserQuestionParamInput(String username) {
        // get the next question param that the user must answer
        UserQuestionInput userQuestionInput = userInputs.get(username);
        if (userQuestionInput == null)
            return null;
        Question question = userQuestionInput.getQuestion();
        if (userQuestionInput.getIndex() < question.getParams().length)
            return question.getParams()[userQuestionInput.getIndex()];
        return null;
    }

    public Question getUserInputQuestion(String username) {
        return Optional.ofNullable(userInputs.getOrDefault(username, null))
                .map(UserQuestionInput::getQuestion)
                .orElseThrow();
    }

    public ParamValue[] getUserInputParams(String username) {
        return Optional.ofNullable(userInputs.getOrDefault(username, null))
                .map(UserQuestionInput::getParams)
                .orElseThrow()
                .toArray(new ParamValue[0]);
    }

    public void removeUserInput(String username) {
        userInputs.remove(username);
    }
}
