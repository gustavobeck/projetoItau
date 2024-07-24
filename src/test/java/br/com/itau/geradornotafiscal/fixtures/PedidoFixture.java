package br.com.itau.geradornotafiscal.fixtures;

import br.com.itau.geradornotafiscal.model.Destinatario;
import br.com.itau.geradornotafiscal.model.Finalidade;
import br.com.itau.geradornotafiscal.model.Pedido;
import br.com.itau.geradornotafiscal.model.Regiao;
import br.com.itau.geradornotafiscal.model.RegimeTributacaoPJ;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static br.com.itau.geradornotafiscal.fixtures.DestinatarioFixture.criarDestinatario;
import static br.com.itau.geradornotafiscal.fixtures.ItemFixture.criarItem;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PedidoFixture {

    public static Pedido criarPedido() {
        return criarPedido(DestinatarioFixture.criarDestinatario());
    }

    public static Pedido criarPedido(final RegimeTributacaoPJ regimeTributacaoPJ) {
        return criarPedido(DestinatarioFixture.criarDestinatario(regimeTributacaoPJ));
    }

    public static Pedido criarPedido(final Finalidade finalidade) {
        return criarPedido(DestinatarioFixture.criarDestinatario(finalidade));
    }

    public static Pedido criarPedido(final Regiao regiao) {
        return criarPedido(criarDestinatario(regiao));
    }

    private static Pedido criarPedido(final Destinatario destinatario) {
        return Pedido.builder()
                .idPedido(1)
                .data(LocalDate.now())
                .valorTotalItens(1000)
                .valorFrete(10)
                .itens(List.of(criarItem()))
                .destinatario(destinatario)
                .build();
    }
}
