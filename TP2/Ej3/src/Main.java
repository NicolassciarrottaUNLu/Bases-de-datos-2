

import java.util.Scanner;

import javax.persistence.*;


public class Main {
	private static EntityManagerFactory emf = null;
	private static EntityManager em = null;
	private static Scanner sc = new Scanner(System.in);

	
	private static void menu() {
		System.out.println("Seleccione que desea insertar (1,2,3):");
		System.out.println("1- Factura");
		System.out.println("2- Cliente");
		System.out.println("3- Salir");
		System.out.println("Ingrese numero de opcion");
	}
	
	
	public static void salir() {
		 if ( em != null ) em.close();
		 if ( emf != null ) emf.close();
		 } 
	
	public static EntityManagerFactory getEntityManagerFactory() { return emf; }
	
	public static EntityManager getEntityManager() { return em; } 
	
	public static void main(String[] args) {
		emf = Persistence.createEntityManagerFactory("db");
		em = emf.createEntityManager();
		EntityTransaction emt;
		menu();
		int opcion = sc.nextInt();
			while(opcion != 3) {
				switch(opcion) {
					case 1:
						{
						System.out.println("Ingrese nro de factura");
						int nro = sc.nextInt();
						System.out.println("Ingrese id de cliente");
						int id_cliente = sc.nextInt();
						System.out.println("Ingrese importe");
						double importe = sc.nextDouble();
						emt = em.getTransaction();
						emt.begin();
							Factura f = new Factura(nro, id_cliente, importe);
								try {
									em.persist(f);
									emt.commit();
									System.out.println("Commit ok");
								}catch(Exception e) {
									System.out.println("Error insertando factura");
								}
						}
						break;
						case 2:
						{
							System.out.println("Ingrese id de cliente");
							int id_cliente = sc.nextInt();
								System.out.println("Ingrese descripcion");
								sc.nextLine();
								String descripcion = sc.nextLine();
								emt = em.getTransaction();
								emt.begin();
									Cliente c = new Cliente(id_cliente,descripcion);
										try {
											em.persist(c);
											emt.commit();
											System.out.println("Commit ok");
										}catch(Exception e) {
											System.out.println("Error insertando cliente");
										}
						}
						break;
						
				}
					
				menu();
				opcion = sc.nextInt();
				}

			salir();
	
	}
}
