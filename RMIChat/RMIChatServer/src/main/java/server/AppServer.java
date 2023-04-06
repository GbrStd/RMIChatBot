package server;

import java.net.MalformedURLException;
import java.rmi.RemoteException;

public class AppServer {
    public static void main(String[] args) {
        try {
            final Server server = new Server();
            server.start(15123);
            System.out.println("Servidor Online");
        } catch (RemoteException | MalformedURLException e) {
            System.out.println("Falha ao iniciar o servidor");
        }
    }

}
