package br.com.github.biblioteca.funcionario.service;

import br.com.github.biblioteca.funcionario.model.dto.FuncionarioRequestTO;
import br.com.github.biblioteca.funcionario.model.dto.FuncionarioResponseTO;
import br.com.github.biblioteca.funcionario.model.entity.Funcionario;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface FuncionarioService {

    FuncionarioResponseTO cadastrar(FuncionarioRequestTO requestTO);
    FuncionarioResponseTO atualizar(FuncionarioRequestTO requestTO, Long id);
    FuncionarioResponseTO consultar(Long id);
    void demitir(Long id);
    List<FuncionarioResponseTO> listar(Specification<Funcionario> specification, int page, int size);

    List<FuncionarioResponseTO> consultarOcorrenciasFuncionarioPorCep(String cep);
}
