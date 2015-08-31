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
        if (piecDisciplina.getAprovada() == null || !piecDisciplina.getAprovada()) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "relevanciaIntegralizacao", "field.required", "Preencha o campo relevância da integralização.");
            if (piecDisciplina.getPlanoEnsino() == null && piecDisciplina.getArquivoPlanoEnsino().isEmpty() && (acao == null || !acao.equals("RemoverArquivo"))) {
                errors.reject("arquivoPlanoEnsino", "Insira o arquivo do plano de ensino da disciplina desejada.");
            }
        }
        if (!Pattern.compile("(I|II)/[0-9][0-9][0-9][0-9]").matcher(piecDisciplina.getSemestreAnoRealizacao()).matches()) {
            errors.reject("semestreAnoRealizacao", "Formato incorreto para semestre e ano de realização, por favor, informe no seguinte formato II/1984 ou I/2001.");
        }
    }
}
