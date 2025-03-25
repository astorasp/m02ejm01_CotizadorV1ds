package mx.com.qtx.cotizadorv1ds;
import java.math.BigDecimal;
import java.util.List;

import mx.com.qtx.cotizadorv1ds.componentes.Componente;
import mx.com.qtx.cotizadorv1ds.componentes.Pc;

public class CotizadorTest {
    public static void main(String[] args) {
    	//testCreacionPC();
    	testGenerarCotizacion();
    }

	private static void testCreacionPC() {
    	Componente disco1 = Componente.crearDiscoDuro("D001", "Disco Seagate", "TechXYZ", "X200", 
	                new BigDecimal("1880.00"), new BigDecimal("2000.00"), "1TB");   
       	Componente monitor = Componente.crearMonitor("M001", "Monitor 17 pulgadas", "Sony", "Z9000", 
                new BigDecimal("3200.00"), new BigDecimal("6000.00"));   
        Componente tarjeta = Componente.crearTarjetaVideo("C001", "Tarjeta XYZ", "TechBrand", "X200", 
                new BigDecimal("150.00"), new BigDecimal("200.00"), "16GB");
        Componente miPc = Componente.getPcBuilder()
			.setId("pc0001")
			.setDescripcion("Laptop 15000 s300")
			.setMarca("Dell")
			.setModelo("Terminator")
			.setCosto(new BigDecimal("1000.00"))
			.setPrecioBase(new BigDecimal("1500.00"))
			.agregarDiscoDuro(disco1)
			.agregarMonitor(monitor)
			.agregarTarjetaVideo(tarjeta)
		.build();
		miPc.mostrarCaracteristicas();
		
	}
	
	private static void testGenerarCotizacion() {
		ICotizador cotizador = new CotizadorConMap();
		
		Componente monitor = Componente.crearMonitor("M001","Monitor 17 pulgadas","Samsung","Goliat-500",
						new BigDecimal(1000), new BigDecimal(2000));
		cotizador.agregarComponente(1, monitor);
		
		Componente monitor2 = Componente.crearMonitor("M022","Monitor 15 pulgadas","Sony","VR-30",
				new BigDecimal(1100), new BigDecimal(2000));
		cotizador.agregarComponente(4, monitor2);
		cotizador.agregarComponente(7, monitor2);
		
		Componente disco = Componente.crearDiscoDuro("D-23", "Disco estado sólido", "Seagate", "T-455", new BigDecimal(500), 
				new BigDecimal(1000), "2TB");
		cotizador.agregarComponente(10, disco);
	
	    Componente tarjeta = Componente.crearTarjetaVideo("C0XY", "Tarjeta THOR", "TechBrand", "X200-34", 
	            new BigDecimal("150.00"), new BigDecimal("300.00"), "8GB");
		cotizador.agregarComponente(10, tarjeta);
	    
    	Componente discoPc = Componente.crearDiscoDuro("D001", "Disco Seagate", "TechXYZ", "X200", 
                new BigDecimal("1880.00"), new BigDecimal("2000.00"), "1TB");   
	   	Componente monitorPc = Componente.crearMonitor("M001", "Monitor 17 pulgadas", "Sony", "Z9000", 
	            new BigDecimal("3200.00"), new BigDecimal("6000.00"));   
	    Componente tarjetaPc = Componente.crearTarjetaVideo("C001", "Tarjeta XYZ", "TechBrand", "X200", 
	            new BigDecimal("150.00"), new BigDecimal("200.00"), "16GB");
	    
		Componente miPc = Componente.getPcBuilder()
			.setId("pc0001")
			.setDescripcion("Laptop 15000 s300")
			.setMarca("Dell")
			.setModelo("Terminator")
			.setCosto(new BigDecimal("1000.00"))
			.setPrecioBase(new BigDecimal("1500.00"))
			.agregarDiscoDuro(discoPc)
			.agregarMonitor(monitorPc)
			.agregarTarjetaVideo(tarjetaPc)
			.build();
		cotizador.agregarComponente(1, miPc);
		Cotizacion cotizacion = cotizador.generarCotizacion();
		cotizacion.emitirComoReporte();
	}

}