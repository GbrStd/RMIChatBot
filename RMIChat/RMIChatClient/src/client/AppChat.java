package client;

import client.chat.ChatClient;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class AppChat {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Test chat do bi and ti");

        System.out.println("Qual é o seu nome?");
        String nomeUser = scanner.nextLine();

        ChatClient chat = new ChatClient();

        System.out.println("\n".repeat(40));

        try {
            chat.join(nomeUser, "15123");
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            System.out.println("Falha ao conectar ao servidor");
        }

        while (true) {
            String menssagemUser = scanner.nextLine();
            try {
                chat.sendMessage(menssagemUser);
            } catch (RemoteException e) {
                System.out.println("Falha ao enviar a mensagem");
                break;
            }
        }
    }
}