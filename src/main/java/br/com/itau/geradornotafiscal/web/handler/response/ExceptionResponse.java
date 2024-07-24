package br.com.itau.geradornotafiscal.web.handler.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {

    @Builder.Default
    private ZonedDateTime timestamp = ZonedDateTime.now();
    private Integer status;
    private String message;
    private String path;
}
