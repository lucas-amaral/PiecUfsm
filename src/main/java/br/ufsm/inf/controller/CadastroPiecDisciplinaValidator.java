package br.ufsm.inf.controller;

import br.ufsm.inf.model.PiecDisciplina;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;

import java.util.regex.Pattern;

/**
 * Created by Lucas on 18/11/2014.
 */
@Validated
public class CadastroPiecDisciplinaValidator {

    public void validate(PiecDisciplina piecDisciplina, Errors errors, String acao)  {
        if (piecDisciplina.getArquivoPlanoEnsino() != null && !piecDisciplina.getArquivoPlanoEnsino().isEmpty()) {
            String extensao = piecDisciplina.getArquivoPlanoEnsino().getOriginalFilename().substring(
                    piecDisciplina.getArquivoPlanoEnsino().getOriginalFilename().lastIndexOf(".")+1,
                    piecDisciplina.getArquivoPlanoEnsino().getOriginalFilename().length());
            if (!extensao.equals("pdf") && !extensao.equals("PDF")) {
                errors.reject("arquivoPlanoEnsino", "Formato do arquivo inválido, insira um arquivo no formato pdf.");
            }
        }
        if (piecDisciplina.getAprovada() == null || !piecDisciplina.getAprovada()) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "relevanciaIntegralizacao", "field.required", "Preencha o campo relevância da integralização.");
            if (piecDisciplina.getPlanoEnsino() == null && piecDisciplina.getArquivoPlanoEnsino().isEmpty() && acao == null) {
                errors.reject("arquivoPlanoEnsino", "Insira o arquivo do plano de ensino da disciplina desejada.");
            }
        }
    }
}
