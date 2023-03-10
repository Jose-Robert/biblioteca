package br.com.github.biblioteca.shared.utils;

import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.List;

public class ValidatorUtils {

    public static boolean verificarCpf(String cpf) {
        CPFValidator cpfValidator = new CPFValidator();
        List<ValidationMessage> erros = cpfValidator.invalidMessagesFor(cpf);
        return erros.isEmpty();
    }

    public static boolean verificarEmail(String email) {
        return EmailValidator.getInstance().isValid(email);
    }
}
