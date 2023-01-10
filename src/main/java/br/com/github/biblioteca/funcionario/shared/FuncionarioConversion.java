package br.com.github.biblioteca.funcionario.shared;

import br.com.github.biblioteca.funcionario.model.dto.FuncionarioResponseTO;
import br.com.github.biblioteca.funcionario.model.entity.Funcionario;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioConversion {

    private final ModelMapper mapper;

    public FuncionarioConversion(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public FuncionarioResponseTO convertToDTO(Funcionario entity) {
        return mapper.map(entity, FuncionarioResponseTO.class);
    }

    public Funcionario convertToEntity(FuncionarioResponseTO responseTO) {
        return  mapper.map(responseTO, Funcionario.class);
    }
}
