import java.net.InetAddress;
import java.net.InetSocketAddress;

public class ClientThread extends Thread{

    private String serverIp;
    private int serverPort;
    // if use bind
    private String localIp;
    private int localPort;
    private boolean isRun;
    
    ClientThread(String serverIp, int serverPort){
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        
        isRun =false;
    }

    public void run(){
        isRun =true;
        while(isRun){
            try {
                System.out.println("ClientThread connecting...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void connect(String serverIp, int serverPort){

    }
    
    public void connectWithBind(String serverIp, int serverPort, String localIp, int localPort){

    }

    public void receive(){

    }

    public void send(){

    }


}
