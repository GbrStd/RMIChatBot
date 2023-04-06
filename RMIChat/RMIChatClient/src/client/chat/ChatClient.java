package client.chat;

import common.IChatServer;
import common.Message;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ChatClient {

    private final ArrayList<Message> chatHistory = new ArrayList<>();

    private IChatServer server = null;

    private String username;

    public void join(String username, String port) throws MalformedURLException, NotBoundException, RemoteException {
        server = (IChatServer) Naming.lookup("rmi://localhost:" + port + "/chat");

        this.username = username;

        threadCheckNewMessages().start();
    }

    private Thread threadCheckNewMessages() {
        return new Thread(() -> {
            while (true) {
                try {
                    final Message[] messages = server.receiveAllMessages();
                    if (messages.length > chatHistory.size()) {
                        chatHistory.clear();
                        chatHistory.addAll(List.of(messages));

                        System.out.println("\n".repeat(40));

                        chatHistory.forEach(message -> System.out.println(message.getOwner() + ": " + message.getContent()));
                    }
                    Thread.sleep(1);
                } catch (RemoteException | InterruptedException e) {
                    System.out.println("Falha ao receber mensagens");
                    break;
                }
            }
        });
    }

    public void sendMessage(String message) throws RemoteException {
        if (server != null) {
            server.sendMessage(this.username, message);
        }
    }

}
