package br.com.github.biblioteca.infrastructure.handler;

import br.com.github.biblioteca.infrastructure.exception.*;
import br.com.github.biblioteca.infrastructure.service.impl.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidacaoExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageService messageService;

    @ExceptionHandler({ CampoObrigatorioException.class })
    public ResponseEntity<Object> handleEsteCampoObrigatorioException(CampoObrigatorioException exception, WebRequest request) {
        Object[] args = { exception.getMessage() };
        return handlerException(exception, HttpStatus.BAD_REQUEST, request, "validacao.campo-obrigatorio", args);
    }

    @ExceptionHandler({ CampoTamanhoMaximoException.class })
    public ResponseEntity<Object> handleCampoTamanhoMaximoException(CampoTamanhoMaximoException exception, WebRequest request) {
        return handlerException(exception, HttpStatus.BAD_REQUEST, request, "validacao.campo-tamanho-maximo", exception.getArgs());
    }

    @ExceptionHandler({ CpfInvalidoException.class })
    public ResponseEntity<Object> handleCpfInvalidoException(CpfInvalidoException exception, WebRequest request) {
        Object[] args = { exception.getMessage() };
        return handlerException(exception, HttpStatus.BAD_REQUEST, request, "validacao.cpf-invalido", args);
    }

    @ExceptionHandler({ EmailInvalidException.class })
    public ResponseEntity<Object> handleEmailInvalidException(EmailInvalidException exception, WebRequest request) {
        Object[] args = { exception.getMessage() };
        return handlerException(exception, HttpStatus.BAD_REQUEST, request, "validacao.email-invalido", args);
    }

    @ExceptionHandler({ RecursoNaoEncontradoException.class })
    public ResponseEntity<Object> handleEmailInvalidException(RecursoNaoEncontradoException exception, WebRequest request) {
        Object[] args = { exception.getMessage() };
        return handlerException(exception, HttpStatus.NOT_FOUND, request, "validacao.recurso-nao-encontrado", args);
    }

    protected ResponseEntity<Object> handlerException(Exception exception, HttpStatus status, WebRequest request, String key, Object[] args) {
        ApiError<List<String>> response = new ApiError<>(List.of((messageService.getMessage(key, args))));
        response.setStatusCode(status.value());
        return handleExceptionInternal(exception, response, new HttpHeaders(), status, request);
    }

}
