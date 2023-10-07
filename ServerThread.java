import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerThread extends Thread{

    
    private boolean isRun;
    private String serverIp;
    private int serverPort;
    private InetSocketAddress serverAddress;
    // SocketChannelクラスはノンブロッキングで通信処理を行うことができるクラス
    // configureBlockingメソッドにfalseを設定すると、受信待機を行いません
    // 受信ブロックを回避するためのスレッドを生成する必要がない...???
    private SocketChannel socketChannel;
    private ServerSocketChannel serverSocketChannel;

    ServerThread(String serverIp, int serverPort){
        this.isRun = false;
        this.serverIp = serverIp;
        this.serverPort = serverPort;
        this.serverAddress = new InetSocketAddress(serverIp, serverPort);
        socketChannel = null;
        serverSocketChannel = null;
    }

    public void init(){
        try {
            this.serverSocketChannel = ServerSocketChannel.open();
            // ノンブロッキングモード
            this.serverSocketChannel.configureBlocking(false);
            this.serverSocketChannel.socket().bind(serverAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run(){
        isRun = true;
        while(isRun){
            try {

                System.out.println("ServerThread accepting...");
                socketChannel = this.serverSocketChannel.accept();
                ByteBuffer buf = ByteBuffer.allocate(4);
                if(socketChannel!=null){
                    socketChannel.read(buf);
                    if(buf != null){
	    		        //intデータを受信データの0バイト目から読み込み
    			        System.out.println("受信:"+buf.getInt(0));
                }
                
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void close(){
        isRun = false;
        try {
                Thread.sleep(1000);
        } catch (InterruptedException e) {
        e.printStackTrace();
        }
        try {
        	//ソケットチャンネルクローズ
			socketChannel.close();
			//サーバーチャンネルクローズ
            serverSocketChannel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}
