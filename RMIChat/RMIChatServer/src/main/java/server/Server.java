package server;

import server.chat.ChatServerImpl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Server {

    public void start(int port) throws RemoteException, MalformedURLException {
        LocateRegistry.createRegistry(port);
        final ChatServerImpl chatServer = new ChatServerImpl();
        Naming.rebind("rmi://localhost:" + port + "/chat", chatServer);
    }

    public void stop(String port) throws RemoteException, MalformedURLException, NotBoundException {
        Naming.unbind("rmi://localhost:" + port + "/chat");
    }

}
