package mx.com.qtx.cotizadorv1ds.persistencia.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mx.com.qtx.cotizadorv1ds.persistencia.entidades.Componente;
import java.util.List;
import java.math.BigDecimal;

@Repository
public interface ComponenteRepositorio extends JpaRepository<Componente, String> {
    // Métodos personalizados pueden ser añadidos aquí
    
    // Encontrar componentes por tipo
    List<Componente> findByTipoComponenteId(Short idTipoComponente);
    
    // Encontrar componentes por marca
    List<Componente> findByMarcaContainingIgnoreCase(String marca);
    
    // Encontrar componentes por rango de precio
    List<Componente> findByPrecioBaseBetween(BigDecimal min, BigDecimal max);

    void deleteById(String id);
}
