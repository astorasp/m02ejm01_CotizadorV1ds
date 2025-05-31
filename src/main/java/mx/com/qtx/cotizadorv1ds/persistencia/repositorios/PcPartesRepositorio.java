package mx.com.qtx.cotizadorv1ds.persistencia.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.qtx.cotizadorv1ds.persistencia.entidades.PcPartes;
import mx.com.qtx.cotizadorv1ds.persistencia.entidades.PcPartes.PcPartesId;

import java.util.List;

@Repository
public interface PcPartesRepositorio extends JpaRepository<PcPartes, PcPartesId> {
    
    // Encontrar componentes por tipo para un PC específico
    @Query("SELECT p FROM PcPartes p WHERE p.idPc = :idPc AND p.componente.tipoComponente.nombre = :tipoComponente")
    List<PcPartes> findByPcAndTipoComponente(
        @Param("idPc") String idPc, 
        @Param("tipoComponente") String tipoComponente
    );
    
    // Contar el número de componentes en un PC
    @Query("SELECT COUNT(p) FROM PcPartes p WHERE p.idPc = :idPc")
    long countComponentesByPc(@Param("idPc") String idPc);
    
    // Eliminar todas las partes de un PC específico
    void deleteByIdPc(String idPc);
}
