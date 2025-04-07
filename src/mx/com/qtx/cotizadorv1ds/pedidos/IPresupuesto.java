package mx.com.qtx.cotizadorv1ds.pedidos;

import java.util.Map;

public interface IPresupuesto {
    String getDescripcionArticulo(String idArticulo);
    Map<String, Integer> getCantidadesXIdArticulo();
    Map<String, Object> getDatosArticulo(String idArticulo); // Object es genérico, podría refinarse
} 