package br.ufsm.inf.model;

import javax.persistence.*;

/**
 * Created by Lucas on 29/10/2014.
 */
@Entity(name = "bloco_aprovado_disciplina")
public class BlocoAprovadoDisciplina {
    private Long id;
    private BlocoAprovado blocoAprovado;
    private Disciplina disciplina;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JoinColumn(name = "id_bloco_aprovado", referencedColumnName = "id")
    @OneToOne
    public BlocoAprovado getBlocoAprovado() {
        return blocoAprovado;
    }

    public void setBlocoAprovado(BlocoAprovado blocoAprovado) {
        this.blocoAprovado = blocoAprovado;
    }

    @JoinColumn(name = "id_disciplina", referencedColumnName = "id")
    @OneToOne
    public Disciplina getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }
}
