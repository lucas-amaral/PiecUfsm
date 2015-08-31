package br.ufsm.inf.model;

import javax.persistence.*;
import java.util.*;

/**
 * Created by Lucas on 29/10/2014.
 */
@Entity(name = "bloco_aprovado")
public class BlocoAprovado {
    private Long id;
    private Collection<BlocoAprovadoDisciplina> blocoAprovadoDisciplinas = new LinkedList<BlocoAprovadoDisciplina>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="id_bloco_aprovado")
    public Collection<BlocoAprovadoDisciplina> getBlocoAprovadoDisciplinas() {
        return blocoAprovadoDisciplinas;
    }

    public void setBlocoAprovadoDisciplinas(Collection<BlocoAprovadoDisciplina> blocoAprovadoDisciplinas) {
        this.blocoAprovadoDisciplinas = blocoAprovadoDisciplinas;
    }

    @Transient
    public Collection<Disciplina> getDisciplinas() {
        List<Disciplina> disciplinas = new ArrayList<Disciplina>();
        for (BlocoAprovadoDisciplina blocoAprovadoDisciplina :  getBlocoAprovadoDisciplinas()) {
            disciplinas.add(blocoAprovadoDisciplina.getDisciplina());
        }
        Collections.sort(disciplinas);
        return disciplinas;
    }
}
