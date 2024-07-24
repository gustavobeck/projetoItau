package br.com.itau.geradornotafiscal.model;

import lombok.Getter;

@Getter
public enum RegimeTributacaoPJ {
    SIMPLES_NACIONAL(new double[]{0.03, 0.07, 0.13, 0.19}),
    LUCRO_REAL(new double[]{0.03, 0.09, 0.15, 0.20}),
    LUCRO_PRESUMIDO(new double[]{0.03, 0.09, 0.16, 0.20}),
    OUTROS(new double[]{0, 0, 0, 0});

    public final double[] aliquota;

    RegimeTributacaoPJ(double[] doubles) {
        aliquota = doubles;
    }
}

