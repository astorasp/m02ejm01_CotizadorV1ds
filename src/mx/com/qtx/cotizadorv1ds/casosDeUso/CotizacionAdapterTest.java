package mx.com.qtx.cotizadorv1ds.casosDeUso;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import mx.com.qtx.cotizadorv1ds.config.Config;
import mx.com.qtx.cotizadorv1ds.core.Cotizacion;
import mx.com.qtx.cotizadorv1ds.core.ICotizador;
import mx.com.qtx.cotizadorv1ds.core.componentes.Componente;
import mx.com.qtx.cotizadorv1ds.pedidos.ManejadorCreacionPedidos;

/**
 * Clase de prueba para demostrar y verificar la creación de pedidos a partir de cotizaciones.
 * Utiliza un adaptador ({@link mx.com.qtx.cotizadorv1ds.core.CotizacionPresupuestoAdapter})
 * para convertir una {@link mx.com.qtx.cotizadorv1ds.core.Cotizacion} en un
 * {@link mx.com.qtx.cotizadorv1ds.pedidos.IPresupuesto} y luego utiliza el
 * {@link ManejadorCreacionPedidos} para generar los pedidos correspondientes.
 */
public class CotizacionAdapterTest { 

    /**
     * Punto de entrada principal para ejecutar la prueba.
     * Llama al método que realiza la generación y prueba de los pedidos.
     *
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {        
        System.out.println("\n==== Probando con Proveedor Inexistente ====\n");
		try{
			testGenerarPedidoProveedorInexistente();
			imprimirResultado("testGenerarPedidoProveedorInexistente", false);
		}
		catch(Exception e){
			imprimirResultado("testGenerarPedidoProveedorInexistente", true);
		}

        System.out.println("\n==== Probando con Cotización Nula ====");
        try{
			testGenerarPedidoCotizacionNula();
			imprimirResultado("testGenerarPedidoCotizacionNula", false); // Fallo si no lanza excepción
		}
		catch(Exception e){
			// Se espera una excepción (NullPointerException probablemente) 
			System.out.println("    (Se capturó la excepción esperada: " + e.getClass().getSimpleName() + ")");
			imprimirResultado("testGenerarPedidoCotizacionNula", true); // Éxito si lanza excepción
		}
		System.out.println("\n==== Probando Gestor de pedidos happy path ====");
		testGenerarCotizacion();
    }    

    /**
     * Realiza la prueba principal de generación de pedidos desde una cotización.
     * 1. Obtiene una cotización de prueba usando {@link #obtenerCotizacionMock()}.
     * 2. Crea una instancia de {@link ManejadorCreacionPedidos}.
     * 3. Llama a {@link ManejadorCreacionPedidos#crearPedidoDesdeCotizacion(Cotizacion, String, int, int, LocalDate, LocalDate)}
     *    dos veces con diferentes proveedores y datos de pedido.
     * 4. Imprime los pedidos generados en la consola.
     */
	private static void testGenerarCotizacion() {
		Cotizacion cotizacion = obtenerCotizacionMock();
		cotizacion.emitirComoReporte();
        ManejadorCreacionPedidos manejador = new ManejadorCreacionPedidos();
        manejador.crearPedidoDesdeCotizacion(cotizacion, "PROV001",
             1, 1, LocalDate.now(), LocalDate.now().plusDays(2));
        manejador.imprimirPedidos();

        manejador.crearPedidoDesdeCotizacion(cotizacion,"PROV002", 
            2, 3, LocalDate.now(), LocalDate.now().plusDays(4));
        manejador.imprimirPedidos();
	}

    /**
     * Prueba la generación de un pedido utilizando una clave de proveedor que no existe.
     * Se espera que el ManejadorCreacionPedidos capture la excepción ProveedorNoExisteExcepcion
     * (lanzada por GestorPedidos) y muestre un mensaje de error en la consola.
     */
    private static void testGenerarPedidoProveedorInexistente() {
        Cotizacion cotizacion = obtenerCotizacionMock();
        ManejadorCreacionPedidos manejador = new ManejadorCreacionPedidos();
        // Intentar crear un pedido con un proveedor que no existe ("PROV999")
        manejador.crearPedidoDesdeCotizacion(cotizacion, "PROV999",
             3, 1, LocalDate.now(), LocalDate.now().plusDays(5));
        // Esta línea podría no imprimir nada relevante si el pedido no se creó debido al error.
        manejador.imprimirPedidos();
    }

    /**
     * Prueba la generación de un pedido pasando una cotización nula.
     * Se espera que el ManejadorCreacionPedidos lance alguna excepción 
     * (probablemente NullPointerException) al intentar acceder a la cotización nula.
     */
    private static void testGenerarPedidoCotizacionNula() {
        System.out.println("Ejecutando prueba: testGenerarPedidoCotizacionNula");
        ManejadorCreacionPedidos manejador = new ManejadorCreacionPedidos();
        // Intentar crear un pedido pasando null como cotización
        manejador.crearPedidoDesdeCotizacion(null, "PROV001", // Proveedor válido o cualquiera
             4, 1, LocalDate.now(), LocalDate.now().plusDays(1));
        // No debería llegar aquí si se lanza la excepción esperada
    }

/**
     * Crea y devuelve una instancia de {@link Cotizacion} con datos de ejemplo (mock).
     * Utiliza el {@link ICotizador} configurado para agregar varios componentes (monitores, discos, etc.)
     * y luego genera la cotización final.
     *
     * @return Una instancia de Cotizacion poblada con datos de prueba.
     */
	private static Cotizacion obtenerCotizacionMock()
	{
		ICotizador cotizador = getCotizadorActual();
		
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
	    
		Componente miPc = Componente.crearPc("pc0001", "Laptop 15000 s300", "Dell", "Terminator",
												List.of(discoPc,monitorPc,tarjetaPc));
		cotizador.agregarComponente(1, miPc);
		Cotizacion cotizacion = cotizador.generarCotizacion();		
		return cotizacion;
	}	

    /**
     * Obtiene la implementación actual del cotizador según la configuración.
     * Delega la obtención al método estático {@link Config#getCotizador()}.
     *
     * @return Una instancia de {@link ICotizador}.
     */
    private static ICotizador getCotizadorActual() {
		return Config.getCotizador();
	}

	private static void imprimirResultado(String nombrePrueba, boolean exito) {
        String estado = exito ? "✅ Éxito" : "❌ Fallo";
        System.out.println(estado + " - " + nombrePrueba);
    }
} 