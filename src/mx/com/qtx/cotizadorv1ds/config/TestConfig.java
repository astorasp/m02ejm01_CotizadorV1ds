package mx.com.qtx.cotizadorv1ds.config;

import java.util.List;

import mx.com.qtx.cotizadorv1ds.core.ICotizador;

public class TestConfig {

	public static void main(String[] args) {
//		test_buscarImplementacionesDe();
		test_getCotizador();
	}

	private static void test_getCotizador() {
		ICotizador cotizador = Config.getCotizador();
		System.out.println("Clase instanciada:" + cotizador.getClass().getName());
		
	}

	private static void test_buscarImplementacionesDe() {
		List<Class<?>> lstImplmICotizador = Config.buscarImplementacionesDe(ICotizador.class, "mx.com.qtx.cotizadorv1ds");
		System.out.println("Implementaciones de ICotizador en el classpath:");
		lstImplmICotizador.forEach(implI->System.out.println("\t" + implI.getName()));
	}

}
