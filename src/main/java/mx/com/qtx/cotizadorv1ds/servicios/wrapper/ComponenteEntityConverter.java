package mx.com.qtx.cotizadorv1ds.servicios.wrapper;

import java.math.BigDecimal;

import mx.com.qtx.cotizadorv1ds.core.componentes.Componente;

/**
 * Clase utilitaria para convertir objetos Componente del dominio a entidades Componente de persistencia
 * y viceversa.
 * <p>
 * Proporciona métodos estáticos para facilitar la conversión bidireccional entre los objetos de dominio
 * y las entidades de persistencia, respetando sus respectivas estructuras y asegurando la consistencia
 * de los datos durante el proceso de conversión.
 * </p>
 */
public class ComponenteEntityConverter {
    
    /**
     * Convierte un objeto Componente del dominio (core) a una entidad Componente para persistencia.
     * <p>
     * Este método copia las propiedades básicas del objeto de dominio a una nueva instancia de la entidad,
     * incluyendo identificador, descripción, marca, modelo, costo y precio base.
     * </p>
     * 
     * @param compCore Objeto Componente del dominio a convertir
     * @return Objeto Componente de persistencia con los datos del objeto de dominio, o null si el parámetro de entrada es null
     */
    public static mx.com.qtx.cotizadorv1ds.persistencia.entidades.Componente convertToEntity(
            mx.com.qtx.cotizadorv1ds.core.componentes.Componente compCore) {
        
        if (compCore == null) {
            return null;
        }
        
        mx.com.qtx.cotizadorv1ds.persistencia.entidades.Componente compEntity = 
                new mx.com.qtx.cotizadorv1ds.persistencia.entidades.Componente();
        
        // Copiar propiedades básicas
        compEntity.setId(compCore.getId());
        compEntity.setDescripcion(compCore.getDescripcion());
        compEntity.setMarca(compCore.getMarca());
        compEntity.setModelo(compCore.getModelo());
        compEntity.setCosto(compCore.getCosto());
        compEntity.setPrecioBase(compCore.getPrecioBase());
        
        // El tipo de componente deberá ser asignado si es necesario en la lógica de negocio específica
        
        return compEntity;
    }

    /**
     * Convierte una entidad Componente de persistencia a un objeto Componente del dominio (core)
     * según su tipo específico (Monitor, DiscoDuro, TarjetaVideo o Pc).
     * <p>
     * Este método determina el tipo específico de la entidad mediante comprobaciones de instancia
     * y crea el objeto de dominio correspondiente utilizando los métodos factory apropiados.
     * Maneja tipos especiales como DiscoDuro y TarjetaVideo que requieren parámetros adicionales,
     * y utiliza una implementación por defecto (Monitor) para otros tipos de componentes.
     * </p>
     * 
     * @param compEntity Entidad de persistencia Componente a convertir
     * @return Objeto Componente del dominio correspondiente al tipo de la entidad, o null si el parámetro de entrada es null
     */
    public static Componente convertToComponente(
            mx.com.qtx.cotizadorv1ds.persistencia.entidades.Componente compEntity) {
        
        if (compEntity == null) {
            return null;
        }
        
        // Extraer propiedades comunes
        String id = compEntity.getId();
        String descripcion = compEntity.getDescripcion();
        String marca = compEntity.getMarca();
        String modelo = compEntity.getModelo();
        BigDecimal costo = compEntity.getCosto();
        BigDecimal precioBase = compEntity.getPrecioBase();
        
        // Determinar el tipo de componente y crear la instancia adecuada
        if (compEntity instanceof mx.com.qtx.cotizadorv1ds.persistencia.entidades.DiscoDuro) {
            // Es un disco duro
            mx.com.qtx.cotizadorv1ds.persistencia.entidades.DiscoDuro discoEntity = 
                    (mx.com.qtx.cotizadorv1ds.persistencia.entidades.DiscoDuro) compEntity;
            String capacidad = discoEntity.getCapacidadAlm();
            // Usar el método factory para crear el objeto
            return Componente.crearDiscoDuro(id, descripcion, marca, modelo, costo, precioBase, capacidad);
            
        } else if (compEntity instanceof mx.com.qtx.cotizadorv1ds.persistencia.entidades.TarjetaVideo) {
            // Es una tarjeta de video
            mx.com.qtx.cotizadorv1ds.persistencia.entidades.TarjetaVideo tarjetaEntity = 
                    (mx.com.qtx.cotizadorv1ds.persistencia.entidades.TarjetaVideo) compEntity;
            String memoria = tarjetaEntity.getMemoria();
            // Usar el método factory para crear el objeto
            return Componente.crearTarjetaVideo(id, descripcion, marca, modelo, costo, precioBase, memoria);
            
        } else {
            // Para otros tipos (Monitor o componentes genéricos)
            // Usar el método factory para crear un Monitor por defecto como tipo más simple
            return Componente.crearMonitor(id, descripcion, marca, modelo, costo, precioBase);
        }
        
        // Nota: La conversión para PC's compuestas requeriría implementación adicional
        // para recuperar todos los componentes relacionados y crear una PC con ellos
    }
}
