package br.com.itau.geradornotafiscal.service.impl;

import br.com.itau.geradornotafiscal.model.Destinatario;
import br.com.itau.geradornotafiscal.model.Endereco;
import br.com.itau.geradornotafiscal.model.Finalidade;
import br.com.itau.geradornotafiscal.model.ItemNotaFiscal;
import br.com.itau.geradornotafiscal.model.NotaFiscal;
import br.com.itau.geradornotafiscal.model.Pedido;
import br.com.itau.geradornotafiscal.model.Regiao;
import br.com.itau.geradornotafiscal.model.RegimeTributacaoPJ;
import br.com.itau.geradornotafiscal.model.TipoPessoa;
import br.com.itau.geradornotafiscal.service.CalculadoraAliquotaProduto;
import br.com.itau.geradornotafiscal.service.GeradorNotaFiscalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GeradorNotaFiscalServiceImpl implements GeradorNotaFiscalService {

    private final EstoqueService estoqueService;
    private final RegistroService registroService;
    private final EntregaService entregaService;
    private final FinanceiroService financeiroService;
    private final CalculadoraAliquotaProduto calculadoraAliquotaProduto;

    @Override
    public NotaFiscal gerarNotaFiscal(final Pedido pedido) {

        log.info("Iniciando processo de geração da nota fiscal para o pedido: [{}]", pedido.getIdPedido());

        final Destinatario destinatario = pedido.getDestinatario();
        final TipoPessoa tipoPessoa = destinatario.getTipoPessoa();

        final double valorTotalItens = pedido.getValorTotalItens();
        final double aliquota = this.calcularAliquota(destinatario, tipoPessoa, valorTotalItens);

        final List<ItemNotaFiscal> itemNotaFiscalList = this.calculadoraAliquotaProduto.calcularAliquota(pedido.getItens(), aliquota);

        //Regras diferentes para frete

        final Regiao regiao = buscarRegiao(destinatario);

        final double valorFrete = pedido.getValorFrete();
        final double valorFreteFinal = calcularValorFreteFinal(valorFrete, regiao);

        // Create the NotaFiscal object
        final String idNotaFiscal = UUID.randomUUID().toString();

        log.info("Criando nota fiscal com ID: [{}]", idNotaFiscal);
        final NotaFiscal notaFiscal = criarNotaFiscal(pedido, idNotaFiscal, valorFreteFinal, itemNotaFiscalList);

        log.info("Iniciando processamento da nota fiscal.");
        this.processarNotaFiscal(notaFiscal);

        log.info("Nota fiscal criada e processada com sucesso.");
        return notaFiscal;
    }

    private double calcularAliquota(final Destinatario destinatario, final TipoPessoa tipoPessoa, final double valorTotalItens) {
        if (tipoPessoa == TipoPessoa.FISICA) {
            return calcularAliquotaPF(valorTotalItens);
        } else if (tipoPessoa == TipoPessoa.JURIDICA) {
            final RegimeTributacaoPJ regimeTributacao = destinatario.getRegimeTributacao();
            final double[] listaAliquota = regimeTributacao.getAliquota();
            return calcularAliquotaPJ(valorTotalItens, listaAliquota);
        }
        return 0;
    }


    private static double calcularAliquotaPF(final double valorTotalItens) {
        if (valorTotalItens < 500) {
            return 0;
        } else if (valorTotalItens <= 2000) {
            return 0.12;
        } else if (valorTotalItens <= 3500) {
            return 0.15;
        } else {
            return 0.17;
        }
    }

    private static double calcularAliquotaPJ(final double valorTotalItens, final double[] listaAliquota) {
        if (valorTotalItens < 1000) {
            return listaAliquota[0];
        } else if (valorTotalItens <= 2000) {
            return listaAliquota[1];
        } else if (valorTotalItens <= 5000) {
            return listaAliquota[2];
        } else {
            return listaAliquota[3];
        }
    }

    private static Regiao buscarRegiao(final Destinatario destinatario) {
        return destinatario.getEnderecos().stream()
                .filter(endereco -> endereco.getFinalidade() == Finalidade.ENTREGA || endereco.getFinalidade() == Finalidade.COBRANCA_ENTREGA)
                .map(Endereco::getRegiao)
                .findFirst()
                .orElse(null);
    }

    private static double calcularValorFreteFinal(final double valorFrete, final Regiao regiao) {
        return valorFrete * (regiao != null ? regiao.getPercentualFrete() : 1);
    }

    private static NotaFiscal criarNotaFiscal(final Pedido pedido, final String idNotaFiscal, final double valorFreteFinal, final List<ItemNotaFiscal> itemNotaFiscalList) {
        return NotaFiscal.builder()
                .idNotaFiscal(idNotaFiscal)
                .data(LocalDateTime.now())
                .valorTotalItens(pedido.getValorTotalItens())
                .valorFrete(valorFreteFinal)
                .itens(itemNotaFiscalList)
                .destinatario(pedido.getDestinatario())
                .build();
    }

    private void processarNotaFiscal(final NotaFiscal notaFiscal) {
        log.info("Enviando nota fiscal para baixa de estoque.");
        this.estoqueService.enviarNotaFiscalParaBaixaEstoque(notaFiscal);

        log.info("Enviando nota fiscal para registro.");
        this.registroService.registrarNotaFiscal(notaFiscal);

        log.info("Agendando entrega.");
        this.entregaService.agendarEntrega(notaFiscal);

        log.info("Enviando nota fiscal para contas a receber.");
        this.financeiroService.enviarNotaFiscalParaContasReceber(notaFiscal);
    }
}