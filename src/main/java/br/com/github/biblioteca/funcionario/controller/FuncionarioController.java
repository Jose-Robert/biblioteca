package br.com.github.biblioteca.funcionario.controller;

import br.com.github.biblioteca.funcionario.model.dto.FuncionarioRequestTO;
import br.com.github.biblioteca.funcionario.model.dto.FuncionarioResponseTO;
import br.com.github.biblioteca.funcionario.model.entity.Funcionario;
import br.com.github.biblioteca.funcionario.model.filter.FuncionarioFilterTO;
import br.com.github.biblioteca.funcionario.service.FuncionarioService;
import br.com.github.biblioteca.infrastructure.persistence.SpecificationFactory;
import br.com.github.biblioteca.shared.model.dto.EnderecoResponseTO;
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

    @PutMapping(value = "/{cpf}/update", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FuncionarioResponseTO> atualizar(@RequestBody FuncionarioRequestTO requestTO,
                                                       @PathVariable(name = "cpf") String cpf) {
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.atualizar(requestTO, cpf));
    }

    @GetMapping(value = "/{cpf}/find")
    public ResponseEntity<FuncionarioResponseTO> consultar(@PathVariable(name = "cpf") String cpf) {
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.consultar(cpf));
    }

    @GetMapping(value = "/find-all")
    public ResponseEntity<List<FuncionarioResponseTO>> listar(
            FuncionarioFilterTO filterTO,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Specification<Funcionario> specification = specificationFactory.create(filterTO);
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.listar(specification, page, size));
    }

    @DeleteMapping(value = "/{cpf}/delete")
    public void demitir(@PathVariable(name = "cpf") String cpf) {
        funcionarioService.demitir(cpf);
    }

    @GetMapping(value = "/find/occurrences-employees/{cep}")
    public ResponseEntity<EnderecoResponseTO> consultarOcorrenciasFuncionariosPorCep(@PathVariable(name = "cep") String cep) {
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioService.consultarOcorrenciasFuncionarioPorCep(cep));
    }
}
