package mx.com.qtx.cotizadorv1ds;
import java.math.BigDecimal;
import java.util.List;

public class CotizadorTest {
    public static void main(String[] args) {
//    	testCreacionPC();
    	
//    	testAgregarComponentes();
//    	testEliminarComponente();
    	
    	testGenerarCotizacion();
    }

	private static void testCreacionPC() {
    	Componente disco1 = new DiscoDuro("D001", "Disco Seagate", "TechXYZ", "X200", 
	                new BigDecimal("1880.00"), new BigDecimal("2000.00"), "1TB");   
       	Componente monitor = new Monitor("M001", "Monitor 17 pulgadas", "Sony", "Z9000", 
                new BigDecimal("3200.00"), new BigDecimal("6000.00"));   
        Componente tarjeta = new TarjetaVideo("C001", "Tarjeta XYZ", "TechBrand", "X200", 
                new BigDecimal("150.00"), new BigDecimal("200.00"), "16GB");
        
    	Componente miPc = new Pc("pc0001", "Laptop 15000 s300", "Dell", "Terminator",
    											List.of(disco1,monitor,tarjeta));
		miPc.mostrarCaracteristicas();
		
	}
	
	private static void testGenerarCotizacion() {
		Cotizador cotizador = new Cotizador();
		
		Componente monitor = new Monitor("M001","Monitor 17 pulgadas","Samsung","Goliat-500",
						new BigDecimal(1000), new BigDecimal(2000));
		cotizador.agregarComponente(1, monitor);
		
		Monitor monitor2 = new Monitor("M022","Monitor 15 pulgadas","Sony","VR-30",
				new BigDecimal(1100), new BigDecimal(2000));
		cotizador.agregarComponente(4, monitor2);
		cotizador.agregarComponente(7, monitor2);
		
		Componente disco = new DiscoDuro("D-23", "Disco estado sólido", "Seagate", "T-455", new BigDecimal(500), 
				new BigDecimal(1000), "2TB");
		cotizador.agregarComponente(10, disco);
	
	    Componente tarjeta = new TarjetaVideo("C0XY", "Tarjeta THOR", "TechBrand", "X200-34", 
	            new BigDecimal("150.00"), new BigDecimal("300.00"), "8GB");
		cotizador.agregarComponente(10, tarjeta);
	    
    	Componente discoPc = new DiscoDuro("D001", "Disco Seagate", "TechXYZ", "X200", 
                new BigDecimal("1880.00"), new BigDecimal("2000.00"), "1TB");   
	   	Componente monitorPc = new Monitor("M001", "Monitor 17 pulgadas", "Sony", "Z9000", 
	            new BigDecimal("3200.00"), new BigDecimal("6000.00"));   
	    Componente tarjetaPc = new TarjetaVideo("C001", "Tarjeta XYZ", "TechBrand", "X200", 
	            new BigDecimal("150.00"), new BigDecimal("200.00"), "16GB");
	    
		Componente miPc = new Pc("pc0001", "Laptop 15000 s300", "Dell", "Terminator",
												List.of(discoPc,monitorPc,tarjetaPc));
		cotizador.agregarComponente(1, miPc);
		Cotizacion cotizacion = cotizador.generarCotizacion();
		cotizacion.emitirComoReporte();
	}

}