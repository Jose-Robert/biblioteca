package br.com.github.biblioteca.infrastructure.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "viacep", url = "${url.viacep}")
public interface ViaCepFeignClient {

    @GetMapping(value = "{cep}/json", produces = MediaType.APPLICATION_JSON_VALUE)
    String getViaCep(@PathVariable String cep);
}
