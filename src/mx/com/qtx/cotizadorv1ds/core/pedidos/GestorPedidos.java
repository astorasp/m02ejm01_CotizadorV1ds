package mx.com.qtx.cotizadorv1ds.core.pedidos;

import mx.com.qtx.cotizadorv1ds.core.presupuestos.IPresupuesto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorPedidos {
    private IPresupuesto presupuestoActual; // El presupuesto cargado
    private Map<String, Proveedor> proveedores; // Simula un repositorio de proveedores
    private List<Pedido> pedidos;

    public GestorPedidos() {
        this.proveedores = new HashMap<>();
        this.pedidos = new ArrayList<>();
        // Simular algunos proveedores
        proveedores.put("PROV001", new Proveedor("PROV001", "Proveedor Alfa", "Alfa SA de CV"));
        proveedores.put("PROV002", new Proveedor("PROV002", "Proveedor Beta", "Beta SA de CV"));        
        proveedores.put("PROV003", new Proveedor("PROV003", "Proveedor Gamma", "Gamma SA de CV"));        
    }

    public void agregarPresupuesto(IPresupuesto presupuesto) {
        System.out.println("GestorPedidos: Recibiendo presupuesto...");
        // Aquí el gestor valida o procesa el presupuesto si es necesario
        Map<String, Integer> cantidades = presupuesto.getCantidadesXIdArticulo();
        System.out.println("GestorPedidos: Cantidades por artículo recibidas:");
        cantidades.forEach((id, cant) -> {
            String desc = presupuesto.getDescripcionArticulo(id);
            System.out.printf("  - ID: %s, Desc: %s, Cant: %d%n", id, desc, cant);
        });
        this.presupuestoActual = presupuesto;
        System.out.println("GestorPedidos: Presupuesto agregado.");
    }

    public void generarPedido(String cveProveedor,int numPedido, int nivelSurtido,
        LocalDate fechaEmision, LocalDate fechaEntrega) {
        if (this.presupuestoActual == null) {
            System.out.println("GestorPedidos: Error - No hay presupuesto cargado.");
            return;
        }
        Proveedor prov = proveedores.get(cveProveedor);
        if (prov == null) {
            System.out.println("GestorPedidos: Error - Proveedor con clave " + cveProveedor + " no encontrado.");
            return;
        }
        // Lógica para extraer datos del presupuesto y crear detalles del pedido
        System.out.println("GestorPedidos: Extrayendo datos del presupuesto para crear pedido...");
        Pedido nuevoPedido = new Pedido(numPedido, fechaEmision, 
            fechaEntrega, nivelSurtido,prov);
        Map<String, Integer> cantidades = this.presupuestoActual.getCantidadesXIdArticulo();
        for (Map.Entry<String, Integer> entry : cantidades.entrySet()) {
            String idArticulo = entry.getKey();
            Integer cantidad = entry.getValue();
            String descripcion = this.presupuestoActual.getDescripcionArticulo(idArticulo);
            // Obtener datos adicionales (como precio) del método getDatosArticulo
            Map<String, Object> datosArticulo = this.presupuestoActual.getDatosArticulo(idArticulo);
            // Asumimos que 'precioBase' o similar está en los datos. Necesita manejo cuidadoso.
            BigDecimal precioUnitario = BigDecimal.ZERO; // Valor por defecto
            BigDecimal importeTotal = BigDecimal.ZERO;
            if (datosArticulo.containsKey("precioBase") 
                    && datosArticulo.get("precioBase") instanceof BigDecimal) {
                precioUnitario = (BigDecimal) datosArticulo.get("precioBase");
            }
             else if (datosArticulo.containsKey("importeTotalLinea") 
                    && datosArticulo.get("importeTotalLinea") instanceof BigDecimal && cantidad > 0) {
                 // Calcular precio unitario si solo tenemos el total
                 importeTotal = (BigDecimal) datosArticulo.get("importeTotalLinea");
            }

            nuevoPedido.agregarDetallePedido(idArticulo, descripcion, cantidad, 
                precioUnitario, importeTotal);
            pedidos.add(nuevoPedido);
        }
        System.out.println("GestorPedidos: Generando pedido " + nuevoPedido.toString());
        // Aquí iría la lógica para persistir el pedido, enviarlo, etc.
        System.out.println("GestorPedidos: Pedido generado exitosamente.");
    }
} 