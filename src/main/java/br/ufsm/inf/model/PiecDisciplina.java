package br.ufsm.inf.model;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.Date;

/**
 * Created by Lucas on 27/09/2014.
 */
@Entity(name = "piec_disciplina")
public class PiecDisciplina {
    private Long id;
    private String semestreAnoRealizacao;
    private String cursoOfertante;
    private String relevanciaIntegralizacao;
    private Boolean aprovada;
    private Date dataAprovacaoReprovacao;
    private Usuario usuarioAprovacao;
    private Disciplina disciplina;
    private Piec piec;
    private Arquivo planoEnsino;

    @JoinColumn(name = "id_arquivo", referencedColumnName = "id")
    @OneToOne(cascade = CascadeType.ALL)
    public Arquivo getPlanoEnsino() {
        return planoEnsino;
    }

    public void setPlanoEnsino(Arquivo planoEnsino) {
        this.planoEnsino = planoEnsino;
    }

    @JoinColumn(name = "id_piec", referencedColumnName = "id")
    @ManyToOne
    public Piec getPiec() {
        return piec;
    }

    public void setPiec(Piec piec) {
        this.piec = piec;
    }

    @JoinColumn(name = "id_disciplina", referencedColumnName = "id")
    @ManyToOne
    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    @JoinColumn(name = "id_usuario_aprovacao", referencedColumnName = "id")
    @ManyToOne
    public Usuario getUsuarioAprovacao() {
        return usuarioAprovacao;
    }

    public void setUsuarioAprovacao(Usuario usuarioAprovacao) {
        this.usuarioAprovacao = usuarioAprovacao;
    }

    @Column(name = "data_aprovacao_reprovacao")
    public Date getDataAprovacaoReprovacao() {
        return dataAprovacaoReprovacao;
    }

    public void setDataAprovacaoReprovacao(Date dataAprovacaoReprovacao) {
        this.dataAprovacaoReprovacao = dataAprovacaoReprovacao;
    }

    public Boolean getAprovada() {
        return aprovada;
    }

    public void setAprovada(Boolean aprovada) {
        this.aprovada = aprovada;
    }

    @Column(name = "relevancia_integralizacao", columnDefinition = "TEXT")
    public String getRelevanciaIntegralizacao() {
        return relevanciaIntegralizacao;
    }

    public void setRelevanciaIntegralizacao(String relevanciaIntegralizacao) {
        this.relevanciaIntegralizacao = relevanciaIntegralizacao;
    }

    @Column(name = "curso_ofertante")
    public String getCursoOfertante() {
        return cursoOfertante;
    }

    public void setCursoOfertante(String cursoOfertante) {
        this.cursoOfertante = cursoOfertante;
    }

    @Column(name = "semestre_ano_realizacao")
    public String getSemestreAnoRealizacao() {
        return semestreAnoRealizacao;
    }

    public void setSemestreAnoRealizacao(String semestreAnoRealizacao) {
        this.semestreAnoRealizacao = semestreAnoRealizacao;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private MultipartFile arquivoPlanoEnsino;

    @Transient
    public MultipartFile getArquivoPlanoEnsino() {
        return arquivoPlanoEnsino;
    }

    public void setArquivoPlanoEnsino(MultipartFile arquivoPlanoEnsino) {
        this.arquivoPlanoEnsino = arquivoPlanoEnsino;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PiecDisciplina that = (PiecDisciplina) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
