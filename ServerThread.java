
public class ServerThread extends Thread{

    
    private boolean isRun;
    private String serverIp;
    private int serverPort;

    ServerThread(String serverIp, int serverPort){
        this.isRun = false;
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    public void run(){
        isRun = true;
        while(isRun){
            System.out.println("ServerThread accepting...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
