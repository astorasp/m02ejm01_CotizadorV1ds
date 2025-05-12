package mx.com.qtx.cotizadorv1ds.servicios.wrapper;

import mx.com.qtx.cotizadorv1ds.core.componentes.ComponenteSimple;
import mx.com.qtx.cotizadorv1ds.core.componentes.DiscoDuro;
import mx.com.qtx.cotizadorv1ds.core.componentes.Monitor;
import mx.com.qtx.cotizadorv1ds.core.componentes.TarjetaVideo;
import mx.com.qtx.cotizadorv1ds.core.componentes.TipoComponenteEnum;

/**
 * Clase utilitaria para convertir objetos Componente del dominio a entidades Componente de persistencia
 */
public class ComponenteEntityConverter {
    
    /**
     * Convierte un objeto Componente del dominio (core) a una entidad Componente para persistencia
     * 
     * @param compCore Objeto Componente del dominio
     * @return Objeto Componente de persistencia
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

}
