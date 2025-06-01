package mx.com.qtx.cotizadorv1ds.persistencia.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import mx.com.qtx.cotizadorv1ds.persistencia.entidades.Promocion;

public interface PromocionRepositorio extends JpaRepository<Promocion, String> {
 
    /**
     * Busca una promoción por su nombre.
     * 
     * @param nombre El nombre de la promoción a buscar.
     * @return La promoción encontrada o null si no existe.
     */
    Promocion findByNombre(String nombre);
}
