package mx.com.qtx.cotizadorv1ds.pedidos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import mx.com.qtx.cotizadorv1ds.pedidos.excepciones.PresupuestoNoCargadoExcepcion;
import mx.com.qtx.cotizadorv1ds.pedidos.excepciones.ProveedorNoExisteExcepcion;

/**
 * Gestiona la creación y manejo de pedidos a partir de presupuestos.
 * Es responsable de recibir presupuestos, interactuar con un repositorio simulado de proveedores
 * y generar objetos Pedido basados en la información del presupuesto.
 */
public class GestorPedidos {
    private IPresupuesto presupuestoActual; // El presupuesto cargado
    private Map<String, Proveedor> proveedores; // Simula un repositorio de proveedores
    private Pedido pedido;

    /**
     * Construye una nueva instancia de GestorPedidos.
     * Inicializa el repositorio simulado de proveedores con datos de ejemplo.
     */
    public GestorPedidos() {
        this.proveedores = new HashMap<>();
        // Simular algunos proveedores
        proveedores.put("PROV001", new Proveedor("PROV001", "Proveedor Alfa", "Alfa SA de CV"));
        proveedores.put("PROV002", new Proveedor("PROV002", "Proveedor Beta", "Beta SA de CV"));        
        proveedores.put("PROV003", new Proveedor("PROV003", "Proveedor Gamma", "Gamma SA de CV"));        
    }

    /**
     * Agrega (o reemplaza) el presupuesto actual que se utilizará para generar pedidos.
     * Realiza una validación básica o procesamiento del presupuesto recibido.
     *
     * @param presupuesto La instancia de IPresupuesto a utilizar.
     */
    public void agregarPresupuesto(IPresupuesto presupuesto) throws PresupuestoNoCargadoExcepcion {
        System.out.println("GestorPedidos: Recibiendo presupuesto...");
        if(presupuesto == null){
            throw new PresupuestoNoCargadoExcepcion();
        }
        this.presupuestoActual = presupuesto;
        Map<String, Integer> cantidades = presupuesto.getCantidadesXIdArticulo();
        
    }

    /**
     * Genera un nuevo pedido basado en el presupuesto actual cargado y los datos proporcionados.
     * Extrae la información necesaria del presupuesto, busca al proveedor y crea el objeto Pedido
     * con sus correspondientes detalles.
     *
     * @param cveProveedor Clave del proveedor al que se le hará el pedido.
     * @param numPedido Número que identificará al nuevo pedido.
     * @param nivelSurtido Nivel inicial de surtido para el pedido.
     * @param fechaEmision Fecha de emisión del pedido.
     * @param fechaEntrega Fecha de entrega programada para el pedido.
     * @throws ProveedorNoExisteExcepcion Si la clave de proveedor no corresponde a un proveedor conocido.
     * @throws PresupuestoNoCargadoExcepcion Si no se ha cargado un presupuesto antes de llamar a este método.
     */
    public void generarPedido(String cveProveedor,int numPedido, int nivelSurtido,
        LocalDate fechaEmision, LocalDate fechaEntrega) throws ProveedorNoExisteExcepcion, 
            PresupuestoNoCargadoExcepcion {
        if (this.presupuestoActual == null) {
            throw new PresupuestoNoCargadoExcepcion();
        }
        Proveedor prov = proveedores.get(cveProveedor);
        if (prov == null) {
            throw new ProveedorNoExisteExcepcion(cveProveedor);
        }
        // Lógica para extraer datos del presupuesto y crear detalles del pedido
        Pedido nuevoPedido = new Pedido(numPedido, fechaEmision, 
            fechaEntrega, nivelSurtido, prov);
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
            if (datosArticulo.containsKey("importeTotalLinea") 
                    && datosArticulo.get("importeTotalLinea") instanceof BigDecimal ) {
                 // Calcular precio unitario si solo tenemos el total
                 importeTotal = (BigDecimal) datosArticulo.get("importeTotalLinea");
            }

            nuevoPedido.agregarDetallePedido(idArticulo, descripcion, cantidad, 
                precioUnitario, importeTotal);
            
        }
        pedido = nuevoPedido;
        // Aquí iría la lógica para persistir el pedido, enviarlo, etc.
        System.out.println("GestorPedidos: Pedido generado exitosamente.");
    }

    /**
     * Imprime en la consola la representación en cadena del último pedido generado.
     * Utiliza el método toString() de la clase Pedido para formatear la salida.
     */
    public void imprimirPedido() {        
        System.out.println(" ");
        System.out.println(pedido.toString());
    }
} 