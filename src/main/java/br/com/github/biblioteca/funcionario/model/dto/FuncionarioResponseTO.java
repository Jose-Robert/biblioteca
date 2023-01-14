package br.com.github.biblioteca.funcionario.model.dto;

import br.com.github.biblioteca.shared.model.dto.EnderecoResponseTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FuncionarioResponseTO implements Serializable {

    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private String matricula;
    private EnderecoResponseTO endereco;
}
