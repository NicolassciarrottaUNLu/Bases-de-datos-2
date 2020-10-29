package bd;

import java.util.ArrayList;
import java.util.Scanner;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;




public class Main {

	private static Scanner sc = new Scanner(System.in);
	private static ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),"facturacion.db4o");
	
	private static void menu() {
		System.out.println("Seleccione que desea realizar (1,2,3):");
		System.out.println("1- Factura");
		System.out.println("2- Cliente");
		System.out.println("3- Salir");
		System.out.println("Ingrese numero de opcion");
	}
	
	
	private static void crud() {
		System.out.println("Seleccione una operacion (1,2,3,4):");
		System.out.println("1) Insert");
		System.out.println("2) Select");
		System.out.println("3) Update");
		System.out.println("4) Delete");
	}
	
	private static void insertFactura() {
			System.out.println("Ingrese numero de cliente:");
			int id = sc.nextInt();
			System.out.println("Ingrese importe de factura");
			double importe = sc.nextDouble();
			Metodos.addFactura(db, id, importe);
	}
	
	private static void selectFactura(){
		System.out.println("Seleccione una opcion");
		System.out.println("1-Ver todas las facturas");
		System.out.println("2-Ver una factura");
		int opcion = sc.nextInt();
			switch(opcion) {
			case 1:
				ArrayList<Factura> facturas = Metodos.selectAllFacturas(db);
				for(Factura factura : facturas) System.out.println(factura.toString());
				break;
			case 2:
				System.out.println("Ingrese numero de factura");
				int id = sc.nextInt();
				System.out.println(Metodos.selectFactura(db, id).toString());
				break;
			}
		
	}
	
	private static void deleteFactura() {
		System.out.println("Ingrese numero de factura a eliminar");
		int nro = sc.nextInt();
		Metodos.deleteFactura(db, nro);
	}
	
	private static void updateFactura() {
		System.out.println("Ingrese numero de factura a modificar");
		int nro = sc.nextInt();
		System.out.println("Ingrese id de cliente");
		int id = sc.nextInt();
		System.out.println("Ingrese importe");
		double importe = sc.nextDouble();
		Metodos.updateFactura(db, nro, id, importe);
	}
	
	private static void insertCliente() {
		System.out.println("Ingrese descipcion de cliente");
		String descripcion = sc.nextLine();
		Metodos.addCliente(db, descripcion);
	}
	
	private static void selectCliente(){
		System.out.println("Seleccione una opcion");
		System.out.println("1-Ver todos las clientes");
		System.out.println("2-Ver un cliente");
		int opcion = sc.nextInt();
			switch(opcion) {
			case 1:
				ArrayList<Cliente> clientes = Metodos.selectAllClientes(db);
				for(Cliente cliente : clientes) System.out.println(cliente.toString());
				break;
			case 2:
				System.out.println("Ingrese id de cliente");
				int id = sc.nextInt();
				System.out.println(Metodos.selectCliente(db, id).toString());
				break;
			}
		
	}
	
	private static void deleteCliente() {
		System.out.println("Ingrese id de cliente a eliminar:");
		int id = sc.nextInt();
		Metodos.deleteCliente(db, id);
	}
	
	private static void updateCliente() {
		System.out.println("Ingrese id de cliente a modificar");
		int id = sc.nextInt();
		System.out.println("Ingrese descripcion de cliente");
		String descripcion = sc.nextLine();
		Metodos.updateCliente(db, id, descripcion);
	}
	
	
	public static void main(String[] args) {
		
		Metodos.Open(db);
		menu();
		int opcion = sc.nextInt();
			while(opcion != 3) {
				crud();
				int opcion2 = sc.nextInt();
				switch(opcion) {
					case 1:
						switch(opcion2) {
						case 1:
							insertFactura();
							break;
						
						case 2:
							selectFactura();
							break;
						case 3:
							updateFactura();
							break;
						case 4:
							deleteFactura();
							break;
							}
					break;
				case 2:
						switch(opcion2){
							case 1:
								insertCliente();
								break;
							case 2:
								selectCliente();
								break;
							case 3:
								updateCliente();
								break;
							case 4:
								deleteCliente();
								break;
						}
				break;
				}
					
				menu();
				opcion = sc.nextInt();
				}
			Metodos.Close(db);
		}
				

	}
	
	


