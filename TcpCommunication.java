
public class TcpCommunication implements ITcpCommunication {

    private ClientThread clientThread;
    private ServerThread serverThread;
    private TcpConfig tcpConfig;
    private FleetModule fleetModule;
    
    public TcpCommunication(TcpConfig tcpConfig, FleetModule fleetModule) {
        this.tcpConfig = tcpConfig;
        this.fleetModule = fleetModule;

    }

    @Override
    public void creatClientThread() {
        if(clientThread == null){
            System.out.println("Instance ClientThread");
            clientThread = new ClientThread(tcpConfig.getServerIp(),tcpConfig.getServerPort());
        }else{
            System.out.println("Already instanced ClientThread");
        }
    }

    @Override
    public void startClientCommunication() {
        if(clientThread != null){
            System.out.println("ClientThread run");
            clientThread.start();
        }else{
            System.out.println("ClientThread is null");   
        }        
    }

    @Override
    public void stopClientCommunication() {

        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stopClientCommunication'");
    }

    @Override
    public void sendMessageFromClient() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendMessageFromClient'");
    }

    @Override
    public void createServerThread() {
        if(serverThread == null){
            System.out.println("Instance ServerThread");
            serverThread = new ServerThread(tcpConfig.getServerIp(),tcpConfig.getServerPort());
        }else{
            System.out.println("Already instanced ServerThread");
        }

    }

    @Override
    public void startServerCommunication() {
        if(serverThread != null){
            System.out.println("ServerThread run");
            serverThread.start();
        }else{
            System.out.println("ServerThread is null");
        }
    }

    @Override
    public void stopServerCommunication() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'stopServerCommunication'");
    }

    @Override
    public void sendMessageFromServer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendMessageFromServer'");
    }

}
