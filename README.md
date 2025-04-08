# Cotizador y Generador de Pedidos Modulo 2

Este proyecto implementa un sistema básico para cotizar componentes (como hardware de computadora) y generar pedidos basados en esas cotizaciones.

## Requisitos

*   Java Development Kit (JDK) 17 o superior.

## Funcionalidades Principales

1.  **Cotización de Componentes:**
    *   Permite definir y agregar diferentes tipos de componentes (Monitores, Discos Duros, Tarjetas de Video, PCs ensambladas) a una cotización.
    *   Calcula costos y precios de venta para cada componente y para la cotización total.
    *   Puede manejar componentes simples y componentes compuestos (como una PC que incluye otros componentes).
    *   La clase principal para interactuar con esta funcionalidad es `mx.com.qtx.cotizadorv1ds.core.ICotizador` (obtenida a través de `mx.com.qtx.cotizadorv1ds.config.Config`).

2.  **Generación de Pedidos desde Cotizaciones:**
    *   Convierte una `Cotizacion` generada en un formato de `IPresupuesto` utilizando un patrón Adapter (`mx.com.qtx.cotizadorv1ds.core.CotizacionPresupuestoAdapter`).
    *   Utiliza un `GestorPedidos` (`mx.com.qtx.cotizadorv1ds.pedidos.GestorPedidos`) para procesar el presupuesto adaptado y generar un `Pedido` (`mx.com.qtx.cotizadorv1ds.pedidos.Pedido`).
    *   Asocia el pedido a un `Proveedor` (`mx.com.qtx.cotizadorv1ds.pedidos.Proveedor`) específico.
    *   Maneja excepciones básicas como `ProveedorNoExisteExcepcion` y `PresupuestoNoCargadoExcepcion`.

3.  **Cálculo Flexible de Impuestos (Patrón Bridge):**
    *   Implementa un patrón Bridge para desacoplar la *abstracción* del tipo de impuesto (p.ej., local, federal - `CalculoImpuestoLocal`, `CalculoImpuestoFederal`) de la *implementación* específica por país o región (p.ej., `CalculoImpuestoMexico`, `CalculoImpuestosUsa`).
    *   Esto permite configurar y aplicar diferentes combinaciones de impuestos (ej. IVA + ISLR en México, Sales Tax en USA) de forma dinámica al generar la cotización.
    *   La interfaz base es `mx.com.qtx.cotizadorv1ds.impuestos.CalculoImpuesto`.

## Pruebas Cruciales: `CotizacionAdapterBridgeTest.java`

La clase `src/mx/com/qtx/cotizadorv1ds/casosDeUso/CotizacionAdapterBridgeTest.java` sirve como punto de entrada principal para probar y demostrar el flujo completo del sistema:

*   **`obtenerCotizacionMock()`:** Crea una cotización de ejemplo (`Cotizacion`) agregando diversos componentes simples y compuestos.
*   **`testGenerarCotizacion()`:**
    1.  Obtiene la cotización de ejemplo.
    2.  Crea una instancia de `ManejadorCreacionPedidos`.
    3.  Llama al método `crearPedidoDesdeCotizacion` para generar un pedido basado en la cotización, utilizando el adaptador internamente.
    4.  Repite el paso 3 para otro proveedor.
    5.  Imprime los detalles de los pedidos generados en la consola, mostrando la información formateada.

Para ejecutar la prueba y ver el sistema en acción, simplemente ejecuta el método `main` de la clase `CotizacionAdapterBridgeTest.java`.

## Estructura del Proyecto (Paquetes Principales)

*   **`mx.com.qtx.cotizadorv1ds.config`:** Configuración del sistema (p. ej., selección de la implementación del cotizador).
*   **`mx.com.qtx.cotizadorv1ds.core`:** Clases centrales del módulo de cotización (Cotizacion, ICotizador, Adapter).
*   **`mx.com.qtx.cotizadorv1ds.core.componentes`:** Clases que representan los diferentes tipos de componentes cotizables.
*   **`mx.com.qtx.cotizadorv1ds.impuestos`:** Clases relacionadas con el cálculo de impuestos (Abstracción e Implementaciones del patrón Bridge).
*   **`mx.com.qtx.cotizadorv1ds.pedidos`:** Clases relacionadas con la gestión y creación de pedidos (Pedido, DetallePedido, GestorPedidos, Proveedor, IPresupuesto, Manejador).
*   **`mx.com.qtx.cotizadorv1ds.pedidos.excepciones`:** Excepciones personalizadas para el módulo de pedidos.
*   **`mx.com.qtx.cotizadorv1ds.casosDeUso`:** Clases que demuestran o prueban casos de uso específicos, como `CotizacionAdapterBridgeTest`. 