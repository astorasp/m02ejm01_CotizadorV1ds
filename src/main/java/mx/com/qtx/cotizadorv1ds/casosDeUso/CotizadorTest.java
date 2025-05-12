package mx.com.qtx.cotizadorv1ds.casosDeUso;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import mx.com.qtx.cotizadorv1ds.config.SpringContextProvider;
import mx.com.qtx.cotizadorv1ds.core.Cotizacion;
import mx.com.qtx.cotizadorv1ds.core.ICotizador;
import mx.com.qtx.cotizadorv1ds.core.componentes.Componente;
import mx.com.qtx.cotizadorv1ds.cotizadorA.Cotizador;
import mx.com.qtx.cotizadorv1ds.servicios.ComponenteServicio;
import mx.com.qtx.cotizadorv1ds.servicios.CotizacionServicio;

public class CotizadorTest {

	private final ComponenteServicio componenteServicio;
	private final CotizacionServicio cotizacionServicio;
	public CotizadorTest() {
		SpringContextProvider.initialize();
		componenteServicio = SpringContextProvider.getBean(ComponenteServicio.class);
		cotizacionServicio = SpringContextProvider.getBean(CotizacionServicio.class);
	}

    public static void main(String[] args) {
		System.out.println("*** Iniciando test ***");
		CotizadorTest cotizadorTest = new CotizadorTest();
		/*
		System.out.println(LocalDateTime.now().toString()+"Inicia testCreacionPC");		
    	cotizadorTest.testCreacionPC();
		System.out.println(LocalDateTime.now().toString()+"Termina testCreacionPC");
		*/

		/*
		System.out.println(LocalDateTime.now().toString()+"Inicia testAgregarComponentes");
    	cotizadorTest.agregarComponente();
		System.out.println(LocalDateTime.now().toString()+"Termina testAgregarComponentes");		
		*/
		/*
		System.out.println(LocalDateTime.now().toString()+"Inicia testEliminarComponente");
    	cotizadorTest.testEliminarComponente();
		System.out.println(LocalDateTime.now().toString()+"Termina testEliminarComponente");				
		*/

		System.out.println(LocalDateTime.now().toString()+"***Inicia testGenerarCotizacion***");
		cotizadorTest.testGenerarCotizacion();
		System.out.println(LocalDateTime.now().toString()+"***Termina testGenerarCotizacion***");
    }

	private void testCreacionPC() {
		System.out.println("*** testCreacionPC ***");
    	Componente disco1 = Componente.crearDiscoDuro("D003", "Disco Seagate", "TechXYZ", "X200", 
	                new BigDecimal("1880.00"), new BigDecimal("2000.00"), "1TB");   
       	Componente monitor = Componente.crearMonitor("M003", "Monitor 17 pulgadas", "Sony", "Z9000", 
                new BigDecimal("3200.00"), new BigDecimal("6000.00"));   
        Componente tarjeta = Componente.crearTarjetaVideo("C003", "Tarjeta XYZ", "TechBrand", "X200", 
                new BigDecimal("150.00"), new BigDecimal("200.00"), "16GB");
        
    	Componente miPc = Componente.crearPc("PC007", "Laptop 15000 s300", "Dell", "Terminator",
    											List.of(disco1,monitor,tarjeta));

		componenteServicio.guardarPcCompleto(miPc);
		/*VER MANERA DE RECUPERAR LA INFO GUARDADA*/										
		miPc.mostrarCaracteristicas();
		
	}

	private void agregarComponente() {
		Componente monitor = Componente.crearMonitor("M005","Monitor 17 pulgadas","Samsung","Goliat-500",
						new BigDecimal(1000), new BigDecimal(2000));
		
		Componente monitor2 = Componente.crearMonitor("M006","Monitor 15 pulgadas","Sony","VR-30",
				new BigDecimal(1100), new BigDecimal(2000));

		componenteServicio.guardarComponente(monitor);
		componenteServicio.guardarComponente(monitor2);		
	}

	private void testEliminarComponente() {
		System.out.println("*** testEliminarComponente ***");

		componenteServicio.borrarComponente("M005");
		componenteServicio.borrarComponente("M006");
		
	}

	private void testMostrarCaracteristicas() {
		System.out.println("*** testMostrarCaracteristicas ***");
    	Componente disco1 = Componente.crearDiscoDuro("D001", "Disco Seagate", "TechXYZ", "X200", 
                new BigDecimal("1880.00"), new BigDecimal("2000.00"), "1TB");   
		disco1.mostrarCaracteristicas();
	}
	
	private void testCreacionPcOk_conPcBuilder() {
		System.out.println("*** testCreacionPcOk_conPcBuilder ***");
		
		Componente miPc = Componente.getPcBuilder()
				                    .definirId("pc0001")
		                            .definirDescripcion("Laptop 15000 s300")
		                            .definirMarcaYmodelo("Dell", "Terminator")
		                            .agregarDisco("D001", "Disco Seagate", "TechXYZ", "X200", 
		             	                new BigDecimal("1880.00"), new BigDecimal("2000.00"), "1TB")
		                            .agregarMonitor("M001", "Monitor 17 pulgadas", "Sony", "Z9000", 
		                                 new BigDecimal("3200.00"), new BigDecimal("6000.00"))
		                            .agregarTarjetaVideo("C001", "Tarjeta XYZ", "TechBrand", "X200", 
		                                 new BigDecimal("150.00"), new BigDecimal("200.00"), "16GB")
		                            .agregarDisco("D002", "Disco Seagate", "TechXYZJr", "X100", 
			             	                new BigDecimal("1000.00"), new BigDecimal("1600.00"), "500GB")
		                            .build();
		componenteServicio.guardarPcCompleto(miPc);									
		miPc.mostrarCaracteristicas();
		
	}
	
	private void testCreacionPcErroneo_conPcBuilder_DiscosDeMas() {
		System.out.println("*** testCreacionPcErroneo_conPcBuilder_DiscosDeMas ***");
		
		Componente miPc = Componente.getPcBuilder()
				                    .definirId("pc0001")
		                            .definirDescripcion("Laptop 15000 s300")
		                            .definirMarcaYmodelo("Dell", "Terminator")
		                            .agregarDisco("D001", "Disco Seagate", "TechXYZ", "X200", 
		             	                new BigDecimal("1880.00"), new BigDecimal("2000.00"), "1TB")
		                            .agregarMonitor("M001", "Monitor 17 pulgadas", "Sony", "Z9000", 
		                                 new BigDecimal("3200.00"), new BigDecimal("6000.00"))
		                            .agregarTarjetaVideo("C001", "Tarjeta XYZ", "TechBrand", "X200", 
		                                 new BigDecimal("150.00"), new BigDecimal("200.00"), "16GB")
		                            .agregarDisco("D002", "Disco Seagate", "TechXYZJr", "X100", 
			             	                new BigDecimal("1000.00"), new BigDecimal("1600.00"), "500GB")
		                            .agregarDisco("D003", "Disco Xtr 1TB", "Xtr-500", "Xtr", 
			             	                new BigDecimal("2000.00"), new BigDecimal("2600.00"), "1TB")
		                            .build();
		componenteServicio.guardarPcCompleto(miPc);									
		miPc.mostrarCaracteristicas();
		
	}
	
	private void testCreacionPcErroneo_conPcBuilder_SinDiscos() {
		System.out.println("*** testCreacionPcOk_conPcBuilder ***");
		
		Componente miPc = Componente.getPcBuilder()
				                    .definirId("pc0001")
		                            .definirDescripcion("Laptop 15000 s300")
		                            .definirMarcaYmodelo("Dell", "Terminator")
		                            .agregarMonitor("M001", "Monitor 17 pulgadas", "Sony", "Z9000", 
		                                 new BigDecimal("3200.00"), new BigDecimal("6000.00"))
		                            .agregarTarjetaVideo("C001", "Tarjeta XYZ", "TechBrand", "X200", 
		                                 new BigDecimal("150.00"), new BigDecimal("200.00"), "16GB")
		                            .build();
		componenteServicio.guardarPcCompleto(miPc);
		miPc.mostrarCaracteristicas();
		
	}

	private void testGenerarCotizacion() {
		
		ICotizador cotizador = getCotizadorActual();
		
		Componente monitor = Componente.crearMonitor("M001","Monitor 17 pulgadas","Samsung","Goliat-500",
						new BigDecimal(1000), new BigDecimal(2000));
		cotizador.agregarComponente(1, monitor);
		componenteServicio.guardarComponente(monitor);
		
		Componente monitor2 = Componente.crearMonitor("M022","Monitor 15 pulgadas","Sony","VR-30",
				new BigDecimal(1100), new BigDecimal(2000));
		componenteServicio.guardarComponente(monitor2);
		cotizador.agregarComponente(4, monitor2);
		cotizador.agregarComponente(7, monitor2);
		
		Componente disco = Componente.crearDiscoDuro("D-23", "Disco estado sólido", "Seagate", "T-455", new BigDecimal(500), 
				new BigDecimal(1000), "2TB");
		componenteServicio.guardarComponente(disco);
		cotizador.agregarComponente(10, disco);
	
	    Componente tarjeta = Componente.crearTarjetaVideo("C0XY", "Tarjeta THOR", "TechBrand", "X200-34", 
	            new BigDecimal("150.00"), new BigDecimal("300.00"), "8GB");
		componenteServicio.guardarComponente(tarjeta);	
		cotizador.agregarComponente(10, tarjeta);
	    
    	Componente discoPc = Componente.crearDiscoDuro("D001", "Disco Seagate", "TechXYZ", "X200", 
                new BigDecimal("1880.00"), new BigDecimal("2000.00"), "1TB");   
	   	Componente monitorPc = Componente.crearMonitor("M001", "Monitor 17 pulgadas", "Sony", "Z9000", 
	            new BigDecimal("3200.00"), new BigDecimal("6000.00"));   
	    Componente tarjetaPc = Componente.crearTarjetaVideo("C001", "Tarjeta XYZ", "TechBrand", "X200", 
	            new BigDecimal("150.00"), new BigDecimal("200.00"), "16GB");
	    
		Componente miPc = Componente.crearPc("PC011", "Laptop 15000 s300", "Dell", "Terminator",
											List.of(discoPc,monitorPc,tarjetaPc));
		componenteServicio.guardarPcCompleto(miPc);
		cotizador.agregarComponente(1, miPc);
		Cotizacion cotizacion = cotizador.generarCotizacion(null);
		cotizacionServicio.guardarCotizacion(cotizacion);
		cotizacion.emitirComoReporte();
	}

	private static ICotizador getCotizadorActual() {
		return new Cotizador();
	}

}