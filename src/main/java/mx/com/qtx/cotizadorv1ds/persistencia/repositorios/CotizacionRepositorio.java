package mx.com.qtx.cotizadorv1ds.persistencia.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.qtx.cotizadorv1ds.persistencia.entidades.Cotizacion;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CotizacionRepositorio extends JpaRepository<Cotizacion, Integer> {
    // Encontrar cotizaciones por fecha
    List<Cotizacion> findByFechaContaining(String fecha);
    
    // Encontrar cotizaciones por rango de monto total
    List<Cotizacion> findByTotalBetween(BigDecimal min, BigDecimal max);
    
    // Encontrar cotizaciones con monto total mayor a un valor dado
    List<Cotizacion> findByTotalGreaterThan(BigDecimal minTotal);
    
    // Buscar cotizaciones que contengan un componente específico (usando JPQL)
    @Query("SELECT c FROM Cotizacion c JOIN c.detalles d WHERE d.componente.id = :idComponente")
    List<Cotizacion> findCotizacionesByComponente(@Param("idComponente") String idComponente);
}
