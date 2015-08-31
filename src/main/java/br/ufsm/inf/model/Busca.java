package br.ufsm.inf.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lucas on 09/10/2014.
 */
public class Busca {
    public static final String FILTRO_NOME = "Nome";
    public static final String FILTRO_CODIGO = "Código";
    public static final String FILTRO_INSTITUICAO = "Instituição";
    public static final String FILTRO_DATA = "Data";

    private String filtro;
    private String expressao;
    private Boolean ativo;
    private Boolean preAprovadas;
    public static final Map<String, String> filtroDisciplina = new HashMap<String, String>();

    static {
        filtroDisciplina.put(FILTRO_NOME, "nome");
        filtroDisciplina.put(FILTRO_CODIGO, "codigo");
        filtroDisciplina.put(FILTRO_INSTITUICAO, "nome");
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public String getExpressao() {
        return expressao;
    }

    public void setExpressao(String expressao) {
        this.expressao = expressao;
    }

    public Boolean getAtivo() {
        return ativo == null || ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean getPreAprovadas() {
        return preAprovadas != null && preAprovadas;
    }

    public void setPreAprovadas(Boolean preAprovadas) {
        this.preAprovadas = preAprovadas;
    }
}
