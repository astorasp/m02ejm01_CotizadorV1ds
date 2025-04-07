package mx.com.qtx.cotizadorv1ds.core.cotizacion;

public class Componente {
    private String idComponente;
    private String descripcion;
    // Otros atributos relevantes si son necesarios

    public Componente(String idComponente, String descripcion) {
        this.idComponente = idComponente;
        this.descripcion = descripcion;
    }

    public String getIdComponente() {
        return idComponente;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // setters si son necesarios
} 