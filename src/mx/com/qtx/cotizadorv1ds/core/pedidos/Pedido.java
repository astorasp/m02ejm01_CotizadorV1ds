package mx.com.qtx.cotizadorv1ds.core.pedidos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private long numPedido;
    private LocalDate fechaEmision;
    private LocalDate fechaEntrega;
    private float nivelSurtido; // Podría ser un enum o una clase más compleja
    private Proveedor proveedor; // Relación con el proveedor
    private List<DetallePedido> detallesPedido; // Almacena los datos copiados

    // Constructor modificado para recibir la lista de detalles
    public Pedido(long numPedido, LocalDate fechaEmision, LocalDate fechaEntrega, Proveedor proveedor, List<DetallePedido> detalles) {
        this.numPedido = numPedido;
        this.fechaEmision = fechaEmision;
        this.fechaEntrega = fechaEntrega;
        this.proveedor = proveedor;
        this.detallesPedido = (detalles != null) ? new ArrayList<>(detalles) : new ArrayList<>(); // Copia defensiva
        this.nivelSurtido = 0.0f; // Inicial
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

    public float getNivelSurtido() {
        return nivelSurtido;
    }

    public void setNivelSurtido(float nivelSurtido) {
        this.nivelSurtido = nivelSurtido;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public List<DetallePedido> getDetallesPedido() {
        return new ArrayList<>(detallesPedido); // Devolver copia defensiva
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido [numPedido=").append(numPedido)
          .append(", fechaEmision=").append(fechaEmision)
          .append(", fechaEntrega=").append(fechaEntrega)
          .append(", nivelSurtido=").append(nivelSurtido)
          .append(", proveedor=").append(proveedor != null ? proveedor.getCve() : "N/A")
          .append(", detalles=").append(detallesPedido.size()).append(" items]");
        return sb.toString();
    }
}
