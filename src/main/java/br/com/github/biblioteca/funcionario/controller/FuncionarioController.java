package br.com.github.biblioteca.funcionario.controller;

import br.com.github.biblioteca.funcionario.model.dto.FuncionarioRequestTO;
import br.com.github.biblioteca.funcionario.model.dto.FuncionarioResponseTO;
import br.com.github.biblioteca.funcionario.model.entity.Funcionario;
import br.com.github.biblioteca.funcionario.model.filter.FuncionarioFilterTO;
import br.com.github.biblioteca.funcionario.service.FuncionarioService;
import br.com.github.biblioteca.infrastructure.persistence.SpecificationFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;
    private final SpecificationFactory<Funcionario> specificationFactory;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FuncionarioResponseTO> cadastrar(@RequestBody FuncionarioRequestTO requestTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioService.cadastrar(requestTO));
    }

    @PutMapping(value = "/{id}/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FuncionarioResponseTO> atualizar(@RequestBody FuncionarioRequestTO requestTO,
                                                       @PathVariable(name = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.atualizar(requestTO, id));
    }

    @GetMapping(value = "/{id}/find")
    public ResponseEntity<FuncionarioResponseTO> consultar(@PathVariable(name = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.consultar(id));
    }

    @GetMapping(value = "/find-all")
    public ResponseEntity<List<FuncionarioResponseTO>> listar(
            FuncionarioFilterTO filterTO,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Specification<Funcionario> specification = specificationFactory.create(filterTO);
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.listar(specification, page, size));
    }

    @DeleteMapping(value = "/{id}/delete")
    public void demitir(@PathVariable(name = "id") Long id) {
        funcionarioService.demitir(id);
    }

    @GetMapping(value = "/find/occurrences-employees/{cep}")
    public ResponseEntity<List<FuncionarioResponseTO>> consultarOcorrenciasFuncionariosPorCep(@PathVariable(name = "cep") String cep) {
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.consultarOcorrenciasFuncionarioPorCep(cep));
    }
}
