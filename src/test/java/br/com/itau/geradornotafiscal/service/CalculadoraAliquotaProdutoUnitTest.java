package br.com.itau.geradornotafiscal.service;

import br.com.itau.geradornotafiscal.model.Item;
import br.com.itau.geradornotafiscal.model.ItemNotaFiscal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static br.com.itau.geradornotafiscal.fixtures.ItemFixture.criarItem;
import static br.com.itau.geradornotafiscal.fixtures.ItemFixture.criarItem2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class CalculadoraAliquotaProdutoUnitTest {

    @Autowired
    private CalculadoraAliquotaProduto calculadoraAliquotaProduto;

    @Test
    void deveCalcularAliquotaCorretamenteParaListaDeItens() {
        final List<Item> items = List.of(criarItem(), criarItem2());
        final double aliquotaPercentual = 0.1;

        final List<ItemNotaFiscal> resultado = this.calculadoraAliquotaProduto.calcularAliquota(items, aliquotaPercentual);

        assertEquals(2, resultado.size());

        final ItemNotaFiscal itemNotaFiscal1 = resultado.get(0);
        assertEquals("1", itemNotaFiscal1.getIdItem());
        assertEquals("Monitor LCD SAMSUNG", itemNotaFiscal1.getDescricao());
        assertEquals(700, itemNotaFiscal1.getValorUnitario());
        assertEquals(8, itemNotaFiscal1.getQuantidade());
        assertEquals(70.0, itemNotaFiscal1.getValorTributoItem());

        final ItemNotaFiscal itemNotaFiscal2 = resultado.get(1);
        assertEquals("2", itemNotaFiscal2.getIdItem());
        assertEquals("Celular SAMSUNG S24", itemNotaFiscal2.getDescricao());
        assertEquals(1000.0, itemNotaFiscal2.getValorUnitario());
        assertEquals(5, itemNotaFiscal2.getQuantidade());
        assertEquals(100.0, itemNotaFiscal2.getValorTributoItem());
    }

    @Test
    void deveRetornarListaVaziaParaListaDeItensVazia() {
        final List<Item> items = new ArrayList<>();
        final double aliquotaPercentual = 0.1;

        final List<ItemNotaFiscal> resultado = this.calculadoraAliquotaProduto.calcularAliquota(items, aliquotaPercentual);

        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveCalcularAliquotaCorretamenteComAliquotaZero() {
        final List<Item> items = List.of(criarItem(), criarItem2());
        final double aliquotaPercentual = 0.0;

        final List<ItemNotaFiscal> resultado = this.calculadoraAliquotaProduto.calcularAliquota(items, aliquotaPercentual);

        assertEquals(2, resultado.size());

        final ItemNotaFiscal itemNotaFiscal1 = resultado.get(0);
        assertEquals("1", itemNotaFiscal1.getIdItem());
        assertEquals("Monitor LCD SAMSUNG", itemNotaFiscal1.getDescricao());
        assertEquals(700, itemNotaFiscal1.getValorUnitario());
        assertEquals(8, itemNotaFiscal1.getQuantidade());
        assertEquals(0.0, itemNotaFiscal1.getValorTributoItem());

        final ItemNotaFiscal itemNotaFiscal2 = resultado.get(1);
        assertEquals("2", itemNotaFiscal2.getIdItem());
        assertEquals("Celular SAMSUNG S24", itemNotaFiscal2.getDescricao());
        assertEquals(1000.0, itemNotaFiscal2.getValorUnitario());
        assertEquals(5, itemNotaFiscal2.getQuantidade());
        assertEquals(0.0, itemNotaFiscal2.getValorTributoItem());
    }
}
