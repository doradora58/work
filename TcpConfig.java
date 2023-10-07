
public class TcpConfig {

    private final String localIp;
    private final int localPort;
    private final String serverIp;
    private final int serverPort;
    TcpConfig(String localIp, int localPort, String serverIp, int serverPort){
        this.localIp = localIp;
        this.localPort = localPort;
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }
    public String getLocalIp() {
        return localIp;
    }
    public int getLocalPort() {
        return localPort;
    }
    public String getServerIp() {
        return serverIp;
    }
    public int getServerPort() {
        return serverPort;
    }

}
