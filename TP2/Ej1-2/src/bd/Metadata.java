package bd;

public class Metadata {

	private int clientes;
	private int facturas;
	
	public Metadata() {
		this.clientes=0;
		this.facturas=0;
	}
	
	public void addFacturas() {
		this.facturas++;
	}
	
	public void addClientes() {
		this.clientes++;
	}
	
	public int getClientes() {
		return clientes;
	}
	
	
	
	public int getFacturas() {
		return facturas;
	}
	
	
	
	
}
