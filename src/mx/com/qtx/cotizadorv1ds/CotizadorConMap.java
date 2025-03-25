package mx.com.qtx.cotizadorv1ds;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import mx.com.qtx.cotizadorv1ds.componentes.Componente;

public class CotizadorConMap implements ICotizador {
    private Map<Componente, Integer> componentes;
    
    public CotizadorConMap() {
        this.componentes = new HashMap<>();
    }

    @Override
    public void agregarComponente(int cantidad, Componente componente) {        
        this.componentes.merge(componente, cantidad, Integer::sum);
    }

    @Override
    public void eliminarComponente(String idComponente) {   
        this.componentes.entrySet()
            .removeIf(entry -> entry.getKey().getId().equals(idComponente));
    }

    @Override
    public Cotizacion generarCotizacion() {
        BigDecimal total = new BigDecimal(0);
        
        Cotizacion cotizacion = new Cotizacion();
        
        int i = 0;
        for(Map.Entry<Componente, Integer> entry : this.componentes.entrySet()) {
        	Componente compI = entry.getKey();
        	int cantidadI = entry.getValue();
        	BigDecimal importeCotizadoI = compI.cotizar(cantidadI);           
        	DetalleCotizacion detI = new DetalleCotizacion((i + 1), compI.getId(), 
                compI.getDescripcion(), cantidadI, 
                compI.getPrecioBase(), importeCotizadoI);
        	cotizacion.agregarDetalle(detI);
            total = total.add(importeCotizadoI);
            i++;
        }
        cotizacion.setTotal(total);
   	
		return cotizacion;
    }

    @Override
    public void listarComponentes() {
        this.componentes.forEach((componente, cantidad) -> {
            System.out.println("Componente: " + componente.getDescripcion() + " - Cantidad: " + cantidad);
        });
    }
}   
