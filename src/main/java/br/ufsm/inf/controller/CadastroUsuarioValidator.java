package br.ufsm.inf.controller;

import br.ufsm.inf.model.Usuario;
import br.ufsm.inf.service.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;

/**
 * Created by Lucas on 18/11/2014.
 */
@Validated
public class CadastroUsuarioValidator {
    private CadastroService cadastroService;

    @Autowired
    public void setCadastroService(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    public void validate(Usuario usuario, Errors errors)  {
        if (usuario.getTipo().equals(Usuario.TIPO_ALUNO)) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "matricula", "field.required", "Preencha o campo matricula.");
            if (cadastroService.existeMatricula(usuario)) {
                errors.reject("codigo", "Matricula já utilizada em outro aluno, entre com contato com a coordenação do curso.");
            }
        }
    }
}
