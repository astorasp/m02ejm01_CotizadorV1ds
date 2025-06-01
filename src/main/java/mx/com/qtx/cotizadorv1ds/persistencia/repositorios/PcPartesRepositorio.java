package mx.com.qtx.cotizadorv1ds.persistencia.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mx.com.qtx.cotizadorv1ds.persistencia.entidades.PcParte;
import mx.com.qtx.cotizadorv1ds.persistencia.entidades.PcParte.PcPartesId;

import java.util.List;

@Repository
public interface PcPartesRepositorio extends JpaRepository<PcParte, PcPartesId> {
    
    
    // Contar el número de componentes en un PC
    @Query("SELECT COUNT(p) FROM PcParte p WHERE p.idPc = :idPc")
    long countComponentesByPc(@Param("idPc") String idPc);
    
    // Eliminar todas las partes de un PC específico
    void deleteByIdPc(String idPc);
}
