package br.com.github.biblioteca.cliente.model.entity;

import br.com.github.biblioteca.shared.model.entity.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Builder
@ToString
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_cliente")
public class Cliente extends BaseEntity {

}
