package mx.com.qtx.cotizadorv1ds.core.cotizacion;

import java.math.BigDecimal;

public class DetalleCotizacion {
    private int num; // número de línea o item
    private String descripcion;
    private int cantidad;
    private BigDecimal importeCotizado; // Costo total por esta línea (cantidad * precio base)
    private BigDecimal precioBase;
    private String idComponente; // Para relacionarlo con el Componente

    public DetalleCotizacion(int num, String descripcion, int cantidad, BigDecimal precioBase, String idComponente) {
        this.num = num;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioBase = precioBase;
        this.idComponente = idComponente;
        this.importeCotizado = precioBase.multiply(BigDecimal.valueOf(cantidad));
    }

    // Getters
    public int getNum() {
        return num;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public BigDecimal getImporteCotizado() {
        return importeCotizado;
    }

    public BigDecimal getPrecioBase() {
        return precioBase;
    }

    public String getIdComponente() {
        return idComponente;
    }

    // Setters si son necesarios (aunque usualmente los detalles son inmutables una vez creados)
} 