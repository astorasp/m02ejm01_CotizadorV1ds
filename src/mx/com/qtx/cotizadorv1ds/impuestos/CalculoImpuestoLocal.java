package mx.com.qtx.cotizadorv1ds.impuestos;

import java.math.BigDecimal;

public class CalculoImpuestoLocal extends CalculoImpuesto {
    private static final BigDecimal IMPUESTO_LOCAL = BigDecimal.valueOf(0.03);
    public CalculoImpuestoLocal(ICalculoImpuestoPais calculoImpuestoPais) {
        super(calculoImpuestoPais);
    }

    @Override
    public BigDecimal calcularImpuesto(BigDecimal monto) {
        return calculoImpuestoPais.calcularImpuestoPais(monto) 
            .add(monto.multiply(IMPUESTO_LOCAL));
    }
}