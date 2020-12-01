package sockets.sockets;

import com.google.gson.Gson;

import bds.Factura;
import bds.Personal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client{
	
	private Socket socketClient;
	private String _SERVER="localhost";
	private int _PORTSERVER = 9000;
	private BufferedReader inputChannel;
	private PrintWriter outputChannel;
	private Scanner sc = new Scanner(System.in);
	
	private void menu() {
		System.out.println("¿Con qué base de datos desea interactuar?");
		System.out.println("Seleccione numero (1,2)");
		System.out.println("1-) Facturacion");
		System.out.println("2-) Personal");
		System.out.println("3-) Salir"); 
	}
	
	private String menu2(String campo) {
		System.out.println("¿Que desea realizar?");
		System.out.println("Seleccione numero (1,2,3,4)");
		System.out.println("1-) SELECT");
		System.out.println("2-) INSERT");
		System.out.println("3-) UPDATE");
		System.out.println("4-) DELETE");
		System.out.println("5-) SALIR");
		int resp = sc.nextInt();
		String query="";
				switch(resp) {
				case 1:
						System.out.println("Ingrese ID a recuperar");
						sc.nextLine();
						query ="SELECT@"+sc.nextLine();							
					break;
				case 2:
						System.out.println("Ingrese ID");
						sc.nextLine();
						String id = sc.nextLine();
						System.out.println("Ingrese "+campo);
						String nombre = sc.nextLine();
							query ="insert@"+id+"@"+nombre;
					break;
				case 3:
					System.out.println("Ingrese ID");
					sc.nextLine();
					id = sc.nextLine();
					System.out.println("Ingrese "+campo);
					nombre = sc.nextLine();
					query = "update@"+id+"@"+nombre;
					break;
				case 4:
						System.out.println("Ingrese ID para eliminar");
						sc.nextLine();
						id = sc.nextLine();
						query = "delete@"+id;
					break;
				}
			return query.toUpperCase();
		}
	
	public void start() {
		try {
			socketClient = new Socket(_SERVER,_PORTSERVER);
			menu();
			int option = sc.nextInt();
			inputChannel =  new BufferedReader (new InputStreamReader (socketClient.getInputStream()));
			outputChannel = new PrintWriter (socketClient.getOutputStream(),true);
				
			Gson gson = new Gson();
			Message exchangeMessage;
			String query,msg,response;
			
				while(option != 3) {
					switch(option) {
						case 1:
							System.out.println("Trabajara con la base de datos FACTURACION");
								query = menu2("monto");
								exchangeMessage = new Message("Firebird",query);
								msg = gson.toJson(exchangeMessage);
								outputChannel.println(msg);
								outputChannel.flush();
								if(query.startsWith("SELECT")) {
									response = inputChannel.readLine();
									Factura f = gson.fromJson(response, Factura.class);
									System.out.println("ID:"+f.getId()+" Monto:"+f.getMonto());
								}else {
									response = inputChannel.readLine();
									if(Integer.parseInt(response)==1){
										System.out.println("¡Operacion correcta!");
									}else {
										System.out.println("Ocurrio un error");
									}
								}
								menu();
								option = sc.nextInt();
								break;
						case 2:
							System.out.println("Trabajara con la base de datos PERSONAL");
							query = menu2("nombre");
							exchangeMessage = new Message("Postgres",query);
							msg = gson.toJson(exchangeMessage);
							outputChannel.println(msg);
							outputChannel.flush();
								if(query.startsWith("SELECT")) {
									response =inputChannel.readLine();
									Personal p = gson.fromJson(response, Personal.class);
									System.out.println("ID:"+ p.getId() + " Nombre: "+p.getNombre());
								}else {
									response = inputChannel.readLine();
										if(Integer.parseInt(response)==1){
											System.out.println("¡Operacion correcta!");
										}else {
											System.out.println("Ocurrio un error");
										}
								}
							
							menu();
							option = sc.nextInt();
							break;
						case 3:
							socketClient.close();
							System.exit(0);
						}
					}
			
		}catch(Exception e1) {
			System.err.println("Server not found");
		}
		
	}
	
	public static void main(String[] args) {
		Client c = new Client();
		c.start();
	}
	

}
