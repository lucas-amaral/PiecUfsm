package br.ufsm.inf.controller;

import br.ufsm.inf.model.Busca;
import br.ufsm.inf.model.Disciplina;
import br.ufsm.inf.model.Instituicao;
import br.ufsm.inf.service.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucas on 07/10/2014.
 */
@Controller
@SessionAttributes("busca")
public class InstituicoesController {
    private CadastroService cadastroService;

    @Autowired
    public void setCadastroService(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    @RequestMapping(value = "/instituicoes.htm", method = RequestMethod.GET)
    public String carregaFormulario(ModelMap model)  {
        Busca busca = new Busca();
        List<Object> instituicoes = cadastroService.buscaAvancada("instituicao", busca);
        model.addAttribute(busca);
        model.addAttribute("instituicoes", instituicoes);
        return "instituicoes";
    }

    @RequestMapping("/instituicao-remover.htm")
    public String removerInstituicao(ModelMap model, HttpServletRequest httpServletRequest) {
        Long idInstituicaoRemover = Long.valueOf(httpServletRequest.getParameter("idInstituicaoRemover"));
        if (idInstituicaoRemover != null && !cadastroService.existeInstituicaoEmDisciplina(idInstituicaoRemover)) {
            cadastroService.removeObject(Instituicao.class, idInstituicaoRemover);
            model.addAttribute("sucesso", "Instituição removida com sucesso");
        } else {
            model.addAttribute("erro", "Instituição já utilizada em alguma disciplina.");
        }
        List<Object> instituicoes = cadastroService.buscaAvancada("instituicao", new Busca());
        model.addAttribute("instituicoes", instituicoes);
        return "instituicoes";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String busca(@ModelAttribute("busca") Busca busca, ModelMap model) {
        List<Object> instituicoes = cadastroService.buscaAvancada("instituicao", busca);
        model.addAttribute("instituicoes", instituicoes);
        if (instituicoes.isEmpty()) {
            model.addAttribute("atencao", "Não foram encontradas instituições com estes critérios");
        }
        return "instituicoes";
    }

}
