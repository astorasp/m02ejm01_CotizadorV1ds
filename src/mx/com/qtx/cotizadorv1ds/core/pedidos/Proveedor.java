package mx.com.qtx.cotizadorv1ds.core.pedidos;

public class Proveedor {
    private String cve;
    private String nombre;
    private String razonSocial;

    public Proveedor(String cve, String nombre, String razonSocial) {
        this.cve = cve;
        this.nombre = nombre;
        this.razonSocial = razonSocial;
    }

    // Getters
    public String getCve() {
        return cve;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRazonSocial() {
        return razonSocial;
    }
} 