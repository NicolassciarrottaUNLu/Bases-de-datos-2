package bd;

import java.util.ArrayList;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;

public class Metodos {
		
		//private String _CLOSE="close";
		//private String _OPEN="open";
		private static String _CLIENTE = "cliente";
		private static String _FACTURA = "factura";

		static void Open(ObjectContainer db) {
				try {
					db= Db4oEmbedded.openFile("db4o");
				}catch(Exception e) {
					System.err.println("Error al iniciar conexion con base de datos");
				}
			
		}
		
		static void Close(ObjectContainer db) {
			try {
				db.close();
			}catch(Exception e) {
				System.err.println("Error al cerrar conexion con base de datos");
			}
		}
		
		private static Metadata addMetadata(ObjectContainer db) {
			Metadata metadata = new Metadata();
			db.store(metadata);
			return metadata;
		}
		
		private static Metadata getMetadata(ObjectContainer db) {
			Metadata metadata = new Metadata();
			try {
				ObjectSet<Object> c = db.queryByExample(Metadata.class);
				metadata = (Metadata) c.get(0);
			}catch(Exception e) {
				metadata = addMetadata(db);
			}
			
			return metadata;
		}
		
		private static void updateMetadata(ObjectContainer db, String tabla) {
			Metadata metadataAux = getMetadata(db);
			db.delete(metadataAux);
				switch(tabla) {
				case "cliente":
					metadataAux.addClientes();
					break;
				case "factura":
					metadataAux.addFacturas();
					break;
				}
			db.store(metadataAux);
		}
		
		public static void addCliente(ObjectContainer db, String descripcion) {
			Metadata metadata = getMetadata(db);
			try {
				Cliente cliente = new Cliente(metadata.getClientes(),descripcion);
				db.store(cliente);
				db.commit();
				updateMetadata(db, _CLIENTE);
				System.out.println(cliente.toString() + " agregado con exito");
			}catch(Exception e) {
				System.err.println("[ERROR]- No se pudo agregar cliente");
			}
		}
		
		public static ArrayList<Cliente> selectAllClientes(ObjectContainer db){
			ArrayList<Cliente> clientes = new ArrayList<Cliente>();
			ObjectSet<Object> c = db.queryByExample(Cliente.class);
				for(Object lclientes : c) clientes.add((Cliente) lclientes);
				return clientes;
		}
		
		public static Cliente selectCliente(ObjectContainer db, int id) {
			ArrayList<Cliente> clientes = selectAllClientes(db);
			int position = 0;	
				if(!clientes.isEmpty()) {
					for(int i=0;i<clientes.size();i++) {
						if(clientes.get(i).getId_cliente() == id) {
						position = i;
						}
					}
					return clientes.get(position);
				}else {
					System.err.println("No existe el cliente solicitado");
					return null;
				}
		}
		
		public static void deleteCliente(ObjectContainer db, int id) {
			Cliente cliente = selectCliente(db,id);
				if(cliente != null) {
					db.delete(cliente);
					db.commit();
					System.out.println("CLiente eliminado con exito");
				}else {
					System.err.println("No existe el cliente solicitado");
				}
		}
		
		public static void updateCliente(ObjectContainer db, int id, String descripcion) {
			Cliente cliente = selectCliente(db, id);
			if(cliente != null) {
				cliente.setDescripcion(descripcion);
				db.store(cliente);
				db.commit();
			}else {
				System.err.println("No existe el cliente solicitado");
			}
		}
		
		public static void addFactura(ObjectContainer db, int id_cliente, Double importe) {
			Metadata metadata = getMetadata(db);
			Cliente cliente = selectCliente(db, id_cliente);
				if (cliente != null) {
					try {
						Factura factura = new Factura(metadata.getFacturas(), id_cliente, importe);
						db.store(factura);
						db.commit();
						updateMetadata(db, _FACTURA);
						System.out.println(factura.toString() + " agregado con exito");
					}catch(Exception e) {
						System.err.println("[ERROR]- No se pudo agregar factura");
					}
				}
		}
		
		public static ArrayList<Factura> selectAllFacturas(ObjectContainer db){
			ArrayList<Factura> facturas = new ArrayList<Factura>();
			ObjectSet<Object> f = db.queryByExample(Factura.class);
				for(Object lfacturas : f) facturas.add((Factura) lfacturas);
				return facturas;
		}
		
		public static Factura selectFactura(ObjectContainer db, int id) {
			ArrayList<Factura> facturas = selectAllFacturas(db);
			int position = 0;
				if(!facturas.isEmpty()) {
					for(int i=0;i<facturas.size();i++) {
						if(facturas.get(i).getId_cliente() == id) {
							position = i;
						}
					}
					return facturas.get(position);
				}else {
					System.err.println("No existe la factura solicitada");
					return null;
				}
		}
		
		public static void deleteFactura(ObjectContainer db, int id) {
			Factura factura = selectFactura(db,id);
				if(factura != null) {
					db.delete(factura);
					db.commit();
					System.out.println("Factura eliminada con exito");
				}else {
					System.err.println("No existe la factura solicitada");
				}
		}
		
		public static void updateFactura(ObjectContainer db, int nro, int id, double importe) {
			Factura factura = selectFactura(db, id);
			if(factura != null) {
				factura.setId_cliente(id);
				factura.setImporte(importe);
				db.store(factura);
				db.commit();
				System.out.println(factura.getNro_factura() + " actualizada con exito");
			}else {
				System.err.println("No existe la factura solicitada");
			}
		}
		
}
