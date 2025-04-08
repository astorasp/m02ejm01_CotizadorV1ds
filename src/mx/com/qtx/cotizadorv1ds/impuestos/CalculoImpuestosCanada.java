package mx.com.qtx.cotizadorv1ds.impuestos;

import java.math.BigDecimal;

public class CalculoImpuestosCanada implements ICalculoImpuestoPais {
    @Override
    public BigDecimal calcularImpuestoPais(BigDecimal monto) {
        return monto.multiply(BigDecimal.valueOf(0.15));
    }
}
