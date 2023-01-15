package br.com.github.biblioteca.cliente.repository;

import br.com.github.biblioteca.cliente.model.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    Page<Cliente> findAll(Specification<Cliente> specification,Pageable pageable);
    Optional<Cliente> findByCpf(String cpf);

    boolean existsByCpfOrEmail(String cpf, String email);
}
