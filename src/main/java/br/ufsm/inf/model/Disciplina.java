package br.ufsm.inf.model;

import br.ufsm.inf.Teste;
import br.ufsm.inf.TestePropriedades;

import javax.persistence.*;

/**
 * Created by Lucas on 27/09/2014.
 */
@Entity(name = "disciplina")
@Teste(getCampo = "salvar", getUrl = "/cadastro-disciplina.htm"
        ,getIdentificadorAssert = TestePropriedades.IDENTIFICADOR_CSS, getCampoAssert = "h4", getValorEsperadoAssert = "Sucesso!")
public class Disciplina  implements Comparable<Disciplina>{
    private Long id;
    private String codigo;
    private String nome;
    private Integer cargaHoraria;
    private Boolean preAprovada;
    private Boolean ativa;
    private Instituicao instituicao;
    private Long idInstituicao;

    @OneToOne
    @JoinColumn(name = "id_instituicao", referencedColumnName = "id")
    public Instituicao getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }

    @Teste(getCampo = "ativa1", click = true)
    public Boolean getAtiva() {
        return ativa == null || ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    @Column(name = "pre_aprovada")
    @Teste(getCampo = "preAprovada1", click = true)
    public Boolean getPreAprovada() {
        return preAprovada != null && preAprovada;
    }

    public void setPreAprovada(Boolean preAprovada) {
        this.preAprovada = preAprovada;
    }

    @Column(name = "carga_horaria")
    @Teste(getCampo = "cargaHoraria", getValor = "60", isSelect = true)
    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    @Teste(getCampo = "nome", getValor = "Disciplina teste")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false)
    @Teste(getCampo =  "codigo", getValor = "TEST123456")
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Transient
    @Teste(getCampo = "idInstituicao", getValor = "UFSM - Universidade Federal de Santa Maria", isSelect = true)
    public Long getIdInstituicao() {
        return idInstituicao;
    }

    public void setIdInstituicao(Long idInstituicao) {
        this.idInstituicao = idInstituicao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Disciplina that = (Disciplina) o;

        if (!codigo.equals(that.codigo)) return false;
        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + codigo.hashCode();
        return result;
    }

    @Override
    public int compareTo(Disciplina dis) {
        return this.nome.compareTo(dis.getNome());
    }
}
