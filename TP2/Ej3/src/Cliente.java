import javax.persistence.*;

@Entity
@Table(name="CLIENTE")
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable=false)
	private int id;
	@Column(nullable=false,length=50)
	private String descripcion;
	
	public Cliente() {
		
	}
	
	public Cliente(int id_cliente, String descripcion) {
		super();
		this.id = id_cliente;
		this.descripcion = descripcion;
	}

	public int getId_cliente() {
		return id;
	}

	public void setId_cliente(int id_cliente) {
		this.id = id_cliente;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
	

}
