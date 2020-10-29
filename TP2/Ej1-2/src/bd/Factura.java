package bd;

public class Factura {
	
	private int nro_factura;
	private int id_cliente;
	private double importe;
	
	
	public Factura(int nro_factura, int id_cliente, double importe) {
		super();
		this.nro_factura = nro_factura;
		this.id_cliente = id_cliente;
		this.importe = importe;
	}


	public void setNro_factura(int nro_factura) {
		this.nro_factura = nro_factura;
	}


	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}


	public void setImporte(double importe) {
		this.importe = importe;
	}


	public int getNro_factura() {
		return nro_factura;
	}


	public int getId_cliente() {
		return id_cliente;
	}


	public double getImporte() {
		return importe;
	}
	
	@Override
	public String toString() {
		return "Numero factura: " + this.nro_factura + ", Id cliente: " + this.id_cliente + " importe: " + this.importe;
	}
	
	
}
