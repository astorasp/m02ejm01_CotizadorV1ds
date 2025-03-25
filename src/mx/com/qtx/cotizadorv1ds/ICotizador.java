package mx.com.qtx.cotizadorv1ds;

import mx.com.qtx.cotizadorv1ds.componentes.Componente;

public interface ICotizador {
    void agregarComponente(int cantidad, Componente componente);
    void eliminarComponente(String idComponente);
    Cotizacion generarCotizacion();
    void listarComponentes();
}
