package bds;

public class Factura {
	private int id;
	private double monto;
	
	public Factura(int id, double monto) {
		super();
		this.id = id;
		this.monto = monto;
	}
	public int getId() {
		return id;
	}
	public double getMonto() {
		return monto;
	}
	
	
}
