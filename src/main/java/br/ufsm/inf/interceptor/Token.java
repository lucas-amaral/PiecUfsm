package br.ufsm.inf.interceptor;

/**
 * Created by Lucas on 20/03/2015.
 */
public class Token {
    public Boolean getGravaToken(String chave) {
        if (PalavraReservada.getPalavrasReservadas.contains(chave)) {
            System.out.println(chave + " - palavra reservada");
        } else if (Delimitador.getDelimitadores.contains(chave)) {
            System.out.println(chave + " - delimitador");
        } else if (chave.startsWith("[a-A][z-Z]")) {
            System.out.println(chave + " - identificador");
        } else {
            return false;
        }
        return true;
    }



}
