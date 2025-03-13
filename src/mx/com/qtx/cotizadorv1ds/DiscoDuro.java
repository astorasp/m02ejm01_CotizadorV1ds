package mx.com.qtx.cotizadorv1ds;

import java.math.BigDecimal;

public class DiscoDuro extends Componente {
	private String capacidadAlm;

	public DiscoDuro(String id, String descripcion, String marca, String modelo, BigDecimal costo,
			BigDecimal precioBase, String capacidadAlm) {
		super(id, descripcion, marca, modelo, costo, precioBase);
		this.capacidadAlm = capacidadAlm;
	}

	public String getCapacidadAlm() {
		return capacidadAlm;
	}

	public void setCapacidadAlm(String capacidadAlm) {
		this.capacidadAlm = capacidadAlm;
	}

	
}
