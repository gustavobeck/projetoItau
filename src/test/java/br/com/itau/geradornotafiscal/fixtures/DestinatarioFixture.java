package br.com.itau.geradornotafiscal.fixtures;

import br.com.itau.geradornotafiscal.model.Destinatario;
import br.com.itau.geradornotafiscal.model.Documento;
import br.com.itau.geradornotafiscal.model.Endereco;
import br.com.itau.geradornotafiscal.model.Finalidade;
import br.com.itau.geradornotafiscal.model.Regiao;
import br.com.itau.geradornotafiscal.model.RegimeTributacaoPJ;
import br.com.itau.geradornotafiscal.model.TipoPessoa;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

import static br.com.itau.geradornotafiscal.fixtures.DocumentoFixture.criarDocumento;
import static br.com.itau.geradornotafiscal.fixtures.DocumentoFixture.criarDocumentoPJ;
import static br.com.itau.geradornotafiscal.fixtures.EnderecoFixture.criarEndereco;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DestinatarioFixture {

    public static Destinatario criarDestinatario() {
        return criarDestinatario(TipoPessoa.FISICA, null, List.of(criarDocumento()), List.of(EnderecoFixture.criarEndereco()));
    }

    public static Destinatario criarDestinatario(final RegimeTributacaoPJ regimeTributacaoPJ) {
        return criarDestinatario(TipoPessoa.JURIDICA, regimeTributacaoPJ, List.of(criarDocumentoPJ()), List.of(EnderecoFixture.criarEndereco()));
    }

    public static Destinatario criarDestinatario(final Finalidade finalidade) {
        return criarDestinatario(TipoPessoa.FISICA, null, List.of(criarDocumento()), List.of(EnderecoFixture.criarEndereco(finalidade)));
    }

    public static Destinatario criarDestinatario(final Regiao regiao) {
        return criarDestinatario(TipoPessoa.FISICA, null, List.of(criarDocumento()), List.of(criarEndereco(regiao)));
    }

    private static Destinatario criarDestinatario(final TipoPessoa tipoPessoa, final RegimeTributacaoPJ regimeTributacao,
                                                  final List<Documento> documentos, final List<Endereco> enderecos) {
        return Destinatario.builder()
                .nome("John Doe")
                .tipoPessoa(tipoPessoa)
                .regimeTributacao(regimeTributacao)
                .documentos(documentos)
                .enderecos(enderecos)
                .build();
    }
}
