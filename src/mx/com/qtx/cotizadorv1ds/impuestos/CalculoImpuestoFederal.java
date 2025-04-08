package mx.com.qtx.cotizadorv1ds.impuestos;

import java.math.BigDecimal;

public class CalculoImpuestoFederal extends CalculoImpuesto {
    private static final BigDecimal IMPUESTO_FEDERAL = BigDecimal.valueOf(0.05);
    public CalculoImpuestoFederal(ICalculoImpuestoPais calculoImpuestoPais) {
        super(calculoImpuestoPais);
    }

    @Override
    public BigDecimal calcularImpuesto(BigDecimal monto) {
        return calculoImpuestoPais.calcularImpuestoPais(monto) 
            .add(monto.multiply(IMPUESTO_FEDERAL));
    }
}