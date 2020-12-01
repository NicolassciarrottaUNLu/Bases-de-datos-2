package bds;

import java.sql.*;
import java.util.ArrayList;

public class Firebird {
	
	private Connection connection;
	private ResultSet resultSet;
	private Statement statement;
	private String driver="org.firebirdsql.jdbc.FBDriver";
	private String db="jdbc:firebirdsql:10.10.10.14/3050:F:/Desktop/FACTURACION.FDB";
	private String user="SYSDBA";
	private String password="masterkey";
	private String consulta;
	private String table = "FACTURACION";
	
	public void openConnection() {
		try {
			Class.forName(driver);
			this.connection = DriverManager.getConnection(this.db,this.user,this.password);
		} catch (Exception e) {
			System.out.println("Error al conectar con la base de datos");
		}
	}
	
	public void closeConnection() {
		try {
			this.connection.close();
		} catch (Exception e) {
			System.out.println("Error al cerrar conexión");
		}
		
	}
	
	public int insert(int id, double monto) {
			try {
				consulta = "INSERT INTO " + table +" VALUES("+id+","+monto+")";
				statement = connection.createStatement();
				statement.execute(consulta);
				return 1;
			} catch (Exception e) {
				System.out.println("Error al insertar valores");
				return 0;
			}
	}
	
	public synchronized int delete(int id) {
		try {
			consulta="DELETE FROM " + table + " WHERE ID="+id;
			statement = connection.createStatement();
			statement.execute(consulta);
			return 1;
		} catch (Exception e) {
			System.out.println("Error al eliminar tupla");
			return 0;
		}
	}
	
	
	
	public synchronized int update(int id, double monto) {
		try {
			consulta = "UPDATE "+table +" SET monto="+ monto +" WHERE id="+id;
			System.out.println(consulta);
			statement = connection.createStatement();
			statement.execute(consulta);
			return 1;
		} catch (Exception e) {
			System.out.println("Error al actualizar valores");
			return 0;
		}
	}
	
	public Factura select(int id) {
		try {
			consulta = "SELECT * FROM "+table+ " WHERE ID="+id;
			statement = connection.createStatement();
			resultSet = statement.executeQuery(consulta);
			Factura f =null;
			if(resultSet != null){
				while(resultSet.next()) {
					f=  new Factura(resultSet.getInt("id"),resultSet.getDouble("monto"));
					}
				return f;
					
			}else {
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Factura> selectAll() {
		try {
			consulta = "SELECT * FROM "+table;
			ArrayList<Factura> facturas = new ArrayList<Factura>();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(consulta);
			if(resultSet != null){
				while(resultSet.next()) {
					Factura f = new Factura(resultSet.getInt("id"),resultSet.getDouble("monto"));
					facturas.add(f);
				}
			}
			return facturas;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
