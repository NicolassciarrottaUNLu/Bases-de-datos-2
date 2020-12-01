package bds;

import java.sql.*;
import java.util.ArrayList;

public class Postgres {


	private  Connection connection;
	private  ResultSet resultSet;
	private  Statement statement;
	private  String driver = "org.postgresql.Driver";
	private  String db="jdbc:postgresql://10.10.10.15:5432/postgres";
	private  String user="postgres";
	private  String password="postgres";
	private  String consulta;
	private  String table = "PERSONAL";
	

	public void openConnection() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(db,user,password);
		} catch (Exception e) {
			System.out.println("Error al conectar con la base de datos");
		}
	}
	
	public void closeConnection() {
		try {
			connection.close();
		} catch (Exception e) {
			System.out.println("Error al cerrar conexión");
		}
		
	}
	
	public synchronized int insert(int id, String nombre) {
			consulta = "INSERT INTO \"" + table +"\" VALUES ("+id+",\'"+nombre+"\'"+")";
			try {
				statement = connection.createStatement();
				statement.execute(consulta);
				return 1;
			} catch (SQLException e) {
				System.out.println("Error al insertar datos");
				return 0;
			}
		}
	
	public synchronized int delete(int id) {
		try {
			consulta="DELETE FROM \"" + table + "\" WHERE id="+id;
			System.out.println(consulta);
			statement = connection.createStatement();
			statement.execute(consulta);
			return 1;
		} catch (Exception e) {
			System.out.println("Error al eliminar tupla");
			return 0;
		}
	}
	
	

	public synchronized int update(int id, String nombre) {
		try {
			consulta = "UPDATE \""+table +"\" SET \"nombre\" =\'"+ nombre+"\' WHERE id="+id;
			statement = connection.createStatement();
			statement.execute(consulta);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al actualizar valores");
			return 0;
		}
	}

	public synchronized ArrayList<Personal> selectAll() {
		try {
			consulta = "SELECT * FROM \""+table+ "\"";
			ArrayList<Personal> personales = new ArrayList<Personal>();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(consulta);
				if(resultSet != null){
					while(resultSet.next()) {
						Personal p = new Personal(resultSet.getInt("id"),resultSet.getString("nombre"));
						personales.add(p);
					}
				}
				return personales;
		} catch (Exception e) {
			System.out.println("Error al seleccionar valores");
			return null;
		
		}
	}
	
	public synchronized Personal select(int id) {
		try {
			consulta = "SELECT * FROM \""+table+ "\" WHERE \"id\"="+id;
			System.out.println(consulta);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(consulta);
			Personal p=null;
			if(resultSet != null){
				while(resultSet.next()) {
					p = new Personal(resultSet.getInt("id"),resultSet.getString("nombre"));
				}
				return p;
			}else
				return null;
		} catch (Exception e) {
			System.out.println("Error al seleccionar valores");
			return null;
		
		}
	}
	
}
