package mx.com.qtx.cotizadorv1ds.pedidos.excepciones;

public class ProveedorNoExisteExcepcion extends Exception {

    public ProveedorNoExisteExcepcion(String cveProveedor) {
        super("Proveedor con clave " + cveProveedor + " no encontrado.");    
    }

}
