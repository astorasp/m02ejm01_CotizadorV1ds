package mx.com.qtx.cotizadorv1ds.servicios;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.qtx.cotizadorv1ds.core.componentes.Componente;
import mx.com.qtx.cotizadorv1ds.core.componentes.Pc;
import mx.com.qtx.cotizadorv1ds.core.componentes.TipoComponenteEnum;
import mx.com.qtx.cotizadorv1ds.persistencia.entidades.PcParte;
import mx.com.qtx.cotizadorv1ds.persistencia.entidades.Promocion;
import mx.com.qtx.cotizadorv1ds.persistencia.entidades.TipoComponente;
import mx.com.qtx.cotizadorv1ds.persistencia.repositorios.ComponenteRepositorio;
import mx.com.qtx.cotizadorv1ds.persistencia.repositorios.PcPartesRepositorio;
import mx.com.qtx.cotizadorv1ds.persistencia.repositorios.PromocionRepositorio;
import mx.com.qtx.cotizadorv1ds.persistencia.repositorios.TipoComponenteRepositorio;
import mx.com.qtx.cotizadorv1ds.servicios.wrapper.ComponenteEntityConverter;

@Service
public class ComponenteServicio {
    
    private ComponenteRepositorio compRepo;
    private PcPartesRepositorio pcPartesRepo;  
    private PromocionRepositorio promoRepo;
    private List<TipoComponente> tipos;
    public ComponenteServicio(ComponenteRepositorio compRepo, 
        PcPartesRepositorio pcPartesRepo,
        PromocionRepositorio promoRepo,
        TipoComponenteRepositorio tipoRepo) {
        this.compRepo = compRepo;
        this.pcPartesRepo = pcPartesRepo;
        this.promoRepo = promoRepo;
        this.tipos = tipoRepo.findAll();
    }

    @Transactional
    public void borrarComponente(String id) {
        compRepo.deleteById(id);
    }

    @Transactional
    public mx.com.qtx.cotizadorv1ds.persistencia.entidades.Componente guardarComponente(Componente comp) {
        // Convertir y guardar/actualizar componente si es necesario
        // Usamos el método con nombre descriptivo para evitar ambigüedades
        var compEntity = ComponenteEntityConverter.convertToEntity(comp);
        Promocion promo = null;
        switch(comp.getCategoria()) {
            case "Disco Duro":
                TipoComponente tipo = tipos.stream()
                    .filter(t -> t.getNombre().equals("DISCO_DURO"))
                    .findFirst()
                    .orElse(null);
                compEntity.setTipoComponente(tipo);
                promo = promoRepo.findByNombre("Regular");
                break;
            case "Tarjeta de Video":
                tipo = tipos.stream()
                    .filter(t -> t.getNombre().equals("TARJETA_VIDEO"))
                    .findFirst()
                    .orElse(null);
                compEntity.setTipoComponente(tipo);
                promo = promoRepo.findByNombre("Tarjetas 3x2");
                break;
            case "Monitor":
                tipo = tipos.stream()
                    .filter(t -> t.getNombre().equals("MONITOR"))
                    .findFirst()
                    .orElse(null);
                compEntity.setTipoComponente(tipo);
                promo = promoRepo.findByNombre("Monitores por Volumen");
                break;
            default:
                throw new IllegalArgumentException("Tipo de componente no válido: " + comp.getCategoria());
        }
        compEntity.setPromocion(promo);
        return compRepo.save(compEntity);
    }

    @Transactional
    public void guardarPcCompleto(Componente pcComponente) {
        // 1. Convertir y guardar PC
        // Usamos el método con nombre descriptivo para evitar ambigüedades
        if(pcComponente instanceof Pc) {
            Pc pc = (Pc) pcComponente;
            var pcEntity = ComponenteEntityConverter.convertToEntity(pc);
            Promocion promo = promoRepo.findByNombre("PC Componentes");
            TipoComponente tipo = tipos.stream()
                .filter(t -> t.getNombre().equals("PC"))
                .findFirst()
                .orElse(null);            
            pcEntity.setPromocion(promo);
            pcEntity.setTipoComponente(tipo);
            pcEntity = compRepo.save(pcEntity);        
            // 2. Procesar componentes y crear asociaciones
            for (Componente comp : pc.getSubComponentes()) {
                // Convertir y guardar/actualizar componente si es necesario
                // Usamos el método con nombre descriptivo para evitar ambigüedades
                var compEntity = guardarComponente(comp);
                compRepo.save(compEntity);        

                PcParte pcParte = new PcParte(pcEntity.getId(), compEntity.getId());
                pcPartesRepo.save(pcParte);
            }
        }
    }


    public Componente buscarComponente(String id) {
        var compEntity = compRepo.findByIdWithTipoComponente(id);
        if(compEntity == null) {
            return null;
        }
        if(compEntity.getTipoComponente().getNombre().equals(TipoComponenteEnum.PC.name())) {
            var subCompEntities = compRepo.findComponentesByPcWithTipoComponente(compEntity.getId());
            return ComponenteEntityConverter.convertToComponente(compEntity, subCompEntities);
        }
        return ComponenteEntityConverter.convertToComponente(compEntity,null);
    }   
}
