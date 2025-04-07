package mx.com.qtx.cotizadorv1ds.pedidos.excepciones;

public class PresupuestoNoCargadoExcepcion extends Exception {
    public PresupuestoNoCargadoExcepcion() {
        super("No hay presupuesto cargado.");
    }
}
