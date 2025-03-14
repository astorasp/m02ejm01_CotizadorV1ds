package mx.com.qtx.cotizadorv1ds;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class Cotizacion {
	private static long nCotizaciones = 0;
	
	private long num;
	private LocalDate fecha;
	private BigDecimal total;
	
	private Map<Integer,DetalleCotizacion> detalles;
	
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
	
	public void emitirComoReporte() {
		System.out.println("================== Cotizacion número:" + this.num + " del " + this.fecha + " ==================\n");
		for(Integer k:this.detalles.keySet()) {
			this.desplegarLineaCotizacion(this.detalles.get(k));
		}
		System.out.printf("\n%72s","Total:$" + String.format("%8.2f",this.getTotal()));
		
	}
	
	private void desplegarLineaCotizacion(DetalleCotizacion detI) {
		System.out.println(String.format("%3d",detI.getCantidad()) + " " 
							+ String.format("%-20s", detI.getDescripcion())
							+ " con precio base de $" + String.format("%8.2f",detI.getPrecioBase())
							+ " cuesta(n) " + String.format("%8.2f",detI.getImporteCotizado()));
	}
}
