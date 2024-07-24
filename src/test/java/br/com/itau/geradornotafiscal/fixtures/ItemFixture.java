package br.com.itau.geradornotafiscal.fixtures;

import br.com.itau.geradornotafiscal.model.Item;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemFixture {

    public static Item criarItem() {
        return Item.builder()
                .idItem("1")
                .descricao("Monitor LCD SAMSUNG")
                .valorUnitario(700)
                .quantidade(8)
                .build();
    }

    public static Item criarItem2() {
        return Item.builder()
                .idItem("2")
                .descricao("Celular SAMSUNG S24")
                .valorUnitario(1000)
                .quantidade(5)
                .build();
    }
}
