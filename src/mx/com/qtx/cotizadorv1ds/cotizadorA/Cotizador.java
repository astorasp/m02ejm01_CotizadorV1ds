package mx.com.qtx.cotizadorv1ds.cotizadorA;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import mx.com.qtx.cotizadorv1ds.core.ComponenteInvalidoException;
import mx.com.qtx.cotizadorv1ds.core.Cotizacion;
import mx.com.qtx.cotizadorv1ds.core.DetalleCotizacion;
import mx.com.qtx.cotizadorv1ds.core.ICotizador;
import mx.com.qtx.cotizadorv1ds.core.componentes.Componente;
import mx.com.qtx.cotizadorv1ds.impuestos.CalculoImpuesto;

public class Cotizador implements ICotizador{
    private List<Componente> componentes = new ArrayList<>();
    private List<Integer> cantidades = new ArrayList<>();
	private CalculoImpuesto calculoImpuesto;

    // Métodos de gestión de componentes
    public void agregarComponente(int cantidad, Componente componente) {
    	this.cantidades.add(cantidad);
    	this.componentes.add(componente);
    }

    public void eliminarComponente(String idComponente) throws ComponenteInvalidoException {
    	if(idComponente == null) {
    		throw new ComponenteInvalidoException("Id del componente es nulo ", null);
    	}
    	int i = this.componentes.stream().map(compI->compI.getId())
    			                         .toList().indexOf(idComponente);
    	if (i==-1) {// NO existe
    		throw new ComponenteInvalidoException("No existe componente con Id "+ idComponente, null);
    	}
    	this.cantidades.remove(i);
    	this.componentes.remove(i);
    }

    public Cotizacion generarCotizacion(List<CalculoImpuesto> calculoImpuestos) {
        BigDecimal total = new BigDecimal(0);
        
        Cotizacion cotizacion = new Cotizacion();
        
        for(int i=0; i<this.cantidades.size();i++) {
        	Componente compI = this.componentes.get(i);
        	int cantidadI = this.cantidades.get(i);
        	BigDecimal importeCotizadoI = new BigDecimal(0);
        	
        	importeCotizadoI = compI.cotizar(cantidadI);
        	        
        	DetalleCotizacion detI = new DetalleCotizacion((i + 1), compI.getId(), compI.getDescripcion(), cantidadI, 
        			                                        compI.getPrecioBase(), importeCotizadoI, compI.getCategoria());
        	cotizacion.agregarDetalle(detI);
            total = total.add(importeCotizadoI);
        }
		/*Calculamos el impuesto total de la cotización*/
		BigDecimal totalImpuestos = new BigDecimal(0);
		if( calculoImpuestos != null) {
			for(CalculoImpuesto calculoImpuesto : calculoImpuestos) {
				BigDecimal impuesto = calculoImpuesto.calcularImpuesto(total);
				total = total.add(impuesto);
				totalImpuestos = totalImpuestos.add(impuesto);
			}
		}
		cotizacion.setTotalImpuestos(totalImpuestos);
        cotizacion.setTotal(total);
   	
		return cotizacion;
    }

	public void listarComponentes() {
        System.out.println("=== Componentes a cotizar ===");
        for(int i=0; i<this.cantidades.size();i++) {
        	Componente c = this.componentes.get(i);
            System.out.println(this.cantidades.get(i) + " " + c.getDescripcion() 
            		 + ": $" + c.getPrecioBase() + " ID:" + c.getId());        	
        }
    }

}