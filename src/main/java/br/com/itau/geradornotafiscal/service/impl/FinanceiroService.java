package br.com.itau.geradornotafiscal.service.impl;

import br.com.itau.geradornotafiscal.model.NotaFiscal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FinanceiroService {

    public void enviarNotaFiscalParaContasReceber(final NotaFiscal notaFiscal) {
        log.info("Iniciando o envio da nota fiscal com ID: [{}] para contas a receber.", notaFiscal.getIdNotaFiscal());

        try {
            //Simula o envio da nota fiscal para o contas a receber
            Thread.sleep(250);
            log.info("Nota fiscal com ID: [{}] enviada para contas a receber com sucesso.", notaFiscal.getIdNotaFiscal());
        } catch (final InterruptedException e) {
            log.error("Erro ao enviar nota fiscal com ID: [{}] para contas a receber.", notaFiscal.getIdNotaFiscal(), e);
            throw new RuntimeException(e);
        }
    }
}
