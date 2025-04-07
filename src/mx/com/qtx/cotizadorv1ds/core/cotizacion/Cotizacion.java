package mx.com.qtx.cotizadorv1ds.core.cotizacion;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cotizacion {
    private LocalDate fecha;
    private BigDecimal total;
    private List<DetalleCotizacion> detalles;

    public Cotizacion(LocalDate fecha) {
        this.fecha = fecha;
        this.total = BigDecimal.ZERO;
        this.detalles = new ArrayList<>();
    }

    public void agregarDetalle(DetalleCotizacion detalle) {
        this.detalles.add(detalle);
        this.total = this.total.add(detalle.getImporteCotizado());
    }

    // Getters
    public LocalDate getFecha() {
        return fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public List<DetalleCotizacion> getDetalles() {
        // Devolver una copia inmutable para proteger la lista interna
        return new ArrayList<>(detalles); 
    }

    // Métodos adicionales como emitirComoReporte, emitirJson, emitirXml si son necesarios
    public void emitirComoReporte() {
        System.out.println("--- Reporte de Cotización ---");
        System.out.println("Fecha: " + fecha);
        System.out.println("Total: " + total);
        System.out.println("Detalles:");
        for (DetalleCotizacion det : detalles) {
            System.out.printf("  Item %d: %s (%d) - Importe: %.2f (Base: %.2f, ID: %s)%n",
                    det.getNum(), det.getDescripcion(), det.getCantidad(),
                    det.getImporteCotizado(), det.getPrecioBase(), det.getIdComponente());
        }
        System.out.println("-----------------------------");
    }

    // emitirJson() y emitirXml() podrían usar librerías como Jackson o JAXB
} 