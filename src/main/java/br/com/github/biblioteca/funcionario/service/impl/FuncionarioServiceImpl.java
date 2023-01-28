package br.com.github.biblioteca.funcionario.service.impl;

import br.com.github.biblioteca.funcionario.model.dto.FuncionarioRequestTO;
import br.com.github.biblioteca.funcionario.model.dto.FuncionarioResponseTO;
import br.com.github.biblioteca.funcionario.model.entity.Funcionario;
import br.com.github.biblioteca.funcionario.repository.FuncionarioRepository;
import br.com.github.biblioteca.funcionario.service.FuncionarioService;
import br.com.github.biblioteca.funcionario.shared.adapter.FuncionarioValidateAdapter;
import br.com.github.biblioteca.funcionario.shared.conversor.FuncionarioConversion;
import br.com.github.biblioteca.infrastructure.exception.RecursoNaoEncontradoException;
import br.com.github.biblioteca.infrastructure.service.impl.ViaCepService;
import br.com.github.biblioteca.shared.model.dto.EnderecoResponseTO;
import br.com.github.biblioteca.shared.model.entity.Endereco;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.github.biblioteca.shared.utils.GeradorAleatorioMatricula.geradorMatricula;
import static br.com.github.biblioteca.shared.utils.StringUtils.*;

@Service
@RequiredArgsConstructor
public class FuncionarioServiceImpl implements FuncionarioService {

    private final ViaCepService viaCepService;
    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioConversion conversion;
    private final FuncionarioValidateAdapter validateAdapter;

    @Override
    public FuncionarioResponseTO cadastrar(FuncionarioRequestTO requestTO) {
        validateAdapter.validate(requestTO);
        EnderecoResponseTO enderecoResponseTO = this.getEndereco(requestTO.getEndereco().getCep());
        Funcionario funcionario = this.funcionarioBuilder(requestTO, enderecoResponseTO);
        return conversion.convertToDTO(funcionarioRepository.saveAndFlush(funcionario));
    }

    @Override
    public FuncionarioResponseTO atualizar(FuncionarioRequestTO requestTO, String cpf) {
        Funcionario funcionario = this.getFuncionarioByCpf(cpf);
        BeanUtils.copyProperties(requestTO, funcionario, "id");
        return conversion.convertToDTO(funcionarioRepository.saveAndFlush(funcionario));
    }

    @Override
    @Cacheable(value = "getFuncionario", key = "#cpf")
    public FuncionarioResponseTO consultar(String cpf) {
        return conversion.convertToDTO(this.getFuncionarioByCpf(cpf));
    }

    @Override
    public void demitir(String cpf) {
        Funcionario funcionario = getFuncionarioByCpf(cpf);
        funcionarioRepository.deleteById(funcionario.getId());
    }

    @Override
    @CacheEvict(value = "getAllFuncionario", allEntries = true)
    public List<FuncionarioResponseTO> listar(Specification<Funcionario> specification, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        Page<Funcionario> funcionarios = funcionarioRepository.findAll(specification, pageable);
        return funcionarios.stream().map(conversion::convertToDTO).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "getEndereco", key = "#cep")
    public EnderecoResponseTO consultarOcorrenciasFuncionarioPorCep(String cep) {
        List<Funcionario> funcionarios = funcionarioRepository.findByEnderecoCep(cep);
        Funcionario funcionario = funcionarios.stream()
                .filter(func -> func.getEndereco().getCep() != null)
                .findFirst()
                .orElseThrow(RecursoNaoEncontradoException::new);
       return this.getEndereco(funcionario.getEndereco().getCep());
    }

    private EnderecoResponseTO getEndereco(String cep) {
        return viaCepService.obterEnderecoViaCep(cep);
    }

    private Funcionario getFuncionarioByCpf(String cpf) {
        return funcionarioRepository.findByCpf(removeCaracteresEspeciaisCpf(cpf)).orElseThrow(RecursoNaoEncontradoException::new);
    }

    private Funcionario funcionarioBuilder(FuncionarioRequestTO requestTO, EnderecoResponseTO enderecoResponseTO) {
        Funcionario funcionario = new Funcionario();
        funcionario.setCpf(removeCaracteresEspeciaisCpf(requestTO.getCpf()));
        funcionario.setEmail(requestTO.getEmail());
        funcionario.setNome(requestTO.getNome());
        funcionario.setTelefone(removeCaracteresEspeciaisTelefone(requestTO.getTelefone()));
        funcionario.setMatricula(geradorMatricula());
        funcionario.setEndereco(Endereco.builder()
                .cep(removeCaracteresEspeciaisCep(enderecoResponseTO.getCep()))
                .logradouro(enderecoResponseTO.getLogradouro())
                .bairro(enderecoResponseTO.getBairro())
                .localidade(enderecoResponseTO.getLocalidade())
                .complemento(requestTO.getEndereco().getComplemento())
                .uf(enderecoResponseTO.getUf())
                .build());
        return funcionario;
    }

}
