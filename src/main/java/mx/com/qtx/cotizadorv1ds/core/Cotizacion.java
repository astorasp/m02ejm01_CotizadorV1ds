package mx.com.qtx.cotizadorv1ds.core;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import mx.com.qtx.cotizadorv1ds.config.SpringContextProvider;
import mx.com.qtx.cotizadorv1ds.servicios.CotizacionServicio;

public class Cotizacion {
	private static CotizacionServicio cotizacionServicio = SpringContextProvider.getBean(CotizacionServicio.class);
	private static long nCotizaciones = 0;
	
	protected long num;
	protected LocalDate fecha;
	protected BigDecimal total;
	protected BigDecimal totalImpuestos;
	
	protected Map<Integer,DetalleCotizacion> detalles;
	
	public Cotizacion() {
		super();
		nCotizaciones++;
		this.num = nCotizaciones;
		this.fecha = LocalDate.now();
		this.total = new BigDecimal(0);
		this.detalles = new TreeMap<>();
	}
	
	public void agregarDetalle(DetalleCotizacion detI) {
		this.detalles.put(detI.getNumDetalle(), detI);
	}

	public long getNum() {
		return num;
	}

	public void setNum(long num) {
		this.num = num;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getTotalImpuestos() {
		return totalImpuestos;
	}

	public void setTotalImpuestos(BigDecimal totalImpuestos) {
		this.totalImpuestos = totalImpuestos;
	}

	public List<DetalleCotizacion> getDetalles() {
		return new ArrayList<DetalleCotizacion>(detalles.values());
	}

	public void emitirComoReporte() {
		System.out.println("================== Cotizacion número:" + this.num + " del " + this.fecha + " ==================\n");
		for(Integer k:this.detalles.keySet()) {
			this.desplegarLineaCotizacion(this.detalles.get(k));
		}

		System.out.printf("\n%72s","Subtotal:$" + String.format("%8.2f",this.getTotal().subtract(this.getTotalImpuestos())));
		System.out.printf("\n%72s","Impuestos:$" + String.format("%8.2f",this.getTotalImpuestos()));
		System.out.printf("\n%72s","Total:$" + String.format("%8.2f",this.getTotal()));
		System.out.println(" ");
		
	}

	public void guardarCotizacion() {
		cotizacionServicio.guardarCotizacion(this);
	}	
	
	protected void desplegarLineaCotizacion(DetalleCotizacion detI) {
		System.out.println(String.format("%3d",detI.getCantidad()) + " " 
							+ String.format("Categoría:%-20s", detI.getCategoria()) 
							+ String.format("%-20s", detI.getDescripcion())
							+ " con precio base de $" + String.format("%8.2f",detI.getPrecioBase())
							+ " cuesta(n) " + String.format("%8.2f",detI.getImporteCotizado()));
	}
}
