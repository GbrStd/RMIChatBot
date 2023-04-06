package common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IChatServer extends Remote {

    Message[] receiveAllMessages() throws RemoteException;

    void sendMessage(String owner, String message) throws RemoteException;

}
