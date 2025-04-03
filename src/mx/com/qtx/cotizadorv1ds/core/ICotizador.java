package mx.com.qtx.cotizadorv1ds.core;

import mx.com.qtx.cotizadorv1ds.core.componentes.Componente;

public interface ICotizador {
    void agregarComponente(int cantidad, Componente componente);
    void eliminarComponente(String idComponente) throws ComponenteInvalidoException;
    Cotizacion generarCotizacion();
    void listarComponentes();
}
