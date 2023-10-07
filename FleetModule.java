
public class FleetModule {

    TcpCommunication tcpCommunication;
    TcpConfig tcpConfig;
    
    FleetModule(){

        boolean config = true;
        if(config){
            // I need to read external file.
            String localIp = "127.0.0.1";
            int localPort = 51000;
            String serverIp = "127.0.0.1";
            int serverPort = 51000;
            // setting file configuration
            System.out.println("Set TcpConfig");
            tcpConfig = new TcpConfig(localIp, localPort, serverIp, serverPort);

            if(tcpConfig != null){
                System.out.println("Instance TcpCommunication");
                tcpCommunication = new TcpCommunication(tcpConfig,this);
                tcpCommunication.creatClientThread();
                tcpCommunication.createServerThread();
                
                start();
                


            }
        }
        
    }

    public void start(){
        // tcpCommunication
        if(tcpCommunication != null){
            tcpCommunication.startClientCommunication();
            tcpCommunication.startServerCommunication();
        }
    }

    public void Stop(){

    }
    
}
