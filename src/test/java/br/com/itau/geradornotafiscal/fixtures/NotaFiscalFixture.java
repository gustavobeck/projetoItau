package br.com.itau.geradornotafiscal.fixtures;

import br.com.itau.geradornotafiscal.model.Destinatario;
import br.com.itau.geradornotafiscal.model.Finalidade;
import br.com.itau.geradornotafiscal.model.NotaFiscal;
import br.com.itau.geradornotafiscal.model.Regiao;
import br.com.itau.geradornotafiscal.model.RegimeTributacaoPJ;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static br.com.itau.geradornotafiscal.fixtures.DestinatarioFixture.criarDestinatario;
import static br.com.itau.geradornotafiscal.fixtures.ItemNotaFiscalFixture.criarItemNotaFiscal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NotaFiscalFixture {

    public static NotaFiscal criarNotaFiscal() {
        return criarNotaFiscal(DestinatarioFixture.criarDestinatario());
    }

    public static NotaFiscal criarNotaFiscal(final RegimeTributacaoPJ regimeTributacaoPJ) {
        return criarNotaFiscal(DestinatarioFixture.criarDestinatario(regimeTributacaoPJ));
    }

    public static NotaFiscal criarNotaFiscal(final Finalidade finalidade) {
        return criarNotaFiscal(DestinatarioFixture.criarDestinatario(finalidade));
    }

    public static NotaFiscal criarNotaFiscal(final Regiao regiao) {
        return criarNotaFiscal(criarDestinatario(regiao));
    }

    private static NotaFiscal criarNotaFiscal(final Destinatario destinatario) {
        return NotaFiscal.builder()
                .idNotaFiscal(UUID.randomUUID().toString())
                .data(LocalDateTime.now())
                .valorTotalItens(1000)
                .valorFrete(10.48)
                .itens(List.of(criarItemNotaFiscal()))
                .destinatario(destinatario)
                .build();
    }
}
