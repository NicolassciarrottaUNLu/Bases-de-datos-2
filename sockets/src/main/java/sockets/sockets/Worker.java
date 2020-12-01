package sockets.sockets;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import bds.Factura;
import bds.Firebird;
import bds.Personal;
import bds.Postgres;

public class Worker implements Runnable {

	private Socket client;
	private final static Logger log = LoggerFactory.getLogger(Worker.class);
	private BufferedReader inputChannel;
	private PrintWriter outputChannel;
	private Gson gson = new Gson();
	public Worker(Socket client) {
		this.client=client;
	}


	public void run() {
		try {
			while(client.isConnected()) {
			inputChannel =  new BufferedReader (new InputStreamReader (client.getInputStream()));
			outputChannel = new PrintWriter (client.getOutputStream(),true);
				String msg = inputChannel.readLine();
				Message exchangeMessagge = gson.fromJson(msg, Message.class);
				String msgParts[]=exchangeMessagge.getQuery().split("@");
				if(exchangeMessagge.getDatabase().equalsIgnoreCase("Firebird")){
						Firebird firebird = new Firebird();
							firebird.openConnection();
						if(exchangeMessagge.getQuery().startsWith("SELECT")){				
							int id=Integer.parseInt(msgParts[1]);
							Factura f = firebird.select(id);
							String responseMsg = gson.toJson(f);
							log.info("[SERVER] - SELECT ACTION ON ID " +msgParts[1]);
							outputChannel.println(responseMsg);
						}
						if(exchangeMessagge.getQuery().startsWith("UPDATE")) {
							outputChannel.println(
									firebird.update(Integer.parseInt(msgParts[1]), Double.parseDouble(msgParts[2]))
							);
							log.info("[SERVER] - UPDATE ACTION ON ID "+msgParts[1]);
						}
						if(exchangeMessagge.getQuery().startsWith("INSERT")) {
							outputChannel.println(
									firebird.insert(Integer.parseInt(msgParts[1]), Double.parseDouble(msgParts[2]))
							);
							log.info("[SERVER] - INSERT ACTION ON ID "+msgParts[1]);
						}
						if(exchangeMessagge.getQuery().startsWith("DELETE")) {
								outputChannel.println(firebird.delete(Integer.parseInt(msgParts[1])));
								log.info("[SERVER] - DELETE ACTION ON ID "+msgParts[1]);
						}
						firebird.closeConnection();
					}
					if(exchangeMessagge.getDatabase().equalsIgnoreCase("Postgres")){
						Postgres postgres = new Postgres();
						postgres.openConnection();
						if(exchangeMessagge.getQuery().startsWith("SELECT")) {
								int id=Integer.parseInt(msgParts[1]);
								Personal p = postgres.select(id);
								String responseMsg = gson.toJson(p);
								outputChannel.println(responseMsg);
								log.info("[SERVER] - SELECT ACTION ON ID " +msgParts[1]);
						}
						if(exchangeMessagge.getQuery().startsWith("UPDATE")) {
							outputChannel.println(
									postgres.update(Integer.parseInt(msgParts[1]), msgParts[2])
									);
							log.info("[SERVER] - UPDATE ACTION ON ID " +msgParts[1]);
						}
						if(exchangeMessagge.getQuery().startsWith("INSERT")) {
							outputChannel.println(
									postgres.insert(Integer.parseInt(msgParts[1]), msgParts[2])
									);
							log.info("[SERVER] - INSERT ACTION ON ID " +msgParts[1]);
						}
						if(exchangeMessagge.getQuery().startsWith("DELETE")) {
							outputChannel.println(postgres.delete(Integer.parseInt(msgParts[1])));
							log.info("[SERVER] - DELETE ACTION ON ID " +msgParts[1]);
						}
						postgres.closeConnection();
					}
					outputChannel.flush();
			}
		} catch (Exception e) {
			System.out.println("Ups");
		}
		
	}

}
