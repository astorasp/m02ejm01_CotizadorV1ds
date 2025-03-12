package mx.com.qtx.cotizadorv1ds;
import java.math.BigDecimal;

public class CotizadorTest {
    public static void main(String[] args) {
//    	testCreacionComponente();
//    	testCreacionComponente_confEquivocada_DiscoConMemoria();
//    	testCreacionPC();
    	
//    	testAgregarComponentes();
//    	testEliminarComponente();
    	
    	testEmitirCotizacion();
    }
    
    private static void testEliminarComponente() {
        // Crear instancia del Cotizador
        Cotizador cotizador = new Cotizador();
        
        // Crear algunos componentes
        Componente componente1 = new Componente("C001", "CPU XYZ", "TechBrand", "X200", 
            new BigDecimal("150.00"), new BigDecimal("200.00"), "DiscoDuro", 
            null, "N/A");
        Componente componente2 = new Componente("C002", "Tarjeta ABC", "GraphicBrand", "G100", 
            new BigDecimal("300.00"), new BigDecimal("400.00"), "TarjetaVideo", 
            "8GB", "N/A");
       	Componente disco1 = new Componente("D001", "Disco Seagate", "TechXYZ", "X200", 
                new BigDecimal("1880.00"), new BigDecimal("2000.00"), "DiscoDuro", 
                null, "1TB");   
       	Componente monitor = new Componente("M001", "Monitor 17 pulgadas", "Sony", "Z9000", 
            new BigDecimal("3200.00"), new BigDecimal("6000.00"), "Monitor", 
            null, null);   

        System.out.println("=== Agregar componentes ===");
        cotizador.agregarComponente(5, componente1);
        cotizador.agregarComponente(3, componente2);
        cotizador.agregarComponente(2, disco1);
        cotizador.agregarComponente(4, monitor);
        cotizador.listarComponentes(); // Mostrar cotización actual
        
        // Prueba: Eliminar componentes
        System.out.println("=== Eliminando componente D001 ===");
        cotizador.eliminarComponente("D001");
        cotizador.listarComponentes(); // Mostrar cotización actual
		
	}

	private static void testCreacionPC() {
    	Componente disco1 = new Componente("D001", "Disco Seagate", "TechXYZ", "X200", 
	                new BigDecimal("1880.00"), new BigDecimal("2000.00"), "DiscoDuro", 
	                null, "1TB");   
       	Componente monitor = new Componente("M001", "Monitor 17 pulgadas", "Sony", "Z9000", 
                new BigDecimal("3200.00"), new BigDecimal("6000.00"), "Monitor", 
                null, null);   
        Componente tarjeta = new Componente("C001", "Tarjeta XYZ", "TechBrand", "X200", 
                new BigDecimal("150.00"), new BigDecimal("200.00"), "TarjetaVideo", 
                "16GB", "N/A");    	
    	Componente miPc = Componente.crearPC("pc0001", "Laptop 15000 s300", "Dell", "Terminator",
    											disco1, null, monitor, tarjeta);
		miPc.mostrarCaracteristicas();
		
	}

	public static void testCreacionComponente() {
        Componente componente1 = new Componente("C001", "Tarjeta XYZ", "TechBrand", "X200", 
                new BigDecimal("150.00"), new BigDecimal("200.00"), "TarjetaVideo", 
                "16GB", "N/A");    	
        componente1.mostrarCaracteristicas();
    }
    
    public static void testCreacionComponente_confEquivocada_DiscoConMemoria() {
    	
    	try {
	        Componente componente1 = new Componente("D001", "Disco Seagate", "TechXYZ", "X200", 
	                new BigDecimal("1880.00"), new BigDecimal("2000.00"), "DiscoDuro", 
	                "16GB", "1TB");    	
			System.out.println("constructor Componente() funciona incorrectamente");
    	}
    	catch(IllegalArgumentException iaex) {
    		System.out.println("constructor Componente() funciona correctamente");
    	}
    	
    }
    
    public static void testAgregarComponentes() {
    // Crear instancia del Cotizador
        Cotizador cotizador = new Cotizador();
    // Crear algunos componentes
        Componente componente1 = new Componente("C001", "CPU XYZ", "TechBrand", "X200", 
            new BigDecimal("150.00"), new BigDecimal("200.00"), "DiscoDuro", 
            null, "N/A");
        Componente componente2 = new Componente("C002", "Tarjeta ABC", "GraphicBrand", "G100", 
            new BigDecimal("300.00"), new BigDecimal("400.00"), "TarjetaVideo", 
            "8GB", "N/A");

    // Prueba: Agregar componentes
        System.out.println("=== Agregar componentes ===");
        cotizador.agregarComponente(5, componente1);
        cotizador.agregarComponente(3, componente2);
        
        cotizador.listarComponentes(); // Mostrar contenido cotizador
   	
    }
    
    public static void testEmitirCotizacion() {
        // Crear instancia del Cotizador
        Cotizador cotizador = new Cotizador();

        // Crear algunos componentes
        Componente componente1 = new Componente("C001", "CPU XYZ", "TechBrand", "X200", 
            new BigDecimal("150.00"), new BigDecimal("200.00"), "DiscoDuro", 
            null, "N/A");
        Componente componente2 = new Componente("C002", "Tarjeta ABC", "GraphicBrand", "G100", 
            new BigDecimal("300.00"), new BigDecimal("400.00"), "TarjetaVideo", 
            "8GB", "N/A");
       	Componente monitor = new Componente("M00T", "Monitor 17 pulgadas", "Sony", "Z9000", 
                new BigDecimal("1000.00"), new BigDecimal("2000.00"), "Monitor", 
                null, null); 
       	
    	Componente discoPc = new Componente("D00Y", "Disco Seagate", "TechXYZ", "X200", 
                new BigDecimal("1880.00"), new BigDecimal("2000.00"), "DiscoDuro", 
                null, "1TB");   
	   	Componente monitorPc = new Componente("M00X", "Monitor 17 pulgadas", "Sony", "Z9000", 
	            new BigDecimal("3200.00"), new BigDecimal("3000.00"), "Monitor", 
	            null, null);   
	    Componente tarjetaPc = new Componente("C005", "Tarjeta XYZ", "TechBrand", "X200", 
	            new BigDecimal("150.00"), new BigDecimal("1000.00"), "TarjetaVideo", 
	            "16GB", "N/A");    	
		Componente miPc = Componente.crearPC("pc0001", "Laptop 15000 s300", "Dell", "Terminator",
												discoPc, null, monitorPc, tarjetaPc);
        System.out.println("=== Agregar componentes ===");
        cotizador.agregarComponente(10, componente1);
        cotizador.agregarComponente(5, componente2);
        cotizador.agregarComponente(10, monitor);
        cotizador.agregarComponente(1, miPc);
        
        // Prueba: Emitir cotizacion
        cotizador.emitirCotizacion(); // Mostrar cotización actual
  	
    }
}