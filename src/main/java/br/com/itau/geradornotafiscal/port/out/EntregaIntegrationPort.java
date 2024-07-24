package br.com.itau.geradornotafiscal.port.out;

import br.com.itau.geradornotafiscal.model.NotaFiscal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EntregaIntegrationPort {

    public void criarAgendamentoEntrega(final NotaFiscal notaFiscal) {
        log.info("Criando o agendamento da entrega para a nota fiscal com ID: [{}].", notaFiscal.getIdNotaFiscal());

        try {
            //Simula o agendamento da entrega
            Thread.sleep(200);
            log.info("Agendamento da entrega para a nota fiscal com ID: [{}] criado com sucesso.", notaFiscal.getIdNotaFiscal());
        } catch (final InterruptedException e) {
            log.error("Erro ao criar agendamento da entrega para a nota fiscal com ID: [{}].", notaFiscal.getIdNotaFiscal(), e);
            throw new RuntimeException(e);
        }
    }
}
