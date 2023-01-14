package br.com.github.biblioteca.funcionario.service.impl;

import br.com.github.biblioteca.funcionario.model.dto.FuncionarioRequestTO;
import br.com.github.biblioteca.funcionario.model.dto.FuncionarioResponseTO;
import br.com.github.biblioteca.funcionario.model.entity.Funcionario;
import br.com.github.biblioteca.funcionario.repository.FuncionarioRepository;
import br.com.github.biblioteca.funcionario.service.FuncionarioService;
import br.com.github.biblioteca.funcionario.shared.FuncionarioConversion;
import br.com.github.biblioteca.infrastructure.service.impl.ViaCepService;
import br.com.github.biblioteca.shared.model.dto.EnderecoResponseTO;
import br.com.github.biblioteca.shared.model.entity.Endereco;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static br.com.github.biblioteca.shared.utils.GeradorAleatorioMatricula.geradorMatricula;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {

    private final ViaCepService viaCepService;
    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioConversion conversion;

    public FuncionarioServiceImpl(ViaCepService viaCepService, FuncionarioRepository funcionarioRepository, FuncionarioConversion conversion) {
        this.viaCepService = viaCepService;
        this.funcionarioRepository = funcionarioRepository;
        this.conversion = conversion;
    }

    @Override
    public FuncionarioResponseTO cadastrar(FuncionarioRequestTO requestTO) {
        EnderecoResponseTO enderecoResponseTO = this.getEndereco(requestTO.getEndereco().getCep());

        Funcionario funcionario = new Funcionario();
        funcionario.setCpf(requestTO.getCpf());
        funcionario.setEmail(requestTO.getEmail());
        funcionario.setNome(requestTO.getNome());
        funcionario.setTelefone(requestTO.getTelefone());
        funcionario.setMatricula(geradorMatricula());
        funcionario.setEndereco(Endereco.builder()
                .cep(enderecoResponseTO.getCep())
                .logradouro(enderecoResponseTO.getLogradouro())
                .bairro(enderecoResponseTO.getBairro())
                .localidade(enderecoResponseTO.getLocalidade())
                .complemento(requestTO.getEndereco().getComplemento())
                .uf(enderecoResponseTO.getUf())
                .build());
        return conversion.convertToDTO(funcionarioRepository.saveAndFlush(funcionario));
    }

    @Override
    public FuncionarioResponseTO atualizar(FuncionarioRequestTO requestTO, String cpf) {
        Funcionario funcionario = this.getFuncionarioByCpf(cpf);
        BeanUtils.copyProperties(requestTO, funcionario, "id");
        return conversion.convertToDTO(funcionarioRepository.saveAndFlush(funcionario));
    }

    @Override
    public FuncionarioResponseTO consultar(String cpf) {
        return conversion.convertToDTO(this.getFuncionarioByCpf(cpf));
    }

    @Override
    public void demitir(String cpf) {
        Funcionario funcionario = getFuncionarioByCpf(cpf);
        funcionarioRepository.deleteById(funcionario.getId());
    }

    @Override
    public List<FuncionarioResponseTO> listar(Specification<Funcionario> specification, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<Funcionario> funcionarios = funcionarioRepository.findAll(specification, pageable);
        return funcionarios.stream().map(conversion::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public EnderecoResponseTO consultarOcorrenciasFuncionarioPorCep(String cep) {
        List<Funcionario> funcionarios = funcionarioRepository.findByEnderecoCep(cep);
        Funcionario funcionario = funcionarios.stream()
                .filter(func -> func.getEndereco().getCep() != null)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not Found."));
       return this.getEndereco(funcionario.getEndereco().getCep());
    }

    private Funcionario getFuncionarioByCpf(String cpf) {
        return funcionarioRepository.findByCpf(cpf).orElseThrow(() -> new RuntimeException("Not Found."));
    }

    private EnderecoResponseTO getEndereco(String cep) {
        return viaCepService.obterEnderecoViaCep(cep);
    }

}
