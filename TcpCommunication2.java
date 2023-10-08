import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class TcpCommunication2 extends Thread implements ITcpCommunication {

    private ClientThread clientThread;
    private ServerThread serverThread;
    private TcpConfig tcpConfig;
    private FleetModule fleetModule;
	private ServerSocketChannel serverChannel;
	private Selector selector;
	
	public TcpCommunication2(TcpConfig tcpConfig, FleetModule fleetModule) {
        this.tcpConfig = tcpConfig;
        this.fleetModule = fleetModule;
        
    }
	
	public void initServer() {
		try{
			serverChannel = ServerSocketChannel.open();
			
			InetSocketAddress serverAdress = new InetSocketAddress(tcpConfig.getServerIp(),tcpConfig.getServerPort());
			
			serverChannel.socket().bind(serverAdress);//受信ポートを指定
			
			serverChannel.configureBlocking(false);
			selector = Selector.open();
			//接続要求を監視
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			System.out.println("サーバー開始エラー");
			e.printStackTrace();
		}
	}
	//接続受け付けメソッド
	void accept() {
		try {
			SocketChannel socketChannel = serverChannel.accept();
			if ( socketChannel == null ) return;
			//非ブロックモードで接続リストに追加
			socketChannel.configureBlocking(false);
			//受信を監視
			socketChannel.register(selector, SelectionKey.OP_READ);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//通信処理
	void networkLogic() {
		try {
			//イベントが発生している場合は処理させます
			while(selector.selectNow()>0){
				Iterator it = selector.selectedKeys().iterator();
				while(it.hasNext()) {
					SelectionKey key = (SelectionKey) it.next();
					it.remove();
					if ( key.isAcceptable() ) {
						accept();
					} else if ( key.isReadable() ) {
						recv((SocketChannel) key.channel());
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//データ受信メソッド
	void recv(SocketChannel sc) {
		try {
			//接続していないなら閉じる
			if ( !sc.isConnected() ) {
				System.out.println("接続終了");
				sc.close();
			}
			//バイナリ受信領域初期化
//			bb.clear();
			
			//受信
//			sc.read(bb);
			
			//ロード準備
//			bb.flip();
			
			//文字列に変換
//			String result = StandardCharsets.UTF_8.decode(bb).toString();
//			if ( result.length() > 0 ) {
//				System.out.println("受信:"+result);
//				//ランダムな位置でメッセージを出現させる
//				comments.add(new Comment(640, rand.nextInt(380)+50, result));
//			}
		}catch(IOException e) {
			try {
				System.out.println("接続終了");
				sc.close();
			} catch (IOException e1) {}
		}
	}
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(40);
				networkLogic();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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
             String ip = null;
             int port = 0;
             clientThread.init(ip, port);
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
            serverThread.init();
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
