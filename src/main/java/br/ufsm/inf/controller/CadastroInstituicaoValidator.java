package br.ufsm.inf.controller;

import br.ufsm.inf.model.Instituicao;
import br.ufsm.inf.service.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;

/**
 * Created by Lucas on 18/11/2014.
 */
@Validated
public class CadastroInstituicaoValidator {
    private CadastroService cadastroService;

    @Autowired
    public void setCadastroService(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    public void validate(Instituicao instituicao, Errors errors, String acao)  {
        if (instituicao.getId() == null && cadastroService.existeInstituicao(instituicao.getSigla())) {
            errors.reject("sigla", "Sigla já cadastrada em outra instituição.");
        }
        if (acao != null && acao.equals("Remover") && cadastroService.existeInstituicaoEmDisciplina(instituicao.getId())) {
            errors.reject("nome", "Instituição já utilizada em alguma disciplina.");
        }
    }
}
