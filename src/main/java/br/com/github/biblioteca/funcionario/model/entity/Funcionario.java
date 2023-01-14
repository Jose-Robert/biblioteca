package br.com.github.biblioteca.funcionario.model.entity;

import br.com.github.biblioteca.shared.model.entity.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "tb_funcionario")
public class Funcionario extends BaseEntity {

    @Column(name = "matricula")
    private String matricula;

}
