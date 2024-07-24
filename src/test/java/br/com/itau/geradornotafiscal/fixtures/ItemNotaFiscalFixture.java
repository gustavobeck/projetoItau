package br.com.itau.geradornotafiscal.fixtures;

import br.com.itau.geradornotafiscal.model.ItemNotaFiscal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemNotaFiscalFixture {

    public static ItemNotaFiscal criarItemNotaFiscal() {
        return ItemNotaFiscal.builder()
                .idItem("1")
                .descricao("Televisao Samsung")
                .valorUnitario(100)
                .quantidade(10)
                .valorTributoItem(0)
                .build();
    }
}
