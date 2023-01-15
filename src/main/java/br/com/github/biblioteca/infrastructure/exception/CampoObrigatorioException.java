package br.com.github.biblioteca.infrastructure.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CampoObrigatorioException extends RuntimeException {

    private static final long serialVersionUID = -4792509148061511516L;

    public CampoObrigatorioException(String message) {
        super(message);
    }
}
