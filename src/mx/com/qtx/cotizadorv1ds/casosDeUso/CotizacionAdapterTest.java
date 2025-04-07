package mx.com.qtx.cotizadorv1ds.casosDeUso;

import mx.com.qtx.cotizadorv1ds.adapters.CotizacionAdapter;
import mx.com.qtx.cotizadorv1ds.core.cotizacion.Componente;
import mx.com.qtx.cotizadorv1ds.core.cotizacion.Cotizacion;
import mx.com.qtx.cotizadorv1ds.core.cotizacion.ICotizador;
import mx.com.qtx.cotizadorv1ds.core.pedidos.GestorPedidos;
import mx.com.qtx.cotizadorv1ds.core.pedidos.Pedido;
import mx.com.qtx.cotizadorv1ds.core.presupuestos.IPresupuesto;
import mx.com.qtx.cotizadorv1ds.cotizadorA.CotizadorImplA;

public class CotizacionAdapterTest {

    public static void main(String[] args) {
        System.out.println("--- DEMO PATRÓN ADAPTER ---");

        // 1. Crear instancias de los componentes
        ICotizador miCotizador = new CotizadorImplA();
        GestorPedidos miGestorPedidos = new GestorPedidos();

        // 2. Usar el Cotizador para agregar componentes
        System.out.println("\n--- Paso 1: Preparar Cotización ---");
        miCotizador.agregarComponente(1, new Componente("CPU001", "Procesador Intel Core i7"));
        miCotizador.agregarComponente(2, new Componente("MEM002", "Memoria RAM 16GB DDR4"));
        miCotizador.agregarComponente(1, new Componente("SSD003", "Disco Estado Sólido 1TB NVMe"));
        miCotizador.listarComponentes();

        // 3. Generar la Cotizacion
        Cotizacion cotizacionGenerada = miCotizador.generarCotizacion();
        cotizacionGenerada.emitirComoReporte(); // Ver la cotización original

        // 4. Crear el Adapter para que GestorPedidos pueda usar la Cotizacion
        System.out.println("\n--- Paso 2: Adaptar Cotización a IPresupuesto ---");
        IPresupuesto presupuestoAdaptado = new CotizacionAdapter(cotizacionGenerada);

        // 5. Pasar el presupuesto adaptado al GestorPedidos
        System.out.println("\n--- Paso 3: Usar Presupuesto Adaptado en GestorPedidos ---");
        miGestorPedidos.agregarPresupuesto(presupuestoAdaptado);

        // 6. Generar un pedido usando el presupuesto adaptado
        System.out.println("\n--- Paso 4: Generar Pedido desde Presupuesto Adaptado ---");
        Pedido pedidoGenerado = miGestorPedidos.generarPedido("PROV001");

        if (pedidoGenerado != null) {
            System.out.println("\nPedido final generado: " + pedidoGenerado);
        } else {
            System.out.println("\nNo se pudo generar el pedido.");
        }

        System.out.println("\n--- FIN DEMO --- ");
    }
} 