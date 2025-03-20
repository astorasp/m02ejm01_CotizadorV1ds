package mx.com.qtx.cotizadorv1ds.componentes;

import java.math.BigDecimal;
import java.util.List;

public class Pc extends Componente {
	private List<Componente> subComponentes;
	private static final float DSCTO_PRECIO_AGREGADO = 20.0f;

	public Pc(String id, String descripcion, String marca, String modelo, 
			List<Componente> subComponentes) {
		super(id, descripcion, marca, modelo, new BigDecimal(0), new BigDecimal(0));
		this.subComponentes = subComponentes;
		this.setPrecioBase(this.calcularPrecioComponenteAgregado(0));
		this.setCosto(this.calcularCostoComponenteAgregado(0));
	}
	
	@Override
	public BigDecimal cotizar(int cantidadI) {
		return this.calcularPrecioComponenteAgregado(cantidadI);
	}
	
    private BigDecimal calcularPrecioComponenteAgregado(int cantidadI) {
        BigDecimal total = BigDecimal.ZERO;
        for (Componente c : this.subComponentes) {
        	if(c == null)
        		continue;
            total = total.add(c.getPrecioBase());
        }
//        return total.multiply(BigDecimal.valueOf(1 - (DSCTO_PRECIO_AGREGADO / 100)));
        return total.multiply( new BigDecimal(1).subtract( new BigDecimal(DSCTO_PRECIO_AGREGADO).divide(new BigDecimal(100)) )
        		             );
    }
	
    private BigDecimal calcularCostoComponenteAgregado(int cantidadI) {
        BigDecimal costoPc = BigDecimal.ZERO;
        for (Componente c : this.subComponentes) {
        	if(c == null)
        		continue;
        	costoPc = costoPc.add(c.getCosto());
        }
        return costoPc;
    }

    @Override
    public void mostrarCaracteristicas() {
        super.mostrarCaracteristicas();
        for (Componente c : this.subComponentes) {
        	c.mostrarCaracteristicas();
        }
    }

	@Override
	public String getCategoria() {
		return "PC";
	}
}
