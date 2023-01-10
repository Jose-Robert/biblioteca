package br.com.github.biblioteca.cliente.controller;

import br.com.github.biblioteca.cliente.model.dto.ClienteRequestTO;
import br.com.github.biblioteca.cliente.model.dto.ClienteResponseTO;
import br.com.github.biblioteca.cliente.model.entity.Cliente;
import br.com.github.biblioteca.cliente.model.filter.ClienteFilterTO;
import br.com.github.biblioteca.cliente.service.ClienteService;
import br.com.github.biblioteca.infrastructure.persistence.SpecificationFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;
    private final SpecificationFactory<Cliente> specificationFactory;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteResponseTO> cadastrar(@RequestBody ClienteRequestTO requestTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.cadastrar(requestTO));
    }

    @PutMapping(value = "/{id}/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteResponseTO> atualizar(@RequestBody ClienteRequestTO requestTO,
                                                       @PathVariable(name = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.atualizar(requestTO, id));
    }

    @GetMapping(value = "/{id}/find")
    public ResponseEntity<ClienteResponseTO> consultar(@PathVariable(name = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.consultar(id));
    }

    @GetMapping(value = "/find-all")
    public ResponseEntity<List<ClienteResponseTO>> listar(
            ClienteFilterTO filterTO,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Specification<Cliente> specification = specificationFactory.create(filterTO);
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.listar(specification, page, size));
    }

    @DeleteMapping(value = "/{id}/delete")
    public void excluir(@PathVariable(name = "id") Long id) {
        clienteService.excluir(id);
    }

}
