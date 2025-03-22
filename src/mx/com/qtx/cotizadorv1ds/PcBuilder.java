package mx.com.qtx.cotizadorv1ds;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import mx.com.qtx.cotizadorv1ds.componentes.Componente;
import mx.com.qtx.cotizadorv1ds.componentes.Pc;

public class PcBuilder {
    private String id;
    private String descripcion;
    private String marca;
    private String modelo;
    private BigDecimal costo;
    private BigDecimal precioBase;    
    List<Componente> monitores;
    List<Componente> discos; 
    List<Componente> tarjetaVideo;
    private static final int maxMonitores = 1;
    private static final int maxDiscos = 2;
    private static final int maxTarjetasVideo = 1;

    public PcBuilder() {
        this.monitores = new ArrayList<>();
        this.discos = new ArrayList<>();
        this.tarjetaVideo = new ArrayList<>();
    }

    public PcBuilder setId(String id){
        this.id = id;
        return this;
    }
    
    public PcBuilder setDescripcion(String descripcion){
        this.descripcion = descripcion;
        return this;
    }

    public PcBuilder setMarca(String marca){
        this.marca = marca;
        return this;
    }
    
    public PcBuilder setModelo(String modelo){
        this.modelo = modelo;
        return this;
    }
    
    public PcBuilder setCosto(BigDecimal costo){
        this.costo = costo;
        return this;
    }
    
    public PcBuilder setPrecioBase(BigDecimal precioBase){
        this.precioBase = precioBase;
        return this;
    }

    public PcBuilder agregarMonitor(Componente monitor) {
        if(this.monitores.size() < PcBuilder.maxMonitores) {
            this.monitores.add(monitor);
        }
        return this;
    }
    public PcBuilder agregarDiscoDuro(Componente disco) {
        if(this.discos.size() < PcBuilder.maxDiscos) {
            this.discos.add(disco);
        }
        return this;
    }
    public PcBuilder agregarTarjetaVideo(Componente tarjetaVideo) {
        if(this.tarjetaVideo.size() < PcBuilder.maxTarjetasVideo) {
            this.tarjetaVideo.add(tarjetaVideo);
        }
        return this;
    }

    public Pc build() {        
        return new Pc(this.id, this.descripcion, this.marca, this.modelo, 
        List.copyOf(
            Stream.of(this.monitores, this.discos, this.tarjetaVideo)
                  .flatMap(List::stream)
                  .toList())
            );
    }
 
    
}
