package br.com.github.biblioteca.cliente.shared.adpter;

import br.com.github.biblioteca.cliente.model.dto.ClienteRequestTO;
import br.com.github.biblioteca.cliente.repository.ClienteRepository;
import br.com.github.biblioteca.cliente.shared.exception.ClienteJaExistenteCPFEmailException;
import br.com.github.biblioteca.infrastructure.exception.CampoObrigatorioException;
import br.com.github.biblioteca.infrastructure.exception.CampoTamanhoMaximoException;
import br.com.github.biblioteca.infrastructure.exception.CpfInvalidoException;
import br.com.github.biblioteca.infrastructure.exception.EmailInvalidException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import static br.com.github.biblioteca.shared.utils.StringUtils.*;
import static br.com.github.biblioteca.shared.utils.ValidatorUtils.verificarCpf;
import static br.com.github.biblioteca.shared.utils.ValidatorUtils.verificarEmail;

@Component
@RequiredArgsConstructor
public class ClienteValidateAdapter {

    private final ClienteRepository clienteRepository;

    public void validate(ClienteRequestTO request) {
        this.verificar(request);
        this.validaObrigatoriedadeDosCampos(request);
        this.validaDuplicidade(request);
    }

    private void verificar(ClienteRequestTO request) {
        var cpf = removeCaracteresEspeciaisCpf(request.getCpf());
        var email = request.getEmail();

        boolean isCpfValid = verificarCpf(cpf);
        if (!isCpfValid) {
            throw new CpfInvalidoException();
        }

        boolean isEmailValid = verificarEmail(email);
        if (!isEmailValid) {
            throw new EmailInvalidException();
        }
    }

    private void validaDuplicidade(ClienteRequestTO requestTO) {
        var cpf = removeCaracteresEspeciaisCpf(requestTO.getCpf());
        var email = requestTO.getEmail();

        boolean exists = clienteRepository.existsByCpfOrEmail(cpf, email);
        if (exists) {
            throw new ClienteJaExistenteCPFEmailException();
        }
    }

    private void validaObrigatoriedadeDosCampos(ClienteRequestTO requestTO) {
        var nome = requestTO.getNome();
        if (StringUtils.isBlank(nome)) {
            throw new CampoObrigatorioException("Nome");
        }

        var cpf = removeCaracteresEspeciaisCpf(requestTO.getCpf());
        if (StringUtils.isBlank(cpf)) {
            throw new CampoObrigatorioException("CPF");
        }

        if (cpf.length() != 11) {
            throw new CampoTamanhoMaximoException("CPF", "11");
        }

        var telefone = removeCaracteresEspeciaisTelefone(requestTO.getTelefone());
        if (StringUtils.isBlank(telefone)) {
            throw new CampoObrigatorioException("Telefone");
        }

        if (telefone.length() != 11) {
            throw new CampoTamanhoMaximoException("Telefone", "11");
        }

        var email = requestTO.getEmail();
        if (StringUtils.isBlank(email)) {
            throw new CampoObrigatorioException("Email");
        }

        var cep = removeCaracteresEspeciaisCep(requestTO.getEndereco().getCep());
        if (StringUtils.isBlank(cep)) {
            throw new CampoObrigatorioException("CEP");
        }

        if (cep.length() != 8) {
            throw new CampoTamanhoMaximoException("CEP", "8");
        }
    }
}
