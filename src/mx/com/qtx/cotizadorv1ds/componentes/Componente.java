package mx.com.qtx.cotizadorv1ds.componentes;
import java.math.BigDecimal;
import java.util.List;

public abstract class Componente {
    protected String id;
    protected String descripcion;
    protected String marca;
    protected String modelo;
    protected BigDecimal costo;
    protected BigDecimal precioBase;
    

    protected Componente(){}
    // Constructor
    protected Componente(String id, String descripcion, String marca, String modelo,
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

    public abstract String getCategoria();

	// Métodos
    public void mostrarCaracteristicas() { //¿Como despliega una pc?
        System.out.println("ID: " + id);
        System.out.println("Descripción: " + descripcion);
        System.out.println("Marca: " + marca);
        System.out.println("Modelo: " + modelo);
        System.out.println("Costo: $" + costo);
        System.out.println("Precio Base: $" + precioBase);
        System.out.println("Categoria: " + getCategoria());
        System.out.println("Utilidad: " + this.calcularUtilidad());
        
    }

    public BigDecimal calcularUtilidad() {
        return precioBase.subtract(costo);
    }

	public BigDecimal cotizar(int cantidadI) {
		return this.precioBase.multiply(new BigDecimal(cantidadI));
	}

    public static Componente crearPC(String id, String descripcion, String marca, String modelo, 
        List<Componente> subComponentes)
    {
        return new Pc(id, descripcion, marca, modelo, subComponentes);
    }
    public static Componente crearMonitor(String id, String descripcion, String marca, 
        String modelo, BigDecimal costo,BigDecimal precioBase)
    {
        return new Monitor(id, descripcion, marca, modelo, costo, precioBase);
    }
    public static Componente crearTarjetaVideo(String id, String descripcion, String marca, 
        String modelo, BigDecimal costo,BigDecimal precioBase, String memoria)
    {
        return new TarjetaVideo(id, descripcion, marca, modelo, costo, precioBase, memoria  );
    }
    public static Componente crearDiscoDuro(String id, String descripcion, String marca, 
    String modelo, BigDecimal costo,BigDecimal precioBase, String capacidadAlm)
    {
        return new DiscoDuro(id, descripcion, marca, modelo, costo, precioBase, capacidadAlm);
    }
        
}