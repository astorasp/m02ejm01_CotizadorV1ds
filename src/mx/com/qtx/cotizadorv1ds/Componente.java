package mx.com.qtx.cotizadorv1ds;
import java.math.BigDecimal;

public abstract class Componente {
    protected String id;
    protected String descripcion;
    protected String marca;
    protected String modelo;
    protected BigDecimal costo;
    protected BigDecimal precioBase;
    
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


	// Métodos
    public void mostrarCaracteristicas() { //¿Como despliega una pc?
        System.out.println("ID: " + id);
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

	public BigDecimal cotizar(int cantidadI) {
		return this.precioBase.multiply(new BigDecimal(cantidadI));
	}

	public abstract String getCategoria();
}