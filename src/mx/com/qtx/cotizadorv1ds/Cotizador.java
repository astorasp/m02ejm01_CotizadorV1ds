package mx.com.qtx.cotizadorv1ds;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cotizador {
    private List<Componente> componentes = new ArrayList<>();
    private List<Integer> cantidades = new ArrayList<>();

    // Métodos de gestión de componentes
    public void agregarComponente(int cantidad, Componente componente) {
    	this.cantidades.add(cantidad);
    	this.componentes.add(componente);
    }

    public void eliminarComponente(String idComponente) {
    	int i = this.componentes.stream().map(compI->compI.getId())
    			                         .toList().indexOf(idComponente);
    	if (i==-1) // NO existe
    		return;
    	this.cantidades.remove(i);
    	this.componentes.remove(i);
    }

    // Métodos de cálculo de precios (ejemplo básico)
/*    public BigDecimal calcularPrecioPromocion3X2(int partes, Componente componente) {
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
    
    public BigDecimal calcularPrecioComponenteAgregado(int cantidadI, Componente compI) {
        BigDecimal total = BigDecimal.ZERO;
        for (Componente c : compI.getSubcomponentes()) {
        	if(c == null)
        		continue;
            total = total.add(c.getPrecioBase());
        }
        return total.multiply(BigDecimal.valueOf(1 - (porcentajePrecioAgregado / 100)));
    }

    public BigDecimal calcularPrecioDefault(Componente componente, int cantidad) {
        return componente.getPrecioBase().multiply(BigDecimal.valueOf(cantidad));
    }
 */

    public void emitirCotizacion() {
        System.out.println("=== Cotización ===");
        BigDecimal total = new BigDecimal(0);
        
        for(int i=0; i<this.cantidades.size();i++) {
        	Componente compI = this.componentes.get(i);
        	int cantidadI = this.cantidades.get(i);
        	BigDecimal importeCotizadoI = new BigDecimal(0);
        	
        	importeCotizadoI = compI.cotizar(cantidadI);
        	        	
            desplegarLineaCotizacion(compI, cantidadI, importeCotizadoI);  
            total = total.add(importeCotizadoI);
        }
        System.out.println("Total: $" + String.format("%8.2f",total));
    }

	private void desplegarLineaCotizacion(Componente compI, int cantidadI, BigDecimal importeCotizadoI) {
		System.out.println(String.format("%3d",cantidadI) + " " 
							+ String.format("%-20s", compI.getDescripcion())
//							+ " Tipo " + String.format("%-15s",compI.getTipo())
							+ " con precio base de $" + String.format("%8.2f",compI.getPrecioBase())
							+ " cuesta(n) " + String.format("%8.2f",importeCotizadoI));
	}
    
	public void listarComponentes() {
        System.out.println("=== Componentes a cotizar ===");
        for(int i=0; i<this.cantidades.size();i++) {
        	Componente c = this.componentes.get(i);
            System.out.println(this.cantidades.get(i) + " " + c.getDescripcion() 
            		 + ": $" + c.getPrecioBase() + " ID:" + c.getId());        	
        }
    }

    // Getters y Setters

}