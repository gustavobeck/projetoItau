package br.com.itau.geradornotafiscal.fixtures;

import br.com.itau.geradornotafiscal.model.Endereco;
import br.com.itau.geradornotafiscal.model.Finalidade;
import br.com.itau.geradornotafiscal.model.Regiao;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EnderecoFixture {

    public static Endereco criarEndereco() {
        return criarEndereco(Finalidade.ENTREGA, Regiao.SUDESTE);
    }

    public static Endereco criarEndereco(final Finalidade finalidade) {
        return criarEndereco(finalidade, Regiao.SUDESTE);
    }

    public static Endereco criarEndereco(final Regiao regiao) {
        return criarEndereco(Finalidade.ENTREGA, regiao);
    }

    private static Endereco criarEndereco(final Finalidade finalidade, final Regiao regiao) {
        return Endereco.builder()
                .cep("03105003")
                .logradouro("Av do estado")
                .numero("5533")
                .estado("SP")
                .complemento("4 anndar b")
                .finalidade(finalidade)
                .regiao(regiao)
                .build();
    }
}
