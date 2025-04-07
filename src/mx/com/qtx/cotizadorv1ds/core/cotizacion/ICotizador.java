package mx.com.qtx.cotizadorv1ds.core.cotizacion;

import java.util.List;

import mx.com.qtx.cotizadorv1ds.core.cotizacion.Cotizacion;
import mx.com.qtx.cotizadorv1ds.core.cotizacion.Componente;

public interface ICotizador {
    Cotizacion generarCotizacion();
    void agregarComponente(int cantidad, Componente comp); // Asumiendo que 'int' es cantidad y 'compK' es Componente
    void eliminarComponente(String idComponente);
    void listarComponentes(); // Asumo que esto imprime o devuelve algo, lo dejo void por ahora
} 