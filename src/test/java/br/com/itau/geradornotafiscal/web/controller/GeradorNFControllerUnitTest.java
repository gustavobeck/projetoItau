package br.com.itau.geradornotafiscal.web.controller;


import br.com.itau.geradornotafiscal.fixtures.NotaFiscalFixture;
import br.com.itau.geradornotafiscal.fixtures.PedidoFixture;
import br.com.itau.geradornotafiscal.model.NotaFiscal;
import br.com.itau.geradornotafiscal.model.Pedido;
import br.com.itau.geradornotafiscal.service.GeradorNotaFiscalService;
import br.com.itau.geradornotafiscal.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class GeradorNFControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GeradorNotaFiscalService geradorNotaFiscalService;

    protected final ObjectMapper mapper = JsonUtil.getMapper();


    @Test
    void deveGerarANotaFiscalComSucesso() throws Exception {
        final Pedido pedido = PedidoFixture.criarPedido();
        final NotaFiscal notaFiscal = NotaFiscalFixture.criarNotaFiscal();

        when(this.geradorNotaFiscalService.gerarNotaFiscal(any())).thenReturn(notaFiscal);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/pedido/gerarNotaFiscal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.mapToJson(pedido)))
                .andExpect(status().isOk())
                .andReturn();

        verify(this.geradorNotaFiscalService).gerarNotaFiscal(any());
    }

    @Test
    void deveRetornarUmInternalServerErrorQuandoReceberUmaExceptionGenericaAoTentarGerarNotaFiscal() throws Exception {
        final Pedido pedido = PedidoFixture.criarPedido();

        doThrow(new RuntimeException()).when(this.geradorNotaFiscalService).gerarNotaFiscal(any());

        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/pedido/gerarNotaFiscal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.mapToJson(pedido)))
                .andExpect(status().isInternalServerError())
                .andReturn();

        verify(this.geradorNotaFiscalService).gerarNotaFiscal(any());
    }

    @Test
    void deveRetornarUmBadRequestQuandoReceberUmHttpMessageNotReadableExceptionAoTentarGerarNotaFiscal() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/pedido/gerarNotaFiscal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("x"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void deveRetornarUmMethodNotAllowedQuandoReceberUmaHttpRequestMethodNotSupportedException() throws Exception {
        final Pedido pedido = PedidoFixture.criarPedido();

        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/pedido/gerarNotaFiscal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.mapToJson(pedido)))
                .andExpect(status().isMethodNotAllowed())
                .andReturn();
    }

    protected String mapToJson(final Object obj) throws JsonProcessingException {
        return this.mapper.writeValueAsString(obj);
    }
}
