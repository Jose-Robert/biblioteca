package br.com.github.biblioteca.infrastructure.service.impl;

import br.com.github.biblioteca.infrastructure.service.ViaCepFeignClient;
import br.com.github.biblioteca.shared.model.dto.EnderecoResponseTO;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
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

    public EnderecoResponseTO obterEnderecoViaCep(String cep) {
        log.info("Realizando Consulta na API - VIACEP {}...", cep);
        String endereco = feignClient.getViaCep(cep);
        log.info("Informações Obtidas com Sucesso!\n {}", endereco);
        return gson.fromJson(endereco, EnderecoResponseTO.class);
    }
}
