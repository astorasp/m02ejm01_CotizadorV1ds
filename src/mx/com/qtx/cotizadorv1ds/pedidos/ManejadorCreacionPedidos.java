package mx.com.qtx.cotizadorv1ds.pedidos;

import java.time.LocalDate;

import mx.com.qtx.cotizadorv1ds.core.Cotizacion;
import mx.com.qtx.cotizadorv1ds.core.CotizacionPresupuestoAdapter;

public class ManejadorCreacionPedidos {
    private GestorPedidos gestorPedidos;
    public ManejadorCreacionPedidos() {
        this.gestorPedidos = new GestorPedidos();
    }
    public void crearPedidoDesdeCotizacion(Cotizacion cotizacion, String cveProveedor, 
                                            int numPedido, int nivelSurtido, 
                                            LocalDate fechaEmision, LocalDate fechaEntrega) {
        try {
            System.out.println("Iniciando proceso para generar pedido desde cotización: " + cotizacion.getNum());
            // 1. Adaptar la cotización a un presupuesto
            IPresupuesto presupuestoAdaptado = new CotizacionPresupuestoAdapter(cotizacion);
            // 2. Agregar el presupuesto al gestor
            gestorPedidos.agregarPresupuesto(presupuestoAdaptado);
            // 3. Generar el pedido
            gestorPedidos.generarPedido(cveProveedor, numPedido, nivelSurtido, 
                fechaEmision, fechaEntrega);
            System.out.println("Pedido generado exitosamente para cotización: " + cotizacion.getNum());
        } 
        catch (Exception e) {
             System.err.println("Error inesperado al generar pedido: " + e.getMessage());
             // Lanzar excepción, loggear, etc.
        }
    }

    public void imprimirPedidos() {
        gestorPedidos.imprimirPedido();
    }
}
