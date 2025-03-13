package mx.com.qtx.cotizadorv1ds;
import java.math.BigDecimal;

public class CotizadorTest {
    public static void main(String[] args) {
//    	testCreacionComponente();
//    	testCreacionComponente_confEquivocada_DiscoConMemoria();
//    	testCreacionPC();
    	
//    	testAgregarComponentes();
//    	testEliminarComponente();
    	
    	testEmitirCotizacion();
    }

	private static void testEmitirCotizacion() {
		
		Componente monitor = new Monitor("M001","Monitor 17 pulgadas","Samsung","Goliat-500",
						new BigDecimal(1000), new BigDecimal(2000));
		Monitor monitor2 = new Monitor("M022","Monitor 15 pulgadas","Sony","VR-30",
				new BigDecimal(1100), new BigDecimal(2200));
		Cotizador cotizador = new Cotizador();
		cotizador.agregarComponente(1, monitor);
		cotizador.agregarComponente(4, monitor2);
		Componente disco = new DiscoDuro("D-23", "Disco estado sólido", "Seagate", "T-455", new BigDecimal(500), 
				new BigDecimal(1000), "2TB");
		cotizador.agregarComponente(10, disco);
		
		cotizador.emitirCotizacion();
	
	}
    

}