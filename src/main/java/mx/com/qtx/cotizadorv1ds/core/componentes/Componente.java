package mx.com.qtx.cotizadorv1ds.core.componentes;
import java.math.BigDecimal;
import java.util.List;

import mx.com.qtx.cotizadorv1ds.config.SpringContextProvider;
import mx.com.qtx.cotizadorv1ds.servicios.ComponenteServicio;

public abstract class Componente {
    protected String id;
    protected String descripcion;
    protected String marca;
    protected String modelo;
    protected BigDecimal costo;
    protected BigDecimal precioBase;
    
    protected IPromocion promo;

    private static ComponenteServicio componenteServicio = SpringContextProvider.getBean(ComponenteServicio.class);
    
    // Constructor
    public Componente(String id, String descripcion, String marca, String modelo, 
                     BigDecimal costo, BigDecimal precioBase) {
        this.id = id;
        this.descripcion = descripcion;
        this.marca = marca;
        this.modelo = modelo;
        this.costo = costo;
        this.precioBase = precioBase;
    }
 
    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }

    public BigDecimal getPrecioBase() { return precioBase; }
    public void setPrecioBase(BigDecimal precioBase) { this.precioBase = precioBase; }


	public IPromocion getPromo() {
		return promo;
	}

	public void setPromo(IPromocion promo) {
		this.promo = promo;
	}

	// Métodos
    public void mostrarCaracteristicas() { //¿Como despliega una pc?
        System.out.println("ID: " + id);
        System.out.println("Categoría: " + this.getCategoria());
        System.out.println("Descripción: " + descripcion);
        System.out.println("Marca: " + marca);
        System.out.println("Modelo: " + modelo);
        System.out.println("Costo: $" + costo);
        System.out.println("Precio Base: $" + precioBase);
        System.out.println("Utilidad: " + this.calcularUtilidad());
               
    }

    public BigDecimal calcularUtilidad() {
        return precioBase.subtract(costo);
    }

	final public BigDecimal cotizar(int cantidadI) {
		if(this.promo == null)
			return this.precioBase.multiply(new BigDecimal(cantidadI));
		else
			return this.promo.calcularImportePromocion(cantidadI, this.precioBase);
	}

    public void borrarComponente() {
        componenteServicio.borrarComponente(this.id);
    }

    public static Componente buscarComponente(String id) {
        return componenteServicio.buscarComponente(id);
    }

    public void guardarComponente() {
        if(this instanceof Pc)
            componenteServicio.guardarPcCompleto(this);
        else
            componenteServicio.guardarComponente(this);
    }

	public abstract String getCategoria();
	
	public static Componente crearDiscoDuro(String id, String descripcion, String marca, String modelo, BigDecimal costo,
			BigDecimal precioBase, String capacidadAlm) {
		Componente disco = new DiscoDuro(id,descripcion,marca,modelo,costo,precioBase,capacidadAlm);
		return disco;
	}
	
	public static Componente crearMonitor(String id, String descripcion, String marca, String modelo, BigDecimal costo,
			BigDecimal precioBase) {
		Componente monitor = new Monitor(id,descripcion, marca, modelo, costo,precioBase);	
		return monitor;
	}

	public static Componente crearTarjetaVideo(String id, String descripcion, String marca, String modelo, BigDecimal costo,
			BigDecimal precioBase, String memoria) {
		Componente tarjeta = new TarjetaVideo(id, descripcion, marca, modelo, costo,
				precioBase, memoria);
		return tarjeta;
	}

	public static Componente crearPc(String id, String descripcion, String marca, String modelo, 
			List<Componente> subComponentes) {
		List<ComponenteSimple> lstDispositivos = subComponentes.stream()
				                                          .filter(cmpI->(cmpI instanceof ComponenteSimple))
		                                                  .map(dispI -> (ComponenteSimple) dispI)
		                                                  .toList();
		Componente pc = new Pc(id, descripcion, marca, modelo, lstDispositivos);
		return pc;
	}
	
	public static PcBuilder getPcBuilder() {
		return new PcBuilder();
	}

	@Override
	public String toString() {
		return "Componente [id=" + id + ", descripcion=" + descripcion + ", marca=" + marca + ", modelo=" + modelo
				+ ", costo=" + costo + ", precioBase=" + precioBase + "]";
	}
	
	
}