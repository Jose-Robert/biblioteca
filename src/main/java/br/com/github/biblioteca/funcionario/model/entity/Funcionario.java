package br.com.github.biblioteca.funcionario.model.entity;

import br.com.github.biblioteca.shared.model.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "tb_funcionario")
public class Funcionario extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "matricula")
    private String matricula;

}
