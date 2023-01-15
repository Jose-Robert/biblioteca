package br.com.github.biblioteca.cliente.service.impl;

import br.com.github.biblioteca.cliente.model.dto.ClienteRequestTO;
import br.com.github.biblioteca.cliente.model.dto.ClienteResponseTO;
import br.com.github.biblioteca.cliente.model.entity.Cliente;
import br.com.github.biblioteca.cliente.repository.ClienteRepository;
import br.com.github.biblioteca.cliente.service.ClienteService;
import br.com.github.biblioteca.cliente.shared.adpter.ClienteValidateAdapter;
import br.com.github.biblioteca.cliente.shared.conversor.ClienteConversion;
import br.com.github.biblioteca.infrastructure.exception.RecursoNaoEncontradoException;
import br.com.github.biblioteca.infrastructure.service.impl.ViaCepService;
import br.com.github.biblioteca.shared.model.dto.EnderecoResponseTO;
import br.com.github.biblioteca.shared.model.entity.Endereco;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static br.com.github.biblioteca.shared.utils.StringUtils.*;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteConversion conversion;
    private final ViaCepService viaCepService;
    private final ClienteValidateAdapter validator;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public ClienteResponseTO cadastrar(ClienteRequestTO requestTO) {
        validator.validate(requestTO);
        EnderecoResponseTO enderecoResponseTO = viaCepService.obterEnderecoViaCep(requestTO.getEndereco().getCep());

        Cliente cliente = new Cliente();
        cliente.setNome(requestTO.getNome());
        cliente.setEmail(requestTO.getEmail());
        cliente.setCpf(removeCaracteresEspeciaisCpf(requestTO.getCpf()));
        cliente.setTelefone(removeCaracteresEspeciaisTelefone(requestTO.getTelefone()));
        cliente.setEndereco(Endereco.builder()
                .cep(removeCaracteresEspeciaisCep(enderecoResponseTO.getCep()))
                .logradouro(enderecoResponseTO.getLogradouro())
                .bairro(enderecoResponseTO.getBairro())
                .localidade(enderecoResponseTO.getLocalidade())
                .complemento(requestTO.getEndereco().getComplemento())
                .uf(enderecoResponseTO.getUf())
                .build());
        return conversion.convertToDTO(clienteRepository.save(cliente));
    }

    @Override
    public ClienteResponseTO atualizar(ClienteRequestTO requestTO, String cpf) {
        Cliente cliente = this.getClienteByCpf(cpf);
        BeanUtils.copyProperties(requestTO, cliente, "id");
        return conversion.convertToDTO(clienteRepository.saveAndFlush(cliente));
    }

    @Override
    public ClienteResponseTO consultar(String cpf) {
        return conversion.convertToDTO(this.getClienteByCpf(cpf));
    }

    @Override
    public void excluir(String cpf) {
        Cliente cliente = this.getClienteByCpf(cpf);
        clienteRepository.deleteById(cliente.getId());
    }

    @Override
    public List<ClienteResponseTO> listar(Specification<Cliente> specification, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "nome"));
        Page<Cliente> clientes = clienteRepository.findAll(specification, pageable);
        return clientes.stream().map(conversion::convertToDTO).collect(Collectors.toList());
    }

    private Cliente getClienteByCpf(String cpf) {
        return clienteRepository.findByCpf(cpf).orElseThrow(RecursoNaoEncontradoException::new);
    }
}
