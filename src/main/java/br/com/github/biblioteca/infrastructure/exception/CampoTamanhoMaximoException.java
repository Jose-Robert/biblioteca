package br.com.github.biblioteca.infrastructure.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CampoTamanhoMaximoException extends RuntimeException {

    private static final long serialVersionUID = -2419164056224622081L;

    private final String nome;
    private final String tamanho;

    public CampoTamanhoMaximoException(String nome, String tamanho) {
        this.nome = nome;
        this.tamanho = tamanho;
    }

    public Object[] getArgs() {
        return new Object[] {nome, tamanho};
    }
}
