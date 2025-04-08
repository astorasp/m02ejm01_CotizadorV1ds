package mx.com.qtx.cotizadorv1ds.impuestos;

import java.math.BigDecimal;

public interface ICalculoImpuestoPais {
    BigDecimal calcularImpuestoPais(BigDecimal monto);
}
