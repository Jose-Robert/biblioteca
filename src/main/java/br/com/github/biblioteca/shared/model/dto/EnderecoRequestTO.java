package br.com.github.biblioteca.shared.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoRequestTO implements Serializable {

    private String cep;
    private String complemento;
}
