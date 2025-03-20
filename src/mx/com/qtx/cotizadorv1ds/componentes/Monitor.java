package mx.com.qtx.cotizadorv1ds.componentes;

import java.math.BigDecimal;
import java.util.Map;

import mx.com.qtx.cotizadorv1ds.PromocionUtil;

public class Monitor extends Componente {
	
	private static Map<Integer, Double> mapDsctos = Map.of(0,  0.0,
														   3,  5.0,
														   6, 10.0,
														   9, 12.0);

	private Monitor(){super();}

	protected Monitor(String id, String descripcion, String marca, String modelo, BigDecimal costo,
			BigDecimal precioBase) {
		super(id, descripcion, marca, modelo, costo, precioBase);
	}
	
	public BigDecimal cotizar(int cantidadI) {
		return PromocionUtil.calcularPrecioPromocionDsctoXcant(cantidadI, this.precioBase, mapDsctos);
	}
	@Override
	public String getCategoria() {
		return "Monitor";
	}

}
