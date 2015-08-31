package br.ufsm.inf.controller;

import br.ufsm.inf.model.Instituicao;
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
@SessionAttributes("instituicao")
public class CadastroInstituicaoController {
    private CadastroService cadastroService;
    private CadastroInstituicaoValidator cadastroInstituicaoValidator;

    @Autowired
    public CadastroInstituicaoController(CadastroInstituicaoValidator cadastroInstituicaoValidator) {
        this.cadastroInstituicaoValidator = cadastroInstituicaoValidator;
    }

    @Autowired
    public void setCadastroService(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    @RequestMapping(value = "/cadastro-instituicao.htm", method = RequestMethod.GET)
    public String carregaFormulario(ModelMap model, @RequestParam(value = "idInstituicao", required = false) Long idInstituicao)  {
        Instituicao instituicao;
            if (idInstituicao != null) {
            instituicao = cadastroService.getInstituicao(idInstituicao);
        } else {
            instituicao = new Instituicao();
        }
        model.addAttribute(instituicao);
        return "cadastro-instituicao";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String salvar(@ModelAttribute("instituicao") Instituicao instituicao, Errors errors, ModelMap model) {
        cadastroInstituicaoValidator.validate(instituicao, errors, null);
        if (!errors.hasErrors()) {
            cadastroService.saveObject(instituicao);
            model.addAttribute("sucesso", "Instituição alterada com sucesso");
        }
        return "cadastro-instituicao";
    }

    @RequestMapping("/cadastro-instituicao-remover.htm")
    public String remover(@ModelAttribute("instituicao") Instituicao instituicao, Errors errors, ModelMap model) {
        cadastroInstituicaoValidator.validate(instituicao, errors, "Remover");
        if (!errors.hasErrors()) {
            cadastroService.removeObject(Instituicao.class, instituicao.getId());
            model.addAttribute("sucesso", "Instituição removida com sucesso");
            return "redirect:instituicoes.htm";
        }
        return "cadastro-instituicao";
    }
}
