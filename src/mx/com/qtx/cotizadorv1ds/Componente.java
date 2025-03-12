package mx.com.qtx.cotizadorv1ds;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Componente {
    private String id;
    private String descripcion;
    private String marca;
    private String modelo;
    private BigDecimal costo;
    private BigDecimal precioBase;
    private String tipo;
    private String memoria;
    private String capacidadAlm;
    
    private List<Componente> subcomponentes;

    // Constructor
    public Componente(String id, String descripcion, String marca, String modelo, 
                     BigDecimal costo, BigDecimal precioBase, String tipo, 
                     String memoria, String capacidadAlm) {
    	
    	if(tipo.equalsIgnoreCase("TarjetaVideo")) {
    		if(memoria == null) {
    			throw new IllegalArgumentException("Falta atributo memoria");
    		}
    	}
    	else {
    		if(memoria != null) {
    			throw new IllegalArgumentException("No debe incluir atributo memoria");
    		}
    	}
    	
        this.id = id;
        this.descripcion = descripcion;
        this.marca = marca;
        this.modelo = modelo;
        this.costo = costo;
        this.precioBase = precioBase;
        this.tipo = tipo;
        this.memoria = memoria;
        this.capacidadAlm = capacidadAlm;
    }
    
    public Componente(String id, String descripcion, String marca, String modelo, List<Componente> subcomponentes) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.marca = marca;
		this.modelo = modelo;
		this.subcomponentes = subcomponentes;
	}

	public static Componente crearPC(String id, String descripcion, String marca, String modelo, 
    		Componente disco1, Componente disco2, Componente monitor, Componente tarjeta) {
    	// Validaciones... 
    	
		//Crear agregado
		List<Componente> componentesPc = new ArrayList<>();
		componentesPc.add(disco1);
		componentesPc.add(disco2);
		componentesPc.add(monitor);
		componentesPc.add(tarjeta);
    	Componente pc = new Componente(id,descripcion,marca,modelo,componentesPc);
    	pc.setTipo("PC");
    	BigDecimal costo = disco1.costo.add((disco2 == null) ? new BigDecimal(0) : disco2.costo)
    			                        .add(monitor.costo)
    			                        .add(tarjeta.costo);
    	pc.setCosto(costo);
    	BigDecimal precioBase = disco1.precioBase.add((disco2 == null) ? new BigDecimal(0) :disco2.precioBase)
    			                                 .add(monitor.precioBase)
    			                                 .add(tarjeta.precioBase);
    	pc.setPrecioBase(precioBase);
    	    	
    	return pc;
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

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMemoria() { return memoria; }
    public void setMemoria(String memoria) { this.memoria = memoria; }

    public String getCapacidadAlm() {
		return capacidadAlm;
	}

	public void setCapacidadAlm(String capacidadAlm) {
		this.capacidadAlm = capacidadAlm;
	}

	// Métodos
    public void mostrarCaracteristicas() { //¿Como despliega una pc?
        System.out.println("ID: " + id);
        System.out.println("Descripción: " + descripcion);
        System.out.println("Marca: " + marca);
        System.out.println("Modelo: " + modelo);
        System.out.println("Costo: $" + costo);
        System.out.println("Precio Base: $" + precioBase);
        System.out.println("Tipo: " + tipo);
        System.out.println("Memoria: " + memoria);
        System.out.println("Capacidad almacenamiento: " + capacidadAlm);
        System.out.println("Utilidad: " + this.calcularUtilidad());
        
        if(this.tipo.equalsIgnoreCase("PC")) {
        	System.out.println("\nComponentes anidados: ---------------------");
        	this.subcomponentes.stream().filter(compI -> compI != null)
        	                            .forEach(compI->compI.mostrarCaracteristicas());
        }
    }

    public BigDecimal calcularUtilidad() {
        return precioBase.subtract(costo);
    }

	public List<Componente> getSubcomponentes() {
		return subcomponentes;
	}

	public void setSubcomponentes(List<Componente> subcomponentes) {
		this.subcomponentes = subcomponentes;
	}
}