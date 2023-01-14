package br.com.github.biblioteca.cliente.model.dto;

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
public class ClienteResponseTO implements Serializable {

    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private EnderecoResponseTO endereco;
}
