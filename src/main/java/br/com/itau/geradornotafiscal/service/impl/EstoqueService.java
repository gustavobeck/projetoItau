package br.com.itau.geradornotafiscal.service.impl;

import br.com.itau.geradornotafiscal.model.NotaFiscal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EstoqueService {

    public void enviarNotaFiscalParaBaixaEstoque(final NotaFiscal notaFiscal) {
        log.info("Iniciando o envio da nota fiscal com ID: [{}] para baixa de estoque.", notaFiscal.getIdNotaFiscal());

        try {
            //Simula envio de nota fiscal para baixa de estoque
            Thread.sleep(380);
            log.info("Nota fiscal com ID: [{}] enviada para baixa de estoque com sucesso.", notaFiscal.getIdNotaFiscal());
        } catch (final InterruptedException e) {
            log.error("Erro ao enviar nota fiscal com ID: [{}] para baixa de estoque.", notaFiscal.getIdNotaFiscal(), e);
            throw new RuntimeException(e);
        }
    }
}
