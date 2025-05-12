package mx.com.qtx.cotizadorv1ds.servicios;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mx.com.qtx.cotizadorv1ds.core.componentes.Componente;
import mx.com.qtx.cotizadorv1ds.core.componentes.DiscoDuro;
import mx.com.qtx.cotizadorv1ds.core.componentes.Pc;
import mx.com.qtx.cotizadorv1ds.core.componentes.TarjetaVideo;
import mx.com.qtx.cotizadorv1ds.core.componentes.TipoComponenteEnum;
import mx.com.qtx.cotizadorv1ds.persistencia.entidades.PcPartes;
import mx.com.qtx.cotizadorv1ds.persistencia.entidades.TipoComponente;
import mx.com.qtx.cotizadorv1ds.persistencia.repositorios.ComponenteRepositorio;
import mx.com.qtx.cotizadorv1ds.persistencia.repositorios.DiscoDuroRepositorio;
import mx.com.qtx.cotizadorv1ds.persistencia.repositorios.PcPartesRepositorio;
import mx.com.qtx.cotizadorv1ds.persistencia.repositorios.TarjetaVideoRepositorio;
import mx.com.qtx.cotizadorv1ds.persistencia.repositorios.TipoComponenteRepositorio;
import mx.com.qtx.cotizadorv1ds.servicios.wrapper.ComponenteEntityConverter;

@Service
public class ComponenteServicio {
    
    private ComponenteRepositorio compRepo;
    private TipoComponenteRepositorio tipoRepo;
    private PcPartesRepositorio pcPartesRepo;  
    private DiscoDuroRepositorio discoDuroRepo;
    private TarjetaVideoRepositorio tarjetaVideoRepo; 
    private List<TipoComponente> tipos;
    
    public ComponenteServicio(ComponenteRepositorio compRepo, 
        TipoComponenteRepositorio tipoRepo, PcPartesRepositorio pcPartesRepo,
        DiscoDuroRepositorio discoDuroRepo, TarjetaVideoRepositorio tarjetaVideoRepo) {
        this.compRepo = compRepo;
        this.tipoRepo = tipoRepo;
        this.pcPartesRepo = pcPartesRepo;
        this.discoDuroRepo = discoDuroRepo;
        this.tarjetaVideoRepo = tarjetaVideoRepo;
        tipos = tipoRepo.findAll();
    }

    @Transactional
    public void borrarComponente(String id) {
        discoDuroRepo.deleteById(id);
        tarjetaVideoRepo.deleteById(id);
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
                compRepo.save(compEntity);
                break;
            case "Tarjeta de Video":
                TarjetaVideo tarjetaVideo = (TarjetaVideo) comp;
                if (tarjetaVideoRepo.existsById(tarjetaVideo.getId())) {
                    mx.com.qtx.cotizadorv1ds.persistencia.entidades.TarjetaVideo tarjetaVideoExistente 
                        = tarjetaVideoRepo.findById(tarjetaVideo.getId()).get();
                    tarjetaVideoExistente.setDescripcion(compEntity.getDescripcion());
                    tarjetaVideoExistente.setMarca(compEntity.getMarca());
                    tarjetaVideoExistente.setModelo(compEntity.getModelo());
                    tarjetaVideoExistente.setCosto(compEntity.getCosto());
                    tarjetaVideoExistente.setPrecioBase(compEntity.getPrecioBase());
                    // Propiedades específicas
                    tarjetaVideoExistente.setMemoria(tarjetaVideo.getMemoria());
                    tarjetaVideoRepo.save(tarjetaVideoExistente);
                } else {
                    mx.com.qtx.cotizadorv1ds.persistencia.entidades.TarjetaVideo tarjetaVideoEntity = 
                    new mx.com.qtx.cotizadorv1ds.persistencia.entidades.TarjetaVideo();
                    tarjetaVideoEntity.setId(compEntity.getId());
                    tarjetaVideoEntity.setDescripcion(compEntity.getDescripcion());
                    tarjetaVideoEntity.setMarca(compEntity.getMarca());
                    tarjetaVideoEntity.setModelo(compEntity.getModelo());
                    tarjetaVideoEntity.setCosto(compEntity.getCosto());
                    tarjetaVideoEntity.setPrecioBase(compEntity.getPrecioBase());
                    // Propiedades específicas
                    tarjetaVideoEntity.setMemoria(tarjetaVideo.getMemoria());
                    // Tipo componente
                    tarjetaVideoEntity.setTipoComponente(tipos.stream()
                        .filter(t -> t.getNombre().equals(TipoComponenteEnum.TARJETA_VIDEO.name()))
                        .findFirst()
                        .get());
                    // Guardar solo esta entidad
                    compRepo.save(tarjetaVideoEntity);
                }
                break;
            case "Disco Duro":
                DiscoDuro discoDuro = (DiscoDuro) comp;
                if (discoDuroRepo.existsById(discoDuro.getId())) {
                    mx.com.qtx.cotizadorv1ds.persistencia.entidades.DiscoDuro discoDuroExistente 
                        = discoDuroRepo.findById(discoDuro.getId()).get();
                    discoDuroExistente.setDescripcion(compEntity.getDescripcion());
                    discoDuroExistente.setMarca(compEntity.getMarca());
                    discoDuroExistente.setModelo(compEntity.getModelo());
                    discoDuroExistente.setCosto(compEntity.getCosto());
                    discoDuroExistente.setPrecioBase(compEntity.getPrecioBase());
                    // Propiedades específicas
                    discoDuroExistente.setCapacidadAlm(discoDuro.getCapacidadAlm());
                    discoDuroRepo.save(discoDuroExistente);
                } else {
                    mx.com.qtx.cotizadorv1ds.persistencia.entidades.DiscoDuro discoDuroEntity = 
                    new mx.com.qtx.cotizadorv1ds.persistencia.entidades.DiscoDuro();
                    discoDuroEntity.setId(compEntity.getId());
                    discoDuroEntity.setDescripcion(compEntity.getDescripcion());
                    discoDuroEntity.setMarca(compEntity.getMarca());
                    discoDuroEntity.setModelo(compEntity.getModelo());
                    discoDuroEntity.setCosto(compEntity.getCosto());
                    discoDuroEntity.setPrecioBase(compEntity.getPrecioBase());
                    // Propiedades específicas
                    discoDuroEntity.setCapacidadAlm(discoDuro.getCapacidadAlm());
                    // Tipo componente
                    discoDuroEntity.setTipoComponente(tipos.stream()
                        .filter(t -> t.getNombre().equals(TipoComponenteEnum.DISCO_DURO.name()))
                        .findFirst()
                        .get());
                    // Guardar solo esta entidad
                    compRepo.save(discoDuroEntity);
                }
                break;
            case "Monitor":
                compEntity.setTipoComponente(tipos.stream()
                    .filter(t -> t.getNombre().equals(TipoComponenteEnum.MONITOR.name()))
                    .findFirst()
                    .get());
                compRepo.save(compEntity);
                break;  
        }        
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
                    compRepo.save(compEntity);
                    break;
                case "Tarjeta de Video":
                    TarjetaVideo tarjetaVideo = (TarjetaVideo) comp;
                    if (tarjetaVideoRepo.existsById(tarjetaVideo.getId())) {
                        mx.com.qtx.cotizadorv1ds.persistencia.entidades.TarjetaVideo tarjetaVideoExistente 
                            = tarjetaVideoRepo.findById(tarjetaVideo.getId()).get();
                        tarjetaVideoExistente.setDescripcion(compEntity.getDescripcion());
                        tarjetaVideoExistente.setMarca(compEntity.getMarca());
                        tarjetaVideoExistente.setModelo(compEntity.getModelo());
                        tarjetaVideoExistente.setCosto(compEntity.getCosto());
                        tarjetaVideoExistente.setPrecioBase(compEntity.getPrecioBase());
                        // Propiedades específicas
                        tarjetaVideoExistente.setMemoria(tarjetaVideo.getMemoria());
                        tarjetaVideoRepo.save(tarjetaVideoExistente);
                    } else {
                        mx.com.qtx.cotizadorv1ds.persistencia.entidades.TarjetaVideo tarjetaVideoEntity = 
                        new mx.com.qtx.cotizadorv1ds.persistencia.entidades.TarjetaVideo();
                        tarjetaVideoEntity.setId(compEntity.getId());
                        tarjetaVideoEntity.setDescripcion(compEntity.getDescripcion());
                        tarjetaVideoEntity.setMarca(compEntity.getMarca());
                        tarjetaVideoEntity.setModelo(compEntity.getModelo());
                        tarjetaVideoEntity.setCosto(compEntity.getCosto());
                        tarjetaVideoEntity.setPrecioBase(compEntity.getPrecioBase());
                        // Propiedades específicas
                        tarjetaVideoEntity.setMemoria(tarjetaVideo.getMemoria());
                        // Tipo componente
                        tarjetaVideoEntity.setTipoComponente(tipos.stream()
                            .filter(t -> t.getNombre().equals(TipoComponenteEnum.TARJETA_VIDEO.name()))
                            .findFirst()
                            .get());
                        // Guardar solo esta entidad
                        compRepo.save(tarjetaVideoEntity);
                    }
                    break;
                case "Disco Duro":
                    DiscoDuro discoDuro = (DiscoDuro) comp;
                    if (discoDuroRepo.existsById(discoDuro.getId())) {
                        mx.com.qtx.cotizadorv1ds.persistencia.entidades.DiscoDuro discoDuroExistente 
                            = discoDuroRepo.findById(discoDuro.getId()).get();
                        discoDuroExistente.setDescripcion(compEntity.getDescripcion());
                        discoDuroExistente.setMarca(compEntity.getMarca());
                        discoDuroExistente.setModelo(compEntity.getModelo());
                        discoDuroExistente.setCosto(compEntity.getCosto());
                        discoDuroExistente.setPrecioBase(compEntity.getPrecioBase());
                        // Propiedades específicas
                        discoDuroExistente.setCapacidadAlm(discoDuro.getCapacidadAlm());
                        discoDuroRepo.save(discoDuroExistente);
                    } else {
                        mx.com.qtx.cotizadorv1ds.persistencia.entidades.DiscoDuro discoDuroEntity = 
                        new mx.com.qtx.cotizadorv1ds.persistencia.entidades.DiscoDuro();
                        discoDuroEntity.setId(compEntity.getId());
                        discoDuroEntity.setDescripcion(compEntity.getDescripcion());
                        discoDuroEntity.setMarca(compEntity.getMarca());
                        discoDuroEntity.setModelo(compEntity.getModelo());
                        discoDuroEntity.setCosto(compEntity.getCosto());
                        discoDuroEntity.setPrecioBase(compEntity.getPrecioBase());
                        // Propiedades específicas
                        discoDuroEntity.setCapacidadAlm(discoDuro.getCapacidadAlm());
                        // Tipo componente
                        discoDuroEntity.setTipoComponente(tipos.stream()
                            .filter(t -> t.getNombre().equals(TipoComponenteEnum.DISCO_DURO.name()))
                            .findFirst()
                            .get());
                        // Guardar solo esta entidad
                        compRepo.save(discoDuroEntity);
                    }
                    break;
                case "Monitor":
                    compEntity.setTipoComponente(tipos.stream()
                        .filter(t -> t.getNombre().equals(TipoComponenteEnum.MONITOR.name()))
                        .findFirst()
                        .get());
                    compRepo.save(compEntity);
                    break;  
            }
            PcPartes pcParte = new PcPartes(pcEntity.getId(), compEntity.getId());
            pcPartesRepo.save(pcParte);
        }
    }


    public Componente buscarComponente(String id) {
        return ComponenteEntityConverter.convertToComponente(compRepo.findById(id).orElse(null));
    }   
}
