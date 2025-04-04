package mx.com.qtx.cotizadorv1ds.excepcion;

import mx.com.qtx.cotizadorv1ds.core.componentes.Componente;

public class ComponenteInvalidoException extends Exception {
    private static final long serialVersionUID = 1L;
    private Componente componente;

    public ComponenteInvalidoException(String message,Componente componente){
        super(message);
        this.componente=componente;
    }
}
