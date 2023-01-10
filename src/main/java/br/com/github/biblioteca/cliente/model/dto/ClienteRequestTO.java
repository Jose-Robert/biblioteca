package br.com.github.biblioteca.cliente.model.dto;

import br.com.github.biblioteca.shared.model.dto.EnderecoRequestTO;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteRequestTO implements Serializable {

    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    private EnderecoRequestTO endereco;
}
