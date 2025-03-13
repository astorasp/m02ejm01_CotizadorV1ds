package mx.com.qtx.cotizadorv1ds;

import java.math.BigDecimal;

public class Monitor extends Componente {

	public Monitor(String id, String descripcion, String marca, String modelo, BigDecimal costo,
			BigDecimal precioBase) {
		super(id, descripcion, marca, modelo, costo, precioBase);
	}
	
	public BigDecimal cotizar(int cantidadI) {
		return this.calcularPrecioPromocion3X2(cantidadI, this);
	}
	
	private BigDecimal calcularPrecioPromocion3X2(int partes, Componente componente) {
    // Obtener el precio base del componente
	    BigDecimal precioBase = componente.getPrecioBase();
	    
	    // Calcular grupos completos de 3 unidades y unidades restantes
	    int gruposCompletos = partes / 3;
	    int unidadesRestantes = partes % 3;
	    
	    // Calcular total: (2 * grupos) + restantes
	    BigDecimal total = precioBase
	        .multiply(BigDecimal.valueOf(gruposCompletos * 2L + unidadesRestantes));
    
	    return total;
	}


}
