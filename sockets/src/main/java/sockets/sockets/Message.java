package sockets.sockets;

import bds.Factura;
import bds.Personal;

public class Message {
	private String database;
	private String query;
	
	public Message(String database, String query) {
		super();
		this.database = database;
		this.query = query;
	}
	
	public String getDatabase() {
		return this.database;
	}

	public String getQuery() {
		return this.query;
	}
		
}
