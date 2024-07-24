package br.com.itau.geradornotafiscal.web.controller;

import br.com.itau.geradornotafiscal.model.NotaFiscal;
import br.com.itau.geradornotafiscal.model.Pedido;
import br.com.itau.geradornotafiscal.service.GeradorNotaFiscalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/pedido")
@RequiredArgsConstructor
@Validated
@Slf4j
public class GeradorNFController {

    private final GeradorNotaFiscalService notaFiscalService;

    @PostMapping("/gerarNotaFiscal")
    public ResponseEntity<NotaFiscal> gerarNotaFiscal(@RequestBody final Pedido pedido) {
        // Lógica de processamento do pedido
        // Aqui você pode realizar as operações desejadas com o objeto Pedido

        // Exemplo de retorno
        log.info("Requisição para gerar nota fiscal recebida para o pedido: [{}]", pedido.getIdPedido());

        final NotaFiscal notaFiscal = this.notaFiscalService.gerarNotaFiscal(pedido);

        log.info("Nota fiscal gerada com sucesso para o pedido: [{}]", pedido.getIdPedido());

        return new ResponseEntity<>(notaFiscal, HttpStatus.OK);
    }

}
