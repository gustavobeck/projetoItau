package br.com.itau.geradornotafiscal.service.impl;

import br.com.itau.geradornotafiscal.model.NotaFiscal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RegistroService {

    public void registrarNotaFiscal(final NotaFiscal notaFiscal) {
        log.info("Iniciando o registro da nota fiscal com ID: [{}].", notaFiscal.getIdNotaFiscal());

        try {
            //Simula o registro da nota fiscal
            Thread.sleep(500);
            log.info("Nota fiscal com ID: [{}] registrada com sucesso.", notaFiscal.getIdNotaFiscal());
        } catch (final InterruptedException e) {
            log.error("Erro ao registrar nota fiscal com ID: [{}].", notaFiscal.getIdNotaFiscal(), e);
            throw new RuntimeException(e);
        }
    }
}
