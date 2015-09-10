package br.ufsm.inf.model;

import br.ufsm.inf.Teste;
import br.ufsm.inf.TestePropriedades;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Created by Lucas on 01/11/2014.
 */
@Entity(name = "instituicao")
@Teste(getCampo = "input.btn.btn-success", getIdentificador = TestePropriedades.IDENTIFICADOR_CSS, getUrl = "/cadastro-instituicao.htm"
    ,getIdentificadorAssert = TestePropriedades.IDENTIFICADOR_CSS, getCampoAssert = "h4", getValorEsperadoAssert = "Sucesso!")
public class Instituicao {
    private Long id;
    private String nome;
    private String sigla;
    private String estado;
    public static Collection<String> ufs = new LinkedHashSet<String>();

    static {
        ufs.add("Acre");
        ufs.add("Alagoas");
        ufs.add("Amapá");
        ufs.add("Amazonas");
        ufs.add("Bahia");
        ufs.add("Ceará");
        ufs.add("Distrito Federal");
        ufs.add("Espírito Santo");
        ufs.add("Goiás");
        ufs.add("Maranhão");
        ufs.add("Mato Grosso");
        ufs.add("Mato Grosso do Sul");
        ufs.add("Minas Gerais");
        ufs.add("Pará");
        ufs.add("Paraíba");
        ufs.add("Paraná");
        ufs.add("Pernambuco");
        ufs.add("Piauí");
        ufs.add("Rio de Janeiro");
        ufs.add("Rio Grande do Norte");
        ufs.add("Rio Grande do Sul");
        ufs.add("Rondônia");
        ufs.add("Roraima");
        ufs.add("Santa Catarina");
        ufs.add("São Paulo");
        ufs.add("Sergipe");
        ufs.add("Tocantins");
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Teste(getCampo = "nome", getValor = "Instituição teste")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Teste(getCampo = "sigla", getValor = "TEST789")
    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    @Teste(getCampo = "estado", getValor = "Piauí", isSelect = true)
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Transient
    public String getNomeCompleto() {
        return getSigla() + " - " + getNome();
    }

    @Transient
    public Collection<String> getUfs() {
        return ufs;
    }

    public void setUfs(Collection<String> ufs) {
        Instituicao.ufs = ufs;
    }
}
