package server.chat;

import common.IChatServer;
import common.Message;
import server.chatbot.ChatBot;
import server.chatbot.questions.Question;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Optional;

public class ChatServerImpl extends UnicastRemoteObject implements IChatServer {

    private static final ChatBot chatBot = new ChatBot();
    private static final int CHAT_BOT_MIN_WEIGHT = 80;

    public ChatServerImpl() throws RemoteException {
    }

    @Override
    public Message[] receiveAllMessages() throws RemoteException {
        return ChatMessages.getMessages();
    }

    @Override
    public void sendMessage(String username, String message) throws RemoteException {
        if (username != null && message != null
                && username.trim().length() > 0 && message.trim().length() > 0) {

            ChatMessages.addMessage(username, message);

            // Verify if the chatbot is waiting for a user input.
            // An input is a question that the chatbot asked the user and the user must answer.
            answerChatBot(username, message);
        }
    }

    private void answerChatBot(String username, String message) {
        if (!chatBot.checkUserHaveInputs(username)) {

            final Optional<Question> chatBotQuestion = chatBot.findQuestion(message, CHAT_BOT_MIN_WEIGHT);

            // Encontrou uma pergunta
            if (chatBotQuestion.isPresent()) {
                final Question question = chatBotQuestion.get();
                if (question.haveParams()) {
                    chatBot.awaitUserInput(username, question);
                    ChatMessages.addMessage("CHATBOT", chatBot.getRemainingUserQuestionParamInput(username).getName());
                } else {
                    ChatMessages.addMessage("CHATBOT", question.action());
                }
            } else {
                ChatMessages.addMessage("CHATBOT", "Não entendi");
            }
        } else {
            // The user answered the chatbot question
            chatBot.processUserInput(username, message);

            if (chatBot.getRemainingUserQuestionParamInput(username) == null) {
                // The user answered all the chatbot questions
                // process the chatbot response
                final String action = chatBot.getUserInputQuestion(username).action(chatBot.getUserInputParams(username));
                ChatMessages.addMessage("CHATBOT", action);

                // remove the user from the list of users waiting for a question answer
                chatBot.removeUserInput(username);
            } else {
                // The user still have to answer the chatbot question
                ChatMessages.addMessage("CHATBOT", chatBot.getRemainingUserQuestionParamInput(username).getName());
            }
        }
    }

}
