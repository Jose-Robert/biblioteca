package br.com.github.biblioteca.shared.utils;

import java.util.Random;

public class GeradorAleatorioMatricula {

    public static String geradorMatricula() {
        var gerador = new Random();
        String matricula = "";
        for (var i = 0; i < 5; i++) {
            String numero = String.valueOf(gerador.nextInt(9));
            matricula = matricula.concat(numero);
        }
        return matricula;
    }
}
