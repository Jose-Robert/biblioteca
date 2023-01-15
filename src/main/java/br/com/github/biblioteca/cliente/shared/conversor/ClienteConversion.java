package br.com.github.biblioteca.cliente.shared.conversor;

import br.com.github.biblioteca.cliente.model.dto.ClienteResponseTO;
import br.com.github.biblioteca.cliente.model.entity.Cliente;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ClienteConversion {

    private final ModelMapper mapper;

    public ClienteConversion(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public ClienteResponseTO convertToDTO(Cliente entity) {
        return mapper.map(entity, ClienteResponseTO.class);
    }

    public Cliente convertToEntity(ClienteResponseTO responseTO) {
        return  mapper.map(responseTO, Cliente.class);
    }
}
