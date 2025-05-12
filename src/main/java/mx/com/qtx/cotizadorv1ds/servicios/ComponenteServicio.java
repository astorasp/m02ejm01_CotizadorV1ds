package mx.com.qtx.cotizadorv1ds.servicios;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.qtx.cotizadorv1ds.core.componentes.Componente;
import mx.com.qtx.cotizadorv1ds.core.componentes.Pc;
import mx.com.qtx.cotizadorv1ds.core.componentes.TipoComponenteEnum;
import mx.com.qtx.cotizadorv1ds.persistencia.entidades.PcPartes;
import mx.com.qtx.cotizadorv1ds.persistencia.entidades.TipoComponente;
import mx.com.qtx.cotizadorv1ds.persistencia.repositorios.ComponenteRepositorio;
import mx.com.qtx.cotizadorv1ds.persistencia.repositorios.PcPartesRepositorio;
import mx.com.qtx.cotizadorv1ds.persistencia.repositorios.TipoComponenteRepositorio;
import mx.com.qtx.cotizadorv1ds.servicios.wrapper.ComponenteEntityConverter;

@Service
public class ComponenteServicio {
    
    private ComponenteRepositorio compRepo;
    private TipoComponenteRepositorio tipoRepo;
    private PcPartesRepositorio pcPartesRepo;   
    private List<TipoComponente> tipos;
    
    public ComponenteServicio(ComponenteRepositorio compRepo, TipoComponenteRepositorio tipoRepo, PcPartesRepositorio pcPartesRepo) {
        this.compRepo = compRepo;
        this.tipoRepo = tipoRepo;
        this.pcPartesRepo = pcPartesRepo;
        tipos = tipoRepo.findAll();
    }

    public void borrarComponente(String id) {
        compRepo.deleteById(id);
    }

    @Transactional
    public void guardarComponente(Componente comp) {
        // Convertir y guardar/actualizar componente si es necesario
        // Usamos el método con nombre descriptivo para evitar ambigüedades
        var compEntity = ComponenteEntityConverter.convertToEntity(comp);            
        switch(comp.getCategoria()) {
            case "Pc":
                compEntity.setTipoComponente(tipos.stream()
                    .filter(t -> t.getNombre().equals(TipoComponenteEnum.MONITOR.name()))
                    .findFirst()
                    .get());
                break;
            case "Tarjeta de Video":
                compEntity.setTipoComponente(tipos.stream()
                    .filter(t -> t.getNombre().equals(TipoComponenteEnum.TARJETA_VIDEO.name()))
                    .findFirst()
                    .get());
                break;
            case "Disco Duro":
                compEntity.setTipoComponente(tipos.stream()
                    .filter(t -> t.getNombre().equals(TipoComponenteEnum.DISCO_DURO.name()))
                    .findFirst()
                    .get());
                break;
            case "Monitor":
                compEntity.setTipoComponente(tipos.stream()
                    .filter(t -> t.getNombre().equals(TipoComponenteEnum.MONITOR.name()))
                    .findFirst()
                    .get());
                break;  
        }
        compRepo.save(compEntity);        
    }

    @Transactional
    public void guardarPcCompleto(Componente pcComponente) {
        // 1. Convertir y guardar PC
        // Usamos el método con nombre descriptivo para evitar ambigüedades
        Pc pc = (Pc) pcComponente;
        var pcEntity = ComponenteEntityConverter.convertToEntity(pc);
        pcEntity.setTipoComponente(tipos.stream()
            .filter(t -> t.getNombre().equals(TipoComponenteEnum.PC.name()))
            .findFirst()
            .get());
        pcEntity = compRepo.save(pcEntity);
        
        // 2. Procesar componentes y crear asociaciones
        for (Componente comp : pc.getSubComponentes()) {
            // Convertir y guardar/actualizar componente si es necesario
            // Usamos el método con nombre descriptivo para evitar ambigüedades
            var compEntity = ComponenteEntityConverter.convertToEntity(comp);            
            switch(comp.getCategoria()) {
                case "Pc":
                    compEntity.setTipoComponente(tipos.stream()
                        .filter(t -> t.getNombre().equals(TipoComponenteEnum.MONITOR.name()))
                        .findFirst()
                        .get());
                    break;
                case "Tarjeta de Video":
                    compEntity.setTipoComponente(tipos.stream()
                        .filter(t -> t.getNombre().equals(TipoComponenteEnum.TARJETA_VIDEO.name()))
                        .findFirst()
                        .get());
                    break;
                case "Disco Duro":
                    compEntity.setTipoComponente(tipos.stream()
                        .filter(t -> t.getNombre().equals(TipoComponenteEnum.DISCO_DURO.name()))
                        .findFirst()
                        .get());
                    break;
                case "Monitor":
                    compEntity.setTipoComponente(tipos.stream()
                        .filter(t -> t.getNombre().equals(TipoComponenteEnum.MONITOR.name()))
                        .findFirst()
                        .get());
                    break;  
            }
            compEntity = compRepo.save(compEntity);
        
            PcPartes pcParte = new PcPartes(pcEntity.getId(), compEntity.getId());
            pcPartesRepo.save(pcParte);
        }
    }
}
