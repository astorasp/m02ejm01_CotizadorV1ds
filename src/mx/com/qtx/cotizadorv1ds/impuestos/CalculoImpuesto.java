package mx.com.qtx.cotizadorv1ds.impuestos;

import java.math.BigDecimal;

public abstract class CalculoImpuesto {
    protected ICalculoImpuestoPais calculoImpuestoPais;

    protected CalculoImpuesto(ICalculoImpuestoPais calculoImpuestoPais) {
        this.calculoImpuestoPais = calculoImpuestoPais;
    }

    public abstract BigDecimal calcularImpuesto(BigDecimal monto);
}
