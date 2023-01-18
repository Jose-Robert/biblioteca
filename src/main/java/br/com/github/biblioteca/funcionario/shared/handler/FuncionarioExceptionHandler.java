package br.com.github.biblioteca.cliente.shared.handler;

import br.com.github.biblioteca.cliente.shared.exception.ClienteJaExistenteCPFEmailException;
import br.com.github.biblioteca.infrastructure.handler.ApiError;
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
public class ClienteExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageService messageService;

    @ExceptionHandler({ ClienteJaExistenteCPFEmailException.class })
    public ResponseEntity<Object> handleEmailInvalidException(ClienteJaExistenteCPFEmailException exception, WebRequest request) {
        Object[] args = { exception.getMessage() };
        return handlerException(exception, HttpStatus.BAD_REQUEST, request, "cliente.cpf-existente", args);
    }

    protected ResponseEntity<Object> handlerException(Exception exception, HttpStatus status, WebRequest request, String key, Object[] args) {
        ApiError<List<String>> response = new ApiError<>(List.of((messageService.getMessage(key, args))));
        response.setStatusCode(status.value());
        return handleExceptionInternal(exception, response, new HttpHeaders(), status, request);
    }

}
