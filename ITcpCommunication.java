
public interface ITcpCommunication {
    public void creatClientThread();
    public void startClientCommunication();
    public void stopClientCommunication();
    public void sendMessageFromClient();

    public void createServerThread();
    public void startServerCommunication();
    public void stopServerCommunication();
    public void sendMessageFromServer();
}
