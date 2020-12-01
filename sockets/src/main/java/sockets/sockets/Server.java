package sockets.sockets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{

	private static ServerSocket serverSocket;
	private static int _PORTSERVER = 9000;
	private final static Logger log = LoggerFactory.getLogger(Server.class);
	
	
	public void start() {
	 
			try {
				serverSocket = new ServerSocket(_PORTSERVER);
				log.info("[SERVER] - Server created");
				
				while(true) {
					Socket client = serverSocket.accept();
						log.info("[SERVER] - Client accepted");
							Worker workerServer = new Worker(client);
							Thread twServer = new Thread(workerServer);
							twServer.start();
				}
				
			} catch (Exception e) {
				log.error("[SERVER] - Error: " + e.getMessage());
			}
		
	}
	
	public static void main(String[] args) {
		Server s = new Server();
		s.start();
		
	}
	
}
