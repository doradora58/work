import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientThread extends Thread {

    private String serverIp;
    private int serverPort;
    // if use bind
    private String localIp;
    private int localPort;
    private boolean isRun;
    SocketChannel socketChannel;
    InetSocketAddress serverAddress;
    InetSocketAddress clientAddress;

    ClientThread(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        serverAddress = new InetSocketAddress(serverIp, serverPort);
        isRun = false;
    }

    public void init(String localIp, int localPort) {
        this.localIp = localIp;
        this.localPort = localPort;
        try {
            if (localIp != null && localPort != 0) {
                System.out.println("Bind mode");
                clientAddress = new InetSocketAddress(localIp, localPort);
                socketChannel.bind(clientAddress);
            } else {
                System.out.println("Not bind mode");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        isRun = true;
        while (isRun) {
            try {
                try {
                    System.out.println("ClientThread connecting...");
                    socketChannel = SocketChannel.open(serverAddress);
                } catch (IOException e) {
                    Thread.sleep(1000);
                    continue;
                }
                sendMessage();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void connect(String serverIp, int serverPort) {

    }

    public void connectWithBind(String serverIp, int serverPort, String localIp, int localPort) {

    }

    public void receive() {

    }

    public void sendMessage() {
    	
    	int dataSize = 14; //byte unit

    	// 送信バッファデータを構築(今回はint型をテストするので最低4バイトを確保)
        ByteBuffer bb = ByteBuffer.allocate(dataSize);
        long id = 1111;
        int no = 2222;
        short areaId = 1234;
        boolean valid = true;
        String version ="1.1.1";
        Status status = new Status(id, no, areaId, version);
        bb = serializeStatus(status);
        // 操作説明
//        System.out.println("送信する数値を入力してEnterで送信します。");
        
        // 数値を入力させる(オーバーフローなどは考慮していない)
//        bb.putInt(new Scanner(System.in).nextInt());
        // 送信準備を行う
        bb.flip();

        // 送信処理
        try {
            socketChannel.write(bb);
            //System.out.println("送信："+bb.getInt(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	private ByteBuffer serializeStatus(Status status) {
		// TODO Auto-generated method stub
	  	int dataSize = 30; //byte unit
		ByteBuffer buf = ByteBuffer.allocate(dataSize);
		// 4 byte
		buf.position(0);
		buf.putLong(status.getId());
		// 8 byte
		buf.position(8);
		buf.putInt(status.getNo());
		
		// 2 byte
		buf.position(12);
		buf.putShort(status.getAreaId());			
		
		buf.position(14);
		buf.put("1.1.1".getBytes(StandardCharsets.UTF_8));
		
		for(int i = 0; i < dataSize; i++) {
			if(i == 0) {
				System.out.print("送信データ："+buf.get(i)+", ");
			}else if(i!=dataSize-1) {
				System.out.print(buf.get(i)+", ");
			}else {
				System.out.println(buf.get(i));
			}
		}
		
		return buf;
	}

}
