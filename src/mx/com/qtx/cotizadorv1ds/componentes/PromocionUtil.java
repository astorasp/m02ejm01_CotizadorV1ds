package mx.com.qtx.cotizadorv1ds.componentes;

import java.math.BigDecimal;
import java.util.Map;

public class PromocionUtil {
	
	public static BigDecimal calcularPrecioPromocion3X2(int nUnidades, BigDecimal precioUnitario) {
		
		return calcularPrecioPromocionNXM(nUnidades, precioUnitario, 3, 2);
	}
	
	public static BigDecimal calcularPrecioPromocionNXM(int nUnidades, BigDecimal precioUnitario, int N, int M) {
	    // Obtener el precio base del componente
		    BigDecimal precioBase = precioUnitario;
		    
		    // Calcular grupos completos de N unidades y unidades restantes
		    int gruposCompletos = nUnidades / N;
		    int unidadesRestantes = nUnidades % N;
		    
		    // Calcular total: (M * grupos) + restantes
		    BigDecimal total = precioBase
		        .multiply(BigDecimal.valueOf(gruposCompletos * M + unidadesRestantes));
	    
		    return total;
		}

	public static BigDecimal calcularPrecioPromocionDsctoXcant(int cantidadI, BigDecimal precioBase,
			Map<Integer, Double> mapDsctos) {
		int keyDscto = mapDsctos.keySet()
				                .stream()
				                .sorted()                           // ordena asc
				                .filter(k -> k <= cantidadI)        // elimina llaves mayores que la cantidad
				                .sorted((n,n2) -> n <= n2 ? 1 : -1) // Ordena elementos filtrados dsc
				                .findFirst()                      // toma el primero, devuele optional
				                .get();                            // toma el valor

		return new BigDecimal(1).subtract(new BigDecimal(mapDsctos.get(keyDscto)).divide(new BigDecimal(100)))
		                        .multiply(precioBase)
		                        .multiply(new BigDecimal(cantidadI));
	}

}
