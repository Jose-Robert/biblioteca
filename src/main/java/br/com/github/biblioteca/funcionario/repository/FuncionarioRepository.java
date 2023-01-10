package br.com.github.biblioteca.funcionario.repository;

import br.com.github.biblioteca.funcionario.model.entity.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    List<Funcionario> findByEnderecoBairroAndEnderecoLocalidade(String bairro, String localidade);

    Page<Funcionario> findAll(Specification<Funcionario> specification, Pageable pageable);
}
