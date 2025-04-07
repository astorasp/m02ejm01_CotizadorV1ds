package mx.com.qtx.cotizadorv1ds.cotizadorA;

import mx.com.qtx.cotizadorv1ds.core.cotizacion.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Implementación simple del cotizador
public class CotizadorImplA implements ICotizador {

    private Map<Componente, Integer> componentesAgregados;
    private int sigNumDetalle = 1;

    public CotizadorImplA() {
        this.componentesAgregados = new HashMap<>();
    }

    @Override
    public void agregarComponente(int cantidad, Componente comp) {
        System.out.println("CotizadorImplA: Agregando " + cantidad + " de " + comp.getDescripcion() + " (ID: " + comp.getIdComponente() + ")");
        this.componentesAgregados.put(comp, this.componentesAgregados.getOrDefault(comp, 0) + cantidad);
    }

    @Override
    public void eliminarComponente(String idComponente) {
        System.out.println("CotizadorImplA: Intentando eliminar componente con ID " + idComponente);
        // Buscar el componente por ID y eliminarlo. Requiere que Componente implemente equals/hashCode correctamente o buscar por ID.
        Componente aEliminar = null;
        for (Componente c : componentesAgregados.keySet()) {
            if (c.getIdComponente().equals(idComponente)) {
                aEliminar = c;
                break;
            }
        }
        if (aEliminar != null) {
            componentesAgregados.remove(aEliminar);
            System.out.println("CotizadorImplA: Componente " + idComponente + " eliminado.");
        } else {
            System.out.println("CotizadorImplA: Componente " + idComponente + " no encontrado para eliminar.");
        }
    }

    @Override
    public void listarComponentes() {
        System.out.println("CotizadorImplA: Listando componentes agregados:");
        if (componentesAgregados.isEmpty()) {
            System.out.println("  (Ninguno)");
            return;
        }
        componentesAgregados.forEach((comp, cant) -> {
            System.out.printf("  - %s (ID: %s), Cantidad: %d%n", comp.getDescripcion(), comp.getIdComponente(), cant);
        });
    }

    @Override
    public Cotizacion generarCotizacion() {
        System.out.println("CotizadorImplA: Generando cotización...");
        Cotizacion cotizacion = new Cotizacion(LocalDate.now());
        sigNumDetalle = 1; // Reiniciar contador para cada cotización

        componentesAgregados.forEach((comp, cant) -> {
            // Simular obtención de precio base (podría venir de una base de datos)
            BigDecimal precioBase = obtenerPrecioBaseSimulado(comp.getIdComponente());
            DetalleCotizacion detalle = new DetalleCotizacion(
                    sigNumDetalle++,
                    comp.getDescripcion(),
                    cant,
                    precioBase,
                    comp.getIdComponente()
            );
            cotizacion.agregarDetalle(detalle);
        });

        System.out.println("CotizadorImplA: Cotización generada.");
        // Limpiar componentes después de generar la cotización
        this.componentesAgregados.clear();
        this.sigNumDetalle = 1;
        return cotizacion;
    }

    // Método simulado para obtener precios
    private BigDecimal obtenerPrecioBaseSimulado(String idComponente) {
        // Precios de ejemplo
        switch (idComponente) {
            case "CPU001": return new BigDecimal("250.00");
            case "MEM002": return new BigDecimal("85.50");
            case "SSD003": return new BigDecimal("120.75");
            default: return new BigDecimal("10.00"); // Precio por defecto
        }
    }
} 