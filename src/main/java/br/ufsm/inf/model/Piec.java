package br.ufsm.inf.model;

import br.ufsm.inf.Teste;
import br.ufsm.inf.TestePropriedades;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.*;

/**
 * Created by Lucas on 27/09/2014.
 */
@Entity(name = "piec")
@Teste(getCampo = "salvar", click = true, fazerLogin = true, getLogin = "lamaral",
        getIdentificadorAssert = TestePropriedades.IDENTIFICADOR_CSS, getCampoAssert = "h4", getValorEsperadoAssert = "Sucesso!")
public class Piec {
    private Long id;
    private Usuario aluno;
    private String perfil;
    private String parecerRelator;
    private String parecerColegiado;
    private Date dataAprovacao;
    private Boolean solicitarAvalacao;
    private Set<PiecDisciplina> piecDisciplinas = new LinkedHashSet<PiecDisciplina>();
    private Set<Arquivo> documentos = new LinkedHashSet<Arquivo>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="id_piec")
    @OrderColumn(name = "id")
    public Set<Arquivo> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(Set<Arquivo> documentos) {
        this.documentos = documentos;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="id_piec")
    @OrderColumn(name = "disciplina.codigo")
    public Set<PiecDisciplina> getPiecDisciplinas() {
        return piecDisciplinas;
    }

    public void setPiecDisciplinas(Set<PiecDisciplina> piecDisciplinas) {
        this.piecDisciplinas = piecDisciplinas;
    }

    @Column(name="solicitar_avaliacao")
    public Boolean getSolicitarAvalacao() {
        return solicitarAvalacao != null && solicitarAvalacao;
    }

    public void setSolicitarAvalacao(Boolean solicitarAvalacao) {
        this.solicitarAvalacao = solicitarAvalacao;
    }

    @OneToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    public Usuario getAluno() {
        return aluno;
    }

    public void setAluno(Usuario aluno) {
        this.aluno = aluno;
    }

    @Column(name = "data_aprovacao")
    public Date getDataAprovacao() {
        return dataAprovacao;
    }

    public void setDataAprovacao(Date dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }

    @Column(name = "parecer_colegiado")
    public String getParecerColegiado() {
        return parecerColegiado;
    }

    public void setParecerColegiado(String parecerColegiado) {
        this.parecerColegiado = parecerColegiado;
    }

    @Column(name = "parecer_relator")
    public String getParecerRelator() {
        return parecerRelator;
    }

    public void setParecerRelator(String parecerRelator) {
        this.parecerRelator = parecerRelator;
    }

    @Column(columnDefinition = "TEXT")
    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Transient
    public String getCargaHorariaDiciplinasAprovadas() {
        Long cargaHorariaTotal = 0l;
        for (PiecDisciplina piecDisciplina : getPiecDisciplinas()) {
            if (piecDisciplina.getAprovada() != null && piecDisciplina.getAprovada() && piecDisciplina.getDisciplina().getCargaHoraria() != null) {
                cargaHorariaTotal += piecDisciplina.getDisciplina().getCargaHoraria();
            }
        }
        return cargaHorariaTotal + " horas";
    }

    @Transient
    public Long getCargaHoraria() {
        Long cargaHorariaTotal = 0l;
        for (PiecDisciplina piecDisciplina : getPiecDisciplinas()) {
            if ((piecDisciplina.getAprovada() == null || piecDisciplina.getAprovada()) && piecDisciplina.getDisciplina().getCargaHoraria() != null) {
                cargaHorariaTotal += piecDisciplina.getDisciplina().getCargaHoraria();
            }
        }
        return cargaHorariaTotal;
    }

    @Transient
    public Collection<Disciplina> getDisciplinas() {
        Collection<Disciplina> disciplinas = new LinkedList<Disciplina>();
        for (PiecDisciplina piecDisciplina : getPiecDisciplinas()) {
            disciplinas.add(piecDisciplina.getDisciplina());
        }
        return disciplinas;
    }

    @Transient
    public Boolean getPossuiDisciplinasExternas() {
        for (PiecDisciplina piecDisciplina : getPiecDisciplinas()) {
            if (!piecDisciplina.getDisciplina().getPreAprovada()) {
                return true;
            }
        }
        return false;
    }

    @Transient
    public Boolean getPossuiDisciplinaOutraInstituicao() {
        for (Disciplina d : getDisciplinas()) {
            if (!d.getInstituicao().getSigla().equals("UFSM")) {
                return true;
            }
        }
        return false;
    }

    //Propriedades command
    private PiecDisciplina piecDisciplinaAdicionar = new PiecDisciplina();
    private Disciplina novaDisciplina = new Disciplina();
    private Instituicao novaInstituicao = new Instituicao();
    private Long idDisciplinaAdicionar;
    private MultipartFile arquivoHistoricoEscolar;

    @Transient
    public MultipartFile getArquivoHistoricoEscolar() {
        return arquivoHistoricoEscolar;
    }

    public void setArquivoHistoricoEscolar(MultipartFile arquivoHistoricoEscolar) {
        this.arquivoHistoricoEscolar = arquivoHistoricoEscolar;
    }

    @Transient
    public PiecDisciplina getPiecDisciplinaAdicionar() {
        return piecDisciplinaAdicionar;
    }

    public void setPiecDisciplinaAdicionar(PiecDisciplina piecDisciplinaAdicionar) {
        this.piecDisciplinaAdicionar = piecDisciplinaAdicionar;
    }

    @Transient
    public Instituicao getNovaInstituicao() {
        return novaInstituicao;
    }

    public void setNovaInstituicao(Instituicao novaInstituicao) {
        this.novaInstituicao = novaInstituicao;
    }

    @Transient
    public Disciplina getNovaDisciplina() {
        return novaDisciplina;
    }

    public void setNovaDisciplina(Disciplina novaDisciplina) {
        this.novaDisciplina = novaDisciplina;
    }

    @Transient
    public Long getIdDisciplinaAdicionar() {
        return idDisciplinaAdicionar;
    }

    public void setIdDisciplinaAdicionar(Long idDisciplinaAdicionar) {
        this.idDisciplinaAdicionar = idDisciplinaAdicionar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Piec piec = (Piec) o;

        if (!aluno.equals(piec.aluno)) return false;
        if (!id.equals(piec.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + aluno.hashCode();
        return result;
    }
}
