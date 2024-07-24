package br.com.itau.geradornotafiscal.service.impl;

import br.com.itau.geradornotafiscal.model.NotaFiscal;
import br.com.itau.geradornotafiscal.port.out.EntregaIntegrationPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EntregaService {
    private final EntregaIntegrationPort entregaIntegrationPort;

    public void agendarEntrega(final NotaFiscal notaFiscal) {
        log.info("Iniciando o agendamento da entrega para a nota fiscal com ID: [{}].", notaFiscal.getIdNotaFiscal());

        try {
            //Simula o agendamento da entrega
            Thread.sleep(150);
            this.entregaIntegrationPort.criarAgendamentoEntrega(notaFiscal);
            log.info("Entrega agendada com sucesso para a nota fiscal com ID: [{}].", notaFiscal.getIdNotaFiscal());
        } catch (final InterruptedException e) {
            log.error("Erro ao tentar agendar a entrega para a nota fiscal com ID: [{}].", notaFiscal.getIdNotaFiscal(), e);
            throw new RuntimeException(e);
        }

    }
}
