package br.com.itau.geradornotafiscal.service;

import br.com.itau.geradornotafiscal.model.Item;
import br.com.itau.geradornotafiscal.model.ItemNotaFiscal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CalculadoraAliquotaProduto {

    public List<ItemNotaFiscal> calcularAliquota(final List<Item> items, final double aliquotaPercentual) {
        log.info("Calculando alíquota dos itens para o percentual: [{}]", aliquotaPercentual);

        final List<ItemNotaFiscal> itemNotaFiscalList = items.stream()
                .map(item -> {
                    final double valorTributo = item.getValorUnitario() * aliquotaPercentual;
                    return ItemNotaFiscal.builder()
                            .idItem(item.getIdItem())
                            .descricao(item.getDescricao())
                            .valorUnitario(item.getValorUnitario())
                            .quantidade(item.getQuantidade())
                            .valorTributoItem(valorTributo)
                            .build();
                })
                .collect(Collectors.toList());

        log.info("Itens calculados com sucesso.");
        return itemNotaFiscalList;
    }
}


