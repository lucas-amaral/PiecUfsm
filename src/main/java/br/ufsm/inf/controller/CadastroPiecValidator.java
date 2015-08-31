package br.ufsm.inf.controller;

import br.ufsm.inf.model.*;
import br.ufsm.inf.service.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Lucas on 30/09/2014.
 */
@Validated
public class CadastroPiecValidator {
    private CadastroService cadastroService;

    @Autowired
    public void setCadastroService(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    public void validate(@ModelAttribute("piec") Piec piec, Errors errors, String acao)  {
        if (acao != null && acao.equals("AdicionarPiecDisciplina")) {
            Disciplina disciplina = cadastroService.getDisciplina(piec.getIdDisciplinaAdicionar());
            if (disciplina != null && cadastroService.existeDisciplinaPlano(piec.getId(), disciplina.getId())) {
                errors.reject("piecDisciplinaAdicionar", "Disciplina já inserida no plano.");
            } else if (disciplina == null){
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "novaDisciplina.codigo", "field.required", "Preencha o campo código.");
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "novaDisciplina.nome", "field.required", "Preencha o campo nome.");
                if (cadastroService.existeDisciplina(piec.getNovaDisciplina().getCodigo())) {
                    errors.reject("novaDisciplina.codigo", "Código já cadastrado em outra disciplina. Por favor, caso a mesma não conste na lista de possibilidades, entre em contato com a coordenação do curso.");
                }
                if (piec.getNovaInstituicao().getSigla() != null && cadastroService.existeInstituicao(piec.getNovaInstituicao().getSigla())) {
                    errors.reject("sigla", "Sigla já cadastrada em outra instituição.");
                }
            }
            if (piec.getPiecDisciplinaAdicionar().getDisciplina() != null && !piec.getPiecDisciplinaAdicionar().getDisciplina().getPreAprovada()
                    || piec.getNovaDisciplina().getCodigo() != null && !piec.getNovaDisciplina().getCodigo().equals("")) {
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "piecDisciplinaAdicionar.relevanciaIntegralizacao", "field.required", "Preencha o campo relevância da integralização.");
                if (piec.getPiecDisciplinaAdicionar().getArquivoPlanoEnsino().isEmpty()) {
                    errors.reject("piecDisciplinaAdicionar.arquivoPlanoEnsino", "Insira o arquivo do plano de ensino da disciplina desejada.");
                }
            }
        } else if (acao != null && acao.equals("Aprovar")) {
            if (piec.getCargaHoraria() < 600) {
                errors.reject("novaDisciplina", "Carga horária inferior a necessária para aprovação.");
            }
        } else if (acao != null && acao.equals("AdicionarArquivo")) {
            if (piec.getArquivoHistoricoEscolar().isEmpty()) {
                errors.reject("arquivoHistoricoEscolar", "Selecione o documento que deseja vincular ao piec.");
            }
        }
    }
}
