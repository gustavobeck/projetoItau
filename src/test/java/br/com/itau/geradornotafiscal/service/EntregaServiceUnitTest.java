package br.com.itau.geradornotafiscal.service;

import br.com.itau.geradornotafiscal.fixtures.NotaFiscalFixture;
import br.com.itau.geradornotafiscal.model.NotaFiscal;
import br.com.itau.geradornotafiscal.port.out.EntregaIntegrationPort;
import br.com.itau.geradornotafiscal.service.impl.EntregaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EntregaServiceUnitTest {

    @Mock
    private EntregaIntegrationPort entregaIntegrationPort;

    @InjectMocks
    private EntregaService entregaService;

    @Test
    void deveAgendarEntregaComSucesso() {
        final NotaFiscal notaFiscal = NotaFiscalFixture.criarNotaFiscal();

        this.entregaService.agendarEntrega(notaFiscal);

        verify(this.entregaIntegrationPort).criarAgendamentoEntrega(any());
    }
}
