package br.com.github.biblioteca.funcionario.service;

import br.com.github.biblioteca.funcionario.model.dto.FuncionarioRequestTO;
import br.com.github.biblioteca.funcionario.model.dto.FuncionarioResponseTO;
import br.com.github.biblioteca.funcionario.model.entity.Funcionario;
import br.com.github.biblioteca.shared.model.dto.EnderecoResponseTO;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface FuncionarioService {

    FuncionarioResponseTO cadastrar(FuncionarioRequestTO requestTO);
    FuncionarioResponseTO atualizar(FuncionarioRequestTO requestTO, String cpf);
    FuncionarioResponseTO consultar(String cpf);
    void demitir(String cpf);
    List<FuncionarioResponseTO> listar(Specification<Funcionario> specification, int page, int size);
    EnderecoResponseTO consultarOcorrenciasFuncionarioPorCep(String cep);
}
