package mx.com.qtx.cotizadorv1ds.adapters;

import mx.com.qtx.cotizadorv1ds.core.cotizacion.Cotizacion;
import mx.com.qtx.cotizadorv1ds.core.cotizacion.DetalleCotizacion;
import mx.com.qtx.cotizadorv1ds.core.presupuestos.IPresupuesto;

import java.util.HashMap;
import java.util.Map;

public class CotizacionAdapter implements IPresupuesto {

    private Cotizacion cotizacionAdaptee; // El objeto que adaptamos (la cotización)
    private Map<String, DetalleCotizacion> detallesPorId; // Para búsqueda rápida

    public CotizacionAdapter(Cotizacion cotizacionAdaptee) {
        System.out.println("CotizacionAdapter: Adaptando la cotización generada...");
        this.cotizacionAdaptee = cotizacionAdaptee;
        this.detallesPorId = new HashMap<>();
        // Pre-procesar los detalles para fácil acceso por ID
        for (DetalleCotizacion detalle : cotizacionAdaptee.getDetalles()) {
            this.detallesPorId.put(detalle.getIdComponente(), detalle);
        }
        System.out.println("CotizacionAdapter: Adaptación lista.");
    }

    @Override
    public String getDescripcionArticulo(String idArticulo) {
        System.out.println("CotizacionAdapter: getDescripcionArticulo(id: " + idArticulo + ")");
        DetalleCotizacion detalle = detallesPorId.get(idArticulo);
        return (detalle != null) ? detalle.getDescripcion() : "Descripción no encontrada";
    }

    @Override
    public Map<String, Integer> getCantidadesXIdArticulo() {
        System.out.println("CotizacionAdapter: getCantidadesXIdArticulo()" );
        Map<String, Integer> cantidades = new HashMap<>();
        for (DetalleCotizacion detalle : cotizacionAdaptee.getDetalles()) {
            cantidades.put(detalle.getIdComponente(), detalle.getCantidad());
        }
        return cantidades;
    }

    @Override
    public Map<String, Object> getDatosArticulo(String idArticulo) {
        System.out.println("CotizacionAdapter: getDatosArticulo(id: " + idArticulo + ")");
        DetalleCotizacion detalle = detallesPorId.get(idArticulo);
        if (detalle == null) {
            return new HashMap<>(); // Vacío si no se encuentra
        }
        // Devolvemos un mapa con los datos que podrían ser útiles del detalle
        // Esto es flexible según lo que realmente necesite IPresupuesto
        Map<String, Object> datos = new HashMap<>();
        datos.put("descripcion", detalle.getDescripcion());
        datos.put("cantidad", detalle.getCantidad());
        datos.put("precioBase", detalle.getPrecioBase());
        datos.put("importeTotalLinea", detalle.getImporteCotizado());
        // Se podrían añadir más datos si fueran necesarios
        return datos;
    }

    // Método adicional para acceder a la cotización original si fuera necesario
    public Cotizacion getCotizacionOriginal() {
        return cotizacionAdaptee;
    }
} 