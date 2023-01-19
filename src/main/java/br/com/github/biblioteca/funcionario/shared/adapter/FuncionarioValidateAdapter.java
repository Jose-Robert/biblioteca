package br.com.github.biblioteca.funcionario.shared.adapter;

import br.com.github.biblioteca.funcionario.model.dto.FuncionarioRequestTO;
import br.com.github.biblioteca.funcionario.repository.FuncionarioRepository;
import br.com.github.biblioteca.funcionario.shared.exception.FuncionarioJaExistenteCPFException;
import br.com.github.biblioteca.funcionario.shared.exception.FuncionarioJaExistenteEmailException;
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
public class FuncionarioValidateAdapter {

    private final FuncionarioRepository funcionarioRepository;

    public void validate(FuncionarioRequestTO request) {
        this.verificar(request);
        this.validaObrigatoriedadeDosCampos(request);
        this.validaDuplicidade(request);
    }

    private void verificar(FuncionarioRequestTO request) {
        var email = request.getEmail();
        var cpf = removeCaracteresEspeciaisCpf(request.getCpf());

        boolean isCpfValid = verificarCpf(cpf);
        if (!isCpfValid) {
            throw new CpfInvalidoException();
        }

        boolean isEmailValid = verificarEmail(email);
        if (!isEmailValid) {
            throw new EmailInvalidException();
        }
    }

    private void validaDuplicidade(FuncionarioRequestTO requestTO) {
        var cpf = removeCaracteresEspeciaisCpf(requestTO.getCpf());
        var email = requestTO.getEmail();

        boolean existsByCpf = funcionarioRepository.existsByCpf(cpf);
        if (existsByCpf) {
            throw new FuncionarioJaExistenteCPFException();
        }

        boolean existsByEmail = funcionarioRepository.existsByEmail(email);
        if (existsByEmail) {
            throw new FuncionarioJaExistenteEmailException();
        }
    }

    private void validaObrigatoriedadeDosCampos(FuncionarioRequestTO requestTO) {
        var cpf = removeCaracteresEspeciaisCpf(requestTO.getCpf());
        if (StringUtils.isBlank(cpf)) {
            throw new CampoObrigatorioException("CPF");
        }

        if (cpf.length() != 11) {
            throw new CampoTamanhoMaximoException("CPF", "11");
        }

        var nome = requestTO.getNome();
        if (StringUtils.isBlank(nome)) {
            throw new CampoObrigatorioException("Nome");
        }

        var email = requestTO.getEmail();
        if (StringUtils.isBlank(email)) {
            throw new CampoObrigatorioException("Email");
        }

        var telefone = removeCaracteresEspeciaisTelefone(requestTO.getTelefone());
        if (StringUtils.isBlank(telefone)) {
            throw new CampoObrigatorioException("Telefone");
        }

        if (telefone.length() != 11) {
            throw new CampoTamanhoMaximoException("Telefone", "11");
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