package br.com.github.biblioteca.cliente.model.filter;

import br.com.github.biblioteca.cliente.model.entity.Cliente;
import br.com.github.biblioteca.infrastructure.persistence.SpecificationOperation;
import br.com.github.biblioteca.infrastructure.specification.SpecificationEntity;
import br.com.github.biblioteca.infrastructure.specification.SpecificationField;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@SpecificationEntity(value = Cliente.class)
public class ClienteFilterTO implements Serializable {

    @SpecificationField(property = "nome", operation = SpecificationOperation.LIKE_IGNORE_CASE)
    private String nome;
    @SpecificationField(property = "cpf")
    private String cpf;
    @SpecificationField(property = "telefone")
    private String telefone;
    @SpecificationField(property = "email")
    private String email;
    @SpecificationField(property = "endereco.cep")
    private String cep;
    @SpecificationField(property = "endereco.bairro", operation = SpecificationOperation.LIKE_IGNORE_CASE)
    private String bairro;
    @SpecificationField(property = "endereco.localidade", operation = SpecificationOperation.LIKE_IGNORE_CASE)
    private String localidade;
    @SpecificationField(property = "endereco.uf", operation = SpecificationOperation.LIKE_IGNORE_CASE)
    private String uf;
}
