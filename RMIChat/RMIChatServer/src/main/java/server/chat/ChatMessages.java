package server.chat;

import common.Message;

import java.util.ArrayList;

public class ChatMessages {

    private static final ArrayList<Message> messages = new ArrayList<>();

    public static void addMessage(String username, String message) {
        messages.add(new Message(username, message));
    }

    public static Message[] getMessages() {
        return messages.toArray(new Message[0]);
    }

}
