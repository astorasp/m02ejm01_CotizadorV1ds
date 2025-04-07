package mx.com.qtx.cotizadorv1ds.pedidos;

import java.math.BigDecimal;

// Clase simple para almacenar los datos de cada línea del pedido,
// copiados desde el presupuesto.
public class DetallePedido {
    private String idArticulo;
    private String descripcion;
    private int cantidad;
    private BigDecimal precioUnitario; // O importe total línea, según necesidad
    private BigDecimal totalCotizado;

    public DetallePedido(String idArticulo, String descripcion, int cantidad
    , BigDecimal precioUnitario, BigDecimal totalCotizado) {
        this.idArticulo = idArticulo;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.totalCotizado = totalCotizado;
    }

    // Getters
    public String getIdArticulo() {
        return idArticulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public BigDecimal getTotalCotizado() {
        return totalCotizado;
    }
    
    public void setTotalCotizado(BigDecimal totalCotizado) {
        this.totalCotizado = totalCotizado;
    }

    @Override
    public String toString() {
        // Formato: | idArticulo | descripcion                      | cant. | precio Unitario |  total Cotizado |
        return String.format("| %-10s | %-30s | %5d | %,15.2f | %,15.2f |", 
                             this.idArticulo, 
                             this.descripcion, 
                             this.cantidad, 
                             this.precioUnitario,
                             this.totalCotizado);
    }
}