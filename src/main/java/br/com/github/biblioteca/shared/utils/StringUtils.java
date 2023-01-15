package br.com.github.biblioteca.shared.utils;

public class StringUtils {

    public static final String REGEX = "[^\\d ]";

    public static String removeCaracteresEspeciaisCpf(String cpf) {
        return cpf.replaceAll(REGEX, "");
    }

    public static String removeCaracteresEspeciaisCep(String cep) {
        return cep.replaceAll(REGEX, "");
    }

    public static String removeCaracteresEspeciaisTelefone(String telefone) {
        var tel = telefone.replaceAll(REGEX, "");
        return tel.replace(" ", "");
    }
}
