package br.com.github.biblioteca.cliente.service;

import br.com.github.biblioteca.cliente.model.dto.ClienteRequestTO;
import br.com.github.biblioteca.cliente.model.dto.ClienteResponseTO;
import br.com.github.biblioteca.cliente.model.entity.Cliente;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ClienteService {

    ClienteResponseTO cadastrar(ClienteRequestTO requestTO);
    ClienteResponseTO atualizar(ClienteRequestTO requestTO, Long id);
    ClienteResponseTO consultar(Long id);
    void excluir(Long id);
    List<ClienteResponseTO> listar(Specification<Cliente> specification, int page, int size);
}
