package bd;

public class Cliente {
	
	private int id_cliente;
	private String descripcion;
	
	
	
	public Cliente(int id_cliente, String descripcion) {
		super();
		this.id_cliente = id_cliente;
		this.descripcion = descripcion;
	}
	
	public int getId_cliente() {
		return id_cliente;
	}
	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	@Override
	public String toString() {
		return "ID: " + this.id_cliente + ", descripcion:" + this.descripcion;
	}
	
	

}
