package br.ufsm.inf.model;

import javax.persistence.*;

/**
 * Created by Lucas on 27/09/2014.
 */
@Entity(name = "usuario")
public class Usuario {
    public static final String TIPO_ALUNO = "Aluno";
    public static final String TIPO_COLEGIADO = "Colegiado";

    private Long id;
    private String nome;
    private String login;
    private Boolean ativo;
    private Integer matricula;
    private String tipo = TIPO_ALUNO;
    private Piec piec;
    private String senha;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(unique = true)
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Basic
    public Boolean getAtivo() {
        return ativo == null || ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Basic
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(unique = true)
    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    @Basic
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @OneToOne
    @JoinColumn(name = "id_piec", referencedColumnName = "id")
    public Piec getPiec() {
        return piec;
    }

    public void setPiec(Piec piec) {
        this.piec = piec;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Usuario usuario = (Usuario) o;

        if (!id.equals(usuario.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Transient
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Transient
    public Boolean getMembroColegiado() {
        return getTipo().equals(TIPO_COLEGIADO);
    }


}

