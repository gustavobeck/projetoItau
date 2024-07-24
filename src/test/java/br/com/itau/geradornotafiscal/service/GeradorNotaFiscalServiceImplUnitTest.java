package br.com.itau.geradornotafiscal.service;

import br.com.itau.geradornotafiscal.fixtures.NotaFiscalFixture;
import br.com.itau.geradornotafiscal.fixtures.PedidoFixture;
import br.com.itau.geradornotafiscal.model.Destinatario;
import br.com.itau.geradornotafiscal.model.Finalidade;
import br.com.itau.geradornotafiscal.model.NotaFiscal;
import br.com.itau.geradornotafiscal.model.Pedido;
import br.com.itau.geradornotafiscal.model.Regiao;
import br.com.itau.geradornotafiscal.model.RegimeTributacaoPJ;
import br.com.itau.geradornotafiscal.service.impl.EntregaService;
import br.com.itau.geradornotafiscal.service.impl.EstoqueService;
import br.com.itau.geradornotafiscal.service.impl.FinanceiroService;
import br.com.itau.geradornotafiscal.service.impl.GeradorNotaFiscalServiceImpl;
import br.com.itau.geradornotafiscal.service.impl.RegistroService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static br.com.itau.geradornotafiscal.fixtures.ItemNotaFiscalFixture.criarItemNotaFiscal;
import static br.com.itau.geradornotafiscal.fixtures.NotaFiscalFixture.criarNotaFiscal;
import static br.com.itau.geradornotafiscal.fixtures.PedidoFixture.criarPedido;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GeradorNotaFiscalServiceImplUnitTest {

    @InjectMocks
    private GeradorNotaFiscalServiceImpl geradorNotaFiscalService;

    @Mock
    private EstoqueService estoqueService;

    @Mock
    private RegistroService registroService;

    @Mock
    private EntregaService entregaService;

    @Mock
    private FinanceiroService financeiroService;

    @Mock
    private CalculadoraAliquotaProduto calculadoraAliquotaProduto;

    @Test
    void deveGerarNotaFiscalComSucesso() {
        final Pedido pedido = PedidoFixture.criarPedido();
        final NotaFiscal notaFiscal = NotaFiscalFixture.criarNotaFiscal();

        when(this.calculadoraAliquotaProduto.calcularAliquota(any(), anyDouble())).thenReturn(List.of(criarItemNotaFiscal()));

        final NotaFiscal result = this.geradorNotaFiscalService.gerarNotaFiscal(pedido);

        assertNotNull(result);
        assertFalse(notaFiscal.getIdNotaFiscal().isBlank());
        assertNotNull(notaFiscal.getData());
        assertEquals(notaFiscal.getValorTotalItens(), result.getValorTotalItens());
        assertEquals(notaFiscal.getValorFrete(), result.getValorFrete());
        assertItensNotaFiscal(result, notaFiscal);
        assertDestinatario(result, notaFiscal);

        verify(this.estoqueService).enviarNotaFiscalParaBaixaEstoque(result);
        verify(this.registroService).registrarNotaFiscal(result);
        verify(this.entregaService).agendarEntrega(result);
        verify(this.financeiroService).enviarNotaFiscalParaContasReceber(result);
    }

    @ParameterizedTest
    @EnumSource(RegimeTributacaoPJ.class)
    void deveGerarNotaFiscalComSucessoComAliquotaPJ(final RegimeTributacaoPJ regimeTributacaoPJ) {
        final Pedido pedido = PedidoFixture.criarPedido(regimeTributacaoPJ);
        final NotaFiscal notaFiscal = NotaFiscalFixture.criarNotaFiscal(regimeTributacaoPJ);

        when(this.calculadoraAliquotaProduto.calcularAliquota(any(), anyDouble())).thenReturn(List.of(criarItemNotaFiscal()));

        final NotaFiscal result = this.geradorNotaFiscalService.gerarNotaFiscal(pedido);

        assertNotNull(result);
        assertFalse(notaFiscal.getIdNotaFiscal().isBlank());
        assertNotNull(notaFiscal.getData());
        assertEquals(notaFiscal.getValorTotalItens(), result.getValorTotalItens());
        assertEquals(notaFiscal.getValorFrete(), result.getValorFrete());
        assertItensNotaFiscal(result, notaFiscal);
        assertDestinatario(result, notaFiscal);

        verify(this.estoqueService).enviarNotaFiscalParaBaixaEstoque(result);
        verify(this.registroService).registrarNotaFiscal(result);
        verify(this.entregaService).agendarEntrega(result);
        verify(this.financeiroService).enviarNotaFiscalParaContasReceber(result);
    }

    @ParameterizedTest
    @MethodSource("finalidadeEValorFrete")
    void deveGerarNotaFiscalComSucessoComFinalidadesDiferentes(final Finalidade finalidade, final double valorFrete) {
        final Pedido pedido = PedidoFixture.criarPedido(finalidade);
        final NotaFiscal notaFiscal = NotaFiscalFixture.criarNotaFiscal(finalidade);

        when(this.calculadoraAliquotaProduto.calcularAliquota(any(), anyDouble())).thenReturn(List.of(criarItemNotaFiscal()));

        final NotaFiscal result = this.geradorNotaFiscalService.gerarNotaFiscal(pedido);

        assertNotNull(result);
        assertFalse(notaFiscal.getIdNotaFiscal().isBlank());
        assertNotNull(notaFiscal.getData());
        assertEquals(notaFiscal.getValorTotalItens(), result.getValorTotalItens());
        assertEquals(valorFrete, result.getValorFrete());
        assertItensNotaFiscal(result, notaFiscal);
        assertDestinatario(result, notaFiscal);

        verify(this.estoqueService).enviarNotaFiscalParaBaixaEstoque(result);
        verify(this.registroService).registrarNotaFiscal(result);
        verify(this.entregaService).agendarEntrega(result);
        verify(this.financeiroService).enviarNotaFiscalParaContasReceber(result);
    }

    @ParameterizedTest
    @MethodSource("regiaoEValorFrete")
    void deveGerarNotaFiscalComSucessoComRegioesDiferentes(final Regiao regiao, final double valorFrete) {
        final Pedido pedido = criarPedido(regiao);
        final NotaFiscal notaFiscal = criarNotaFiscal(regiao);

        when(this.calculadoraAliquotaProduto.calcularAliquota(any(), anyDouble())).thenReturn(List.of(criarItemNotaFiscal()));

        final NotaFiscal result = this.geradorNotaFiscalService.gerarNotaFiscal(pedido);

        assertNotNull(result);
        assertFalse(notaFiscal.getIdNotaFiscal().isBlank());
        assertNotNull(notaFiscal.getData());
        assertEquals(notaFiscal.getValorTotalItens(), result.getValorTotalItens());
        assertEquals(valorFrete, result.getValorFrete());
        assertItensNotaFiscal(result, notaFiscal);
        assertDestinatario(result, notaFiscal);

        verify(this.estoqueService).enviarNotaFiscalParaBaixaEstoque(result);
        verify(this.registroService).registrarNotaFiscal(result);
        verify(this.entregaService).agendarEntrega(result);
        verify(this.financeiroService).enviarNotaFiscalParaContasReceber(result);
    }

    private static Stream<Arguments> finalidadeEValorFrete() {
        return Stream.of(
                Arguments.of(Finalidade.ENTREGA, 10.48),
                Arguments.of(Finalidade.COBRANCA_ENTREGA, 10.48),
                Arguments.of(Finalidade.COBRANCA, 10.0),
                Arguments.of(Finalidade.OUTROS, 10.0)
        );
    }

    private static Stream<Arguments> regiaoEValorFrete() {
        return Stream.of(
                Arguments.of(Regiao.NORTE, 10.8),
                Arguments.of(Regiao.NORDESTE, 10.85),
                Arguments.of(Regiao.CENTRO_OESTE, 10.700000000000001),
                Arguments.of(Regiao.SUDESTE, 10.48),
                Arguments.of(Regiao.SUL, 10.600000000000001)
        );
    }

    private static void assertDestinatario(final NotaFiscal result, final NotaFiscal notaFiscal) {
        assertNotNull(result.getDestinatario());
        final var destinatario = notaFiscal.getDestinatario();
        final var destinatarioResult = result.getDestinatario();
        assertEquals(destinatario.getNome(), destinatarioResult.getNome());
        assertEquals(destinatario.getTipoPessoa(), destinatarioResult.getTipoPessoa());
        assertEquals(destinatario.getRegimeTributacao(), destinatarioResult.getRegimeTributacao());

        assertDocumentoDestinatario(destinatarioResult, destinatario);

        assertEnderecoDestinatario(destinatarioResult, destinatario);
    }

    private static void assertDocumentoDestinatario(final Destinatario destinatarioResult, final Destinatario destinatario) {
        assertNotNull(destinatarioResult.getDocumentos());
        assertNotNull(destinatarioResult.getDocumentos().get(0));
        final var documento = destinatario.getDocumentos().get(0);
        final var documentoResult = destinatarioResult.getDocumentos().get(0);
        assertEquals(documento.getNumero(), documentoResult.getNumero());
        assertEquals(documento.getTipo(), documentoResult.getTipo());
    }

    private static void assertEnderecoDestinatario(final Destinatario destinatarioResult, final Destinatario destinatario) {
        assertNotNull(destinatarioResult.getEnderecos());
        assertNotNull(destinatarioResult.getEnderecos().get(0));
        final var endereco = destinatario.getEnderecos().get(0);
        final var enderecoResult = destinatarioResult.getEnderecos().get(0);
        assertEquals(endereco.getCep(), enderecoResult.getCep());
        assertEquals(endereco.getLogradouro(), enderecoResult.getLogradouro());
        assertEquals(endereco.getNumero(), enderecoResult.getNumero());
        assertEquals(endereco.getEstado(), enderecoResult.getEstado());
        assertEquals(endereco.getComplemento(), enderecoResult.getComplemento());
        assertEquals(endereco.getFinalidade(), enderecoResult.getFinalidade());
        assertEquals(endereco.getRegiao(), enderecoResult.getRegiao());
    }

    private static void assertItensNotaFiscal(final NotaFiscal result, final NotaFiscal notaFiscal) {
        assertNotNull(result.getItens());
        assertNotNull(result.getItens().get(0));
        final var itemNotaFiscal = notaFiscal.getItens().get(0);
        final var itemNotaFiscalResult = result.getItens().get(0);
        assertEquals(itemNotaFiscal.getIdItem(), itemNotaFiscalResult.getIdItem());
        assertEquals(itemNotaFiscal.getDescricao(), itemNotaFiscalResult.getDescricao());
        assertEquals(itemNotaFiscal.getValorUnitario(), itemNotaFiscalResult.getValorUnitario());
        assertEquals(itemNotaFiscal.getQuantidade(), itemNotaFiscalResult.getQuantidade());
        assertEquals(itemNotaFiscal.getValorTributoItem(), itemNotaFiscalResult.getValorTributoItem());
    }
}
