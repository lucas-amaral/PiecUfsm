package br.ufsm.inf.controller;

import br.ufsm.inf.model.Disciplina;
import br.ufsm.inf.service.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Lucas on 29/09/2014.
 */
@Controller
@SessionAttributes("disciplina")
public class CadastroDisciplinaController {
    private CadastroService cadastroService;
    private CadastroDisciplinaValidator cadastroDisciplinaValidator;

    @Autowired
    public CadastroDisciplinaController(CadastroDisciplinaValidator cadastroDisciplinaValidator) {
        this.cadastroDisciplinaValidator = cadastroDisciplinaValidator;
    }

    @Autowired
    public void setCadastroService(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    @RequestMapping(value = "/cadastro-disciplina.htm", method = RequestMethod.GET)
    public String carregaFormulario(ModelMap model, @RequestParam(value = "idDisciplina", required = false) Long idDisciplina)  {
        Disciplina disciplina;
        if (idDisciplina != null) {
            disciplina = cadastroService.getDisciplina(idDisciplina);
            if (disciplina.getInstituicao() != null) {
                disciplina.setIdInstituicao(disciplina.getInstituicao().getId());
            }
        } else {
            disciplina = new Disciplina();
        }
        model.addAttribute("instituicoes", cadastroService.instituicoes());
        model.addAttribute(disciplina);
        return "cadastro-disciplina";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String salvar(@ModelAttribute("disciplina") Disciplina disciplina, Errors errors, ModelMap model) {
        cadastroDisciplinaValidator.validate(disciplina, errors, null);
        if (!errors.hasErrors()) {
            disciplina.setInstituicao(cadastroService.getInstituicao(disciplina.getIdInstituicao()));
            cadastroService.saveObject(disciplina);
            model.addAttribute("sucesso", "Disciplina alterada com sucesso");
        }
        model.addAttribute("instituicoes", cadastroService.instituicoes());
        return "cadastro-disciplina";
    }

    @RequestMapping("/cadastro-disciplina-remover.htm")
    public String remover(@ModelAttribute("disciplina") Disciplina disciplina, Errors errors, ModelMap model) {
        cadastroDisciplinaValidator.validate(disciplina, errors, "Remover");
        if (!errors.hasErrors()) {
            cadastroService.removeObject(Disciplina.class, disciplina.getId());
            model.addAttribute("instituicoes", cadastroService.instituicoes());
            model.addAttribute("sucesso", "Disciplina removida com sucesso");
            return "redirect:disciplinas.htm";
        }
        return "cadastro-disciplina";
    }
}
