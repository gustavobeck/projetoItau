package br.com.itau.geradornotafiscal.web.handler;

import br.com.itau.geradornotafiscal.web.handler.response.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.METHOD_NOT_ALLOWED;


@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {
    private static final String EXCEPTION = "Exception: ";

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ExceptionResponse> handleGenericException(
            final Exception e, final HttpServletRequest request) {
        log.error(EXCEPTION, e);
        return this.getExceptionResponse(INTERNAL_SERVER_ERROR, request, e.getMessage());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadableException(
            final HttpMessageNotReadableException e, final HttpServletRequest request) {
        log.error(EXCEPTION, e);
        return this.getExceptionResponse(BAD_REQUEST, request, e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionResponse> handleMethodNotSupported(final HttpServletRequest request) {
        return this.getExceptionResponse(METHOD_NOT_ALLOWED, request, "Método HTTP não suportado.");
    }

    private ResponseEntity<ExceptionResponse> getExceptionResponse(final HttpStatus status,
                                                                   final HttpServletRequest request,
                                                                   final String message) {
        var uri = "";
        try {
            uri = request.getRequestURI();
        } catch (final Exception e) {
            log.error("Não foi possível recuperar os dados do error.");
        }

        final var response = ExceptionResponse.builder()
                .status(status.value())
                .message(message)
                .path(uri)
                .build();

        return ResponseEntity.status(status).body(response);
    }
}
