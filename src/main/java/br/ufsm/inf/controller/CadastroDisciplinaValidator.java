package br.ufsm.inf.controller;

import br.ufsm.inf.model.Disciplina;
import br.ufsm.inf.service.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;

/**
 * Created by Lucas on 18/11/2014.
 */
@Validated
public class CadastroDisciplinaValidator {
    private CadastroService cadastroService;

    @Autowired
    public void setCadastroService(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    public void validate(Disciplina disciplina, Errors errors, String acao)  {
        if (disciplina.getId() == null && cadastroService.existeDisciplina(disciplina.getCodigo())) {
            errors.reject("codigo", "Código já cadastrado em outra disciplina.");
        }
        if (acao != null && acao.equals("Remover")) {
            if (cadastroService.existeDisciplinaEmAlgumPlano(disciplina.getId())) {
                errors.reject("nome", "Disciplina já utilizada em algum plano.");
            }
            if (cadastroService.existeDisciplinaEmBlocoAprovado(disciplina.getId())) {
                errors.reject("nome", "Disciplina já utilizada em algum bloco de sugestão.");
            }
        }
    }
}
