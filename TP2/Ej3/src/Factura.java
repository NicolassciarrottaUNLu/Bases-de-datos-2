

import javax.persistence.*;

@Entity
@Table (name="FACTURA")
public class Factura {
	@Id
	@Column(nullable=false)
	private int nro;
	@Column(nullable=false)
	private int id_cliente;
	@Column(nullable=false)
	private double importe;
	
	public Factura() {
		
	}
	public Factura(int nro, int id_cliente, double importe) {
		super();
		this.nro = nro;
		this.id_cliente = id_cliente;
		this.importe = importe;
	}

	public int getNro() {
		return nro;
	}

	public void setNro(int nro) {
		this.nro = nro;
	}

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(double importe) {
		this.importe = importe;
	}
	
	
	
}
