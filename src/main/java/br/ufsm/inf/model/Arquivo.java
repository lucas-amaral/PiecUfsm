package br.ufsm.inf.model;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;

/**
 * Created by Lucas on 27/09/2014.
 */
@Entity(name = "arquivo")
public class Arquivo {
    public final static String TIPO_PLANO_ENSINO = "Plano ensino";
    public final static String TIPO_HISTORICO_ESCOLAR = "Histórico escolar";

    private Long id;
    private String nome;
    private String contentType;
    private Long tamanho;
    private Blob arquivo;
    private String tipo;
    private Boolean compactado;
    private Date dataCadastro;
    private Piec piec;

    @ManyToOne
    @JoinColumn(name = "id_piec", referencedColumnName = "id")
    public Piec getPiec() {
        return piec;
    }

    public void setPiec(Piec piec) {
        this.piec = piec;
    }

    @Column(name = "tipo")
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Column(name = "arquivo")
    public Blob getArquivo() {
        return arquivo;
    }

    public void setArquivo(Blob arquivo) {
        this.arquivo = arquivo;
    }

    @Column(name = "tamanho")
    public Long getTamanho() {
        return tamanho;
    }

    public void setTamanho(Long tamanho) {
        this.tamanho = tamanho;
    }

    @Column(name = "content_type")
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Basic
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

    public Boolean getCompactado() {
        return compactado;
    }

    public void setCompactado(Boolean compactado) {
        this.compactado = compactado;
    }

    @Column(name = "data_cadastro")
    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Arquivo arquivo = (Arquivo) o;

        if (!id.equals(arquivo.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}

