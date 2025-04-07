package mx.com.qtx.cotizadorv1ds.pedidos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private long numPedido;
    private LocalDate fechaEmision;
    private LocalDate fechaEntrega;
    private int nivelSurtido; 
    private Proveedor proveedor; // Relación con el proveedor
    private List<DetallePedido> detallesPedido; // Almacena los datos copiados

    private Pedido() {
        this.detallesPedido = new ArrayList<>();
    }

    // Constructor modificado para recibir la lista de detalles
    public Pedido(long numPedido, LocalDate fechaEmision, LocalDate fechaEntrega, 
        int nivelSurtido, Proveedor proveedor) {
        this();
        this.numPedido = numPedido;
        this.fechaEmision = fechaEmision;
        this.fechaEntrega = fechaEntrega;
        this.proveedor = proveedor;
        this.nivelSurtido = nivelSurtido; // Inicial
    }

    // Getters y Setters necesarios...
    public long getNumPedido() {
        return numPedido;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public int getNivelSurtido() {
        return nivelSurtido;
    }

    public void setNivelSurtido(int nivelSurtido) {
        this.nivelSurtido = nivelSurtido;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public List<DetallePedido> getDetallesPedido() {
        return new ArrayList<>(detallesPedido); // Devolver copia defensiva
    }

    public void agregarDetallePedido(String idArticulo, String descripcion, 
            int cantidad, BigDecimal precioUnitario, BigDecimal totalCotizado) {
        DetallePedido detalle = new DetallePedido(idArticulo, descripcion, cantidad, precioUnitario
            , totalCotizado);
        this.detallesPedido.add(detalle);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Datos del Pedido: \n");
        sb.append("=".repeat(30));
        sb.append("\n");
        sb.append("numPedido=").append(numPedido)
          .append("\nfechaEmision=").append(fechaEmision)
          .append("\nfechaEntrega=").append(fechaEntrega)
          .append("\nnivelSurtido=").append(nivelSurtido)
          .append("\nproveedor=").append(proveedor != null ? proveedor.getCve() : "N/A")
          .append("\n").append("=".repeat(30))
          .append("\nDetalle Pedido: \n");
        for (DetallePedido detalle : detallesPedido) {
            sb.append("\n").append(detalle.toString());
        }
        
        
        return sb.toString();
    }
}
