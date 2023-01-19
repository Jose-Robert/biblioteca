package br.com.github.biblioteca.funcionario.repository;

import br.com.github.biblioteca.funcionario.model.entity.Funcionario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID> {

    List<Funcionario> findByEnderecoCep(String cep);

    Page<Funcionario> findAll(Specification<Funcionario> specification, Pageable pageable);

    Optional<Funcionario> findByCpf(String cpf);

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
}
