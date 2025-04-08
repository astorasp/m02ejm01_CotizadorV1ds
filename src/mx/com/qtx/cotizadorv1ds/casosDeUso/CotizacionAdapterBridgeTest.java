package mx.com.qtx.cotizadorv1ds.casosDeUso;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import mx.com.qtx.cotizadorv1ds.config.Config;
import mx.com.qtx.cotizadorv1ds.core.Cotizacion;
import mx.com.qtx.cotizadorv1ds.core.ICotizador;
import mx.com.qtx.cotizadorv1ds.core.componentes.Componente;
import mx.com.qtx.cotizadorv1ds.impuestos.CalculoImpuesto;
import mx.com.qtx.cotizadorv1ds.impuestos.CalculoImpuestoFederal;
import mx.com.qtx.cotizadorv1ds.impuestos.CalculoImpuestoLocal;
import mx.com.qtx.cotizadorv1ds.impuestos.CalculoImpuestoMexico;
import mx.com.qtx.cotizadorv1ds.impuestos.CalculoImpuestosUsa;
import mx.com.qtx.cotizadorv1ds.pedidos.ManejadorCreacionPedidos;

/**
 * Clase de prueba que demuestra la integración de los módulos de cotización,
 * cálculo de impuestos y generación de pedidos, utilizando patrones Adapter y Bridge.
 *
 * <p>Esta clase verifica:</p>
 * <ul>
 *     <li>La conversión de una {@link Cotizacion} a un {@link mx.com.qtx.cotizadorv1ds.pedidos.IPresupuesto}
 *         mediante el {@link mx.com.qtx.cotizadorv1ds.core.CotizacionPresupuestoAdapter}.</li>
 *     <li>La aplicación flexible de esquemas de impuestos (locales/federales, México/USA)
 *         usando el patrón Bridge ({@link CalculoImpuesto}, {@link CalculoImpuestoLocal},
 *         {@link CalculoImpuestoFederal}, {@link CalculoImpuestoMexico}, {@link CalculoImpuestosUsa}).</li>
 *     <li>La creación de pedidos a través del {@link ManejadorCreacionPedidos}.</li>
 *     <li>El manejo de casos de error como proveedores inexistentes o cotizaciones nulas.</li>
 * </ul>
 *
 * @see mx.com.qtx.cotizadorv1ds.core.CotizacionPresupuestoAdapter
 * @see ManejadorCreacionPedidos
 * @see CalculoImpuesto
 * @see Cotizacion
 * @see ICotizador
 */
public class CotizacionAdapterBridgeTest {

    /**
     * Punto de entrada principal para ejecutar las pruebas de integración.
     * <p>
     * Ejecuta diferentes escenarios de prueba:
     * <ul>
     *     <li>Intento de creación de pedido con proveedor inexistente.</li>
     *     <li>Intento de creación de pedido con cotización nula.</li>
     *     <li>Creación exitosa de pedidos con impuestos de México (solo federal).</li>
     *     <li>Creación exitosa de pedidos con impuestos de USA (local y federal).</li>
     * </ul>
     * Imprime los resultados de cada prueba en la consola.
     *
     * @param args Argumentos de línea de comandos (no utilizados en esta implementación).
     */
    public static void main(String[] args) {
        System.out.println("\n==== Probando con Proveedor Inexistente ====\n");
		try{
			testGenerarPedidoProveedorInexistente(obtenerCalculoImpuestosMexico());
			imprimirResultado("testGenerarPedidoProveedorInexistente", false); // Se espera excepción
		}
		catch(Exception e){
			// La excepción es esperada en este caso debido al proveedor inexistente.
			// El manejador debería haberla gestionado internamente (e.g., loggeado).
			// Aquí se considera éxito si el flujo principal no falla abruptamente.
			System.out.println("    (Flujo continuó tras intento con proveedor inexistente: " + e.getClass().getSimpleName() + ")");
			imprimirResultado("testGenerarPedidoProveedorInexistente", true); // Éxito si maneja la excepción
		}

        System.out.println("\n==== Probando con Cotización Nula ====");
        try{
			testGenerarPedidoCotizacionNula();
			imprimirResultado("testGenerarPedidoCotizacionNula", false); // Fallo si no lanza excepción
		}
		catch(Exception e){
			// Se espera una excepción (NullPointerException o similar) al pasar null.
			System.out.println("    (Se capturó la excepción esperada: " + e.getClass().getSimpleName() + ")");
			imprimirResultado("testGenerarPedidoCotizacionNula", true); // Éxito si lanza excepción esperada
		}
		System.out.println("\n==== Probando Gestor de pedidos happy path con impuestos de México ====");
		testGenerarCotizacion(obtenerCalculoImpuestosMexicoFederal());

        System.out.println("\n==== Probando Gestor de pedidos happy path con impuestos de USA ====\n");
        testGenerarCotizacion(obtenerCalculoImpuestosUsa());
    }

    /**
     * Realiza una prueba completa ("happy path") de generación de cotización y pedidos.
     * <p>
     * Pasos:
     * <ol>
     *     <li>Obtiene un cotizador con componentes de ejemplo usando {@link #obtenerCotizador()}.</li>
     *     <li>Genera una {@link Cotizacion} aplicando la lista de impuestos proporcionada.</li>
     *     <li>Emite la cotización como reporte a la consola.</li>
     *     <li>Crea una instancia de {@link ManejadorCreacionPedidos}.</li>
     *     <li>Llama a {@link ManejadorCreacionPedidos#crearPedidoDesdeCotizacion(Cotizacion, String, int, int, LocalDate, LocalDate)}
     *         para generar un primer pedido para "PROV001".</li>
     *     <li>Imprime los pedidos actuales del manejador.</li>
     *     <li>Llama nuevamente a {@code crearPedidoDesdeCotizacion} para generar un segundo pedido para "PROV002".</li>
     *     <li>Imprime los pedidos actualizados del manejador.</li>
     * </ol>
     *
     * @param impuestos Lista de {@link CalculoImpuesto} a aplicar en la cotización.
     *                  Define qué impuestos (y de qué jurisdicción) se calcularán.
     * @see #obtenerCotizador()
     * @see ICotizador#generarCotizacion(List)
     * @see Cotizacion#emitirComoReporte()
     * @see ManejadorCreacionPedidos#crearPedidoDesdeCotizacion(Cotizacion, String, int, int, LocalDate, LocalDate)
     * @see ManejadorCreacionPedidos#imprimirPedidos()
     */
	private static void testGenerarCotizacion(List<CalculoImpuesto> impuestos) {
        ICotizador cotizador = obtenerCotizador();
        Cotizacion cotizacion = cotizador.generarCotizacion(impuestos);
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
     * Prueba el escenario donde se intenta crear un pedido para un proveedor inexistente.
     * <p>
     * Genera una cotización y luego intenta crear un pedido usando la clave "PROV999",
     * la cual se asume que no está registrada en el sistema (simulado por el {@code GestorPedidos}).
     * Se espera que el {@link ManejadorCreacionPedidos} maneje internamente la excepción
     * {@code ProveedorNoExisteExcepcion} lanzada por el gestor subyacente, posiblemente
     * registrando el error pero permitiendo que el flujo de la aplicación continúe.
     * </p>
     *
     * @param impuestos Lista de {@link CalculoImpuesto} a aplicar en la cotización.
     * @see ManejadorCreacionPedidos#crearPedidoDesdeCotizacion(Cotizacion, String, int, int, LocalDate, LocalDate)
     * @see mx.com.qtx.cotizadorv1ds.pedidos.excepciones.ProveedorNoExisteExcepcion
     */
    private static void testGenerarPedidoProveedorInexistente(List<CalculoImpuesto> impuestos) {
        ICotizador cotizador = obtenerCotizador();
        Cotizacion cotizacion = cotizador.generarCotizacion(impuestos);
        ManejadorCreacionPedidos manejador = new ManejadorCreacionPedidos();
        // Intentar crear un pedido con un proveedor que no existe ("PROV999")
        manejador.crearPedidoDesdeCotizacion(cotizacion, "PROV999",
             3, 1, LocalDate.now(), LocalDate.now().plusDays(5));
        // Esta línea podría no imprimir nada relevante si el pedido no se creó debido al error.
        manejador.imprimirPedidos(); // Verificar si se imprime algo o si el manejador lo omitió.
    }

    /**
     * Prueba el escenario donde se intenta crear un pedido pasando {@code null} como cotización.
     * <p>
     * Se espera que al llamar a {@link ManejadorCreacionPedidos#crearPedidoDesdeCotizacion(Cotizacion, String, int, int, LocalDate, LocalDate)}
     * con el primer argumento nulo, se lance una excepción, típicamente una
     * {@link NullPointerException}, ya que el manejador intentará acceder a la cotización inexistente.
     * La prueba se considera exitosa si esta excepción es capturada por el bloque {@code try-catch}
     * en el método {@link #main(String[])}.
     * </p>
     *
     * @throws NullPointerException u otra excepción si se intenta operar sobre la cotización nula.
     * @see ManejadorCreacionPedidos#crearPedidoDesdeCotizacion(Cotizacion, String, int, int, LocalDate, LocalDate)
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
     * Crea y devuelve una instancia de {@link ICotizador} configurada con datos de ejemplo (mock).
     * <p>
     * Agrega varios componentes como monitores, discos duros, tarjetas de video y una PC ensamblada
     * utilizando los métodos de fábrica de {@link Componente} y el método
     * {@link ICotizador#agregarComponente(int, Componente)}.
     * </p>
     *
     * @return Una instancia de {@link ICotizador} lista para generar una cotización con componentes de prueba.
     * @see #getCotizadorActual()
     * @see ICotizador#agregarComponente(int, Componente)
     * @see Componente#crearMonitor(String, String, String, String, BigDecimal, BigDecimal)
     * @see Componente#crearDiscoDuro(String, String, String, String, BigDecimal, BigDecimal, String)
     * @see Componente#crearTarjetaVideo(String, String, String, String, BigDecimal, BigDecimal, String)
     * @see Componente#crearPc(String, String, String, String, List)
     */
	private static ICotizador obtenerCotizador()
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
		return cotizador;
	}

    /**
     * Obtiene la implementación concreta de {@link ICotizador} según la configuración actual del sistema.
     * <p>
     * Delega la obtención a la clase de configuración {@link Config}.
     * </p>
     *
     * @return Una instancia de {@link ICotizador} (la implementación específica es definida en {@code Config}).
     * @see Config#getCotizador()
     */
    private static ICotizador getCotizadorActual() {
		return Config.getCotizador();
	}

    /**
     * Obtiene una lista de cálculos de impuestos configurada para México (Local y Federal).
     * <p>
     * Combina {@link CalculoImpuestoLocal} y {@link CalculoImpuestoFederal} utilizando
     * la implementación {@link CalculoImpuestoMexico}.
     * </p>
     *
     * @return Una lista de {@link CalculoImpuesto} que representa los impuestos locales y federales de México.
     * @see CalculoImpuestoLocal
     * @see CalculoImpuestoFederal
     * @see CalculoImpuestoMexico
     */
    private static List<CalculoImpuesto> obtenerCalculoImpuestosMexico() {
        return List.of(new CalculoImpuestoLocal(new CalculoImpuestoMexico()),
            new CalculoImpuestoFederal(new CalculoImpuestoMexico()));
    }

    /**
     * Obtiene una lista de cálculos de impuestos configurada solo para impuestos Federales de México.
     * <p>
     * Utiliza {@link CalculoImpuestoFederal} con la implementación {@link CalculoImpuestoMexico}.
     * </p>
     *
     * @return Una lista de {@link CalculoImpuesto} que representa únicamente los impuestos federales de México.
     * @see CalculoImpuestoFederal
     * @see CalculoImpuestoMexico
     */
    private static List<CalculoImpuesto> obtenerCalculoImpuestosMexicoFederal() {
        return List.of(new CalculoImpuestoFederal(new CalculoImpuestoMexico()));
    }

    /**
     * Obtiene una lista de cálculos de impuestos configurada para USA (Local y Federal).
     * <p>
     * Combina {@link CalculoImpuestoLocal} y {@link CalculoImpuestoFederal} utilizando
     * la implementación {@link CalculoImpuestosUsa}.
     * </p>
     *
     * @return Una lista de {@link CalculoImpuesto} que representa los impuestos locales y federales de USA.
     * @see CalculoImpuestoLocal
     * @see CalculoImpuestoFederal
     * @see CalculoImpuestosUsa
     */
    private static List<CalculoImpuesto> obtenerCalculoImpuestosUsa() {
        return List.of(new CalculoImpuestoLocal(new CalculoImpuestosUsa()),
            new CalculoImpuestoFederal(new CalculoImpuestosUsa()));
    }

	/**
	 * Imprime un mensaje simple en la consola indicando el resultado (éxito o fallo) de una prueba.
	 *
	 * @param nombrePrueba El nombre descriptivo de la prueba ejecutada.
	 * @param exito        {@code true} si la prueba fue exitosa, {@code false} si falló.
	 */
	private static void imprimirResultado(String nombrePrueba, boolean exito) {
        String estado = exito ? "✅ Éxito" : "❌ Fallo";
        System.out.println(estado + " - " + nombrePrueba);
    }
} 