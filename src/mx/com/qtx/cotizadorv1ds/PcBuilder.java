package mx.com.qtx.cotizadorv1ds;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import mx.com.qtx.cotizadorv1ds.componentes.Componente;
import mx.com.qtx.cotizadorv1ds.componentes.DiscoDuro;
import mx.com.qtx.cotizadorv1ds.componentes.Monitor;
import mx.com.qtx.cotizadorv1ds.componentes.Pc;
import mx.com.qtx.cotizadorv1ds.componentes.TarjetaVideo;

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
    private static final int MAX_MONITORES = 1;
    private static final int MAX_DISCOS = 2;
    private static final int MAX_TARJETAS_VIDEO = 1;

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
        if(this.monitores.size() < MAX_MONITORES && monitor instanceof Monitor) {
            this.monitores.add(monitor);
        }
        else {
            throw new IllegalArgumentException("No se puede agregar más monitores");
        }
        return this;
    }
    public PcBuilder agregarDiscoDuro(Componente disco) {
        if(this.discos.size() < MAX_DISCOS && disco instanceof DiscoDuro) {
            this.discos.add(disco);
        }
        else{
            throw new IllegalArgumentException("No se puede agregar más discos");
        }
        return this;
    }
    public PcBuilder agregarTarjetaVideo(Componente tarjetaVideo) {
        if(this.tarjetaVideo.size() < MAX_TARJETAS_VIDEO && tarjetaVideo instanceof TarjetaVideo) {
            this.tarjetaVideo.add(tarjetaVideo);
        }
        else{
            throw new IllegalArgumentException("No se puede agregar más tarjetas de video");
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
