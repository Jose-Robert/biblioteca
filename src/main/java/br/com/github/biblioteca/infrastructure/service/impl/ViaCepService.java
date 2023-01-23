package br.com.github.biblioteca.infrastructure.service.impl;

import br.com.github.biblioteca.infrastructure.exception.CepInvalidoException;
import br.com.github.biblioteca.infrastructure.service.ViaCepFeignClient;
import br.com.github.biblioteca.shared.model.dto.EnderecoResponseTO;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ViaCepService {

    private final ViaCepFeignClient feignClient;
    private final Gson gson;

    public ViaCepService(ViaCepFeignClient feignClient, Gson gson) {
        this.feignClient = feignClient;
        this.gson = gson;
    }

    @Cacheable(value = "getViaCep", key = "#cep")
    public EnderecoResponseTO obterEnderecoViaCep(String cep) {
        log.info("Realizando Consulta na API - VIACEP {}...", cep);
        var json = feignClient.getViaCep(cep);
        this.validateCep(json);
        log.info("Informações Obtidas com Sucesso!\n {}", json);
        return gson.fromJson(json, EnderecoResponseTO.class);
    }

    private void validateCep(String json) {
        log.info("Validando o CEP informado...");
        if (json.contains("erro")) {
            JSONObject object = new JSONObject(json);
            var erro = object.getBoolean("erro");
            if (erro) {
                log.error("CEP Inválido!!!");
                throw new CepInvalidoException();
            }
        }
    }
}
