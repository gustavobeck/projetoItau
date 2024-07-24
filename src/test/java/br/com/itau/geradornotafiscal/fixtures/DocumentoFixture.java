package br.com.itau.geradornotafiscal.fixtures;

import br.com.itau.geradornotafiscal.model.Documento;
import br.com.itau.geradornotafiscal.model.TipoDocumento;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DocumentoFixture {

    public static Documento criarDocumento() {
        return Documento.builder()
                .numero("43212343211")
                .tipo(TipoDocumento.CPF)
                .build();
    }

    public static Documento criarDocumentoPJ() {
        return Documento.builder()
                .numero("04955706000168")
                .tipo(TipoDocumento.CNPJ)
                .build();
    }
}
