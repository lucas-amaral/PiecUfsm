package br.ufsm.inf.model;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by Lucas on 31/10/2014.
 */
public class ArquivoTemporario {
    private Long id;
    private String nomeArquivo;
    private String contentType;
    private Long tamanho;
    private byte[] arquivo;
    private Date dataCadastro;
    private Usuario usuarioCadastro;
    private String descricao;
    private Boolean compactado;

    public Boolean getCompactado() {
        return compactado != null && compactado;
    }

    public void setCompactado(Boolean compactado) {
        this.compactado = compactado;
    }

    public ArquivoTemporario(Arquivo arquivoPersistente) {
        this.id = arquivoPersistente.getId();
        this.nomeArquivo = arquivoPersistente.getNome();
        this.contentType = arquivoPersistente.getContentType();
        this.tamanho = arquivoPersistente.getTamanho();
        this.dataCadastro = arquivoPersistente.getDataCadastro();
        try {
            this.arquivo = arquivoPersistente.getArquivo().getBytes(1, (int) arquivoPersistente.getArquivo().length());
        } catch (SQLException e) { e.printStackTrace(); }
        this.setCompactado(arquivoPersistente.getCompactado());
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Usuario getUsuarioCadastro() {
        return usuarioCadastro;
    }

    public void setUsuarioCadastro(Usuario usuarioCadastro) {
        this.usuarioCadastro = usuarioCadastro;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Long getTamanho() {
        return tamanho;
    }

    public void setTamanho(Long tamanho) {
        this.tamanho = tamanho;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }
}
