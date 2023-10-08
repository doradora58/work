import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

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
				int dataSize =1024;
				ByteBuffer buf = ByteBuffer.allocate(dataSize);
				if(socketChannel!=null){
					socketChannel.read(buf);
					StatusMessage statusMessage = deserializeStatus(buf);
					statusMessage.printData();
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

	private StatusMessage deserializeStatus(ByteBuffer buf) {
		// TODO Auto-generated method stub
		int dataSize =1024;
		if(buf != null){
			//intデータを受信データの0バイト目から読み込み
			for (int i = 0; i < dataSize; i++) {
				if(i == 0) {
					System.out.print("受信データ "+buf.get(i)+", ");
				}else if(i != dataSize-1) {
					System.out.print(buf.get(i)+", ");
				}else {
					System.out.println(buf.get(i));
				}
			}
		}
//		buf.position(0);
		buf.flip();
		long id = buf.getLong();
		buf.position(8);
		int no = buf.getInt();
		buf.position(12);
		short areaId = buf.getShort();
		//boolean valid =buf.get;
		buf.position(14);
		buf.limit(19);
		String version = StandardCharsets.UTF_8.decode(buf).toString();
//		System.out.println(version);
//		String version = versionBuf.toString();
		buf.position(19);
		buf.limit(45);
//		CharBuffer newContent = StandardCharsets.UTF_8.decode(buf);
//		System.out.println(newContent);
//		String version = newContent.toString();
		String currentTime = StandardCharsets.UTF_8.decode(buf).toString();
//		System.out.println(currentTime);
		
		return new StatusMessage(id, no, areaId, version, currentTime);//new StatusMessage(id, no, areaId, version, currentTime);
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
