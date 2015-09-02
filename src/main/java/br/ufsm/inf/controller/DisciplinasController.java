package br.ufsm.inf.controller;

import br.ufsm.inf.model.Busca;
import br.ufsm.inf.model.Disciplina;
import br.ufsm.inf.service.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

/**
 * Created by Lucas on 07/10/2014.
 */
@Controller
@SessionAttributes("busca")
public class DisciplinasController {
    private CadastroService cadastroService;

    @Autowired
    public void setCadastroService(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    @RequestMapping(value = "/disciplinas.htm", method = RequestMethod.GET)
    public String carregaFormulario(ModelMap model)  {
        Busca busca = new Busca();
        model.addAttribute(busca);
        model.addAttribute("disciplinas", cadastroService.buscaAvancada("disciplina", busca));
        return "disciplinas";
    }

    @RequestMapping("/disciplina-remover.htm")
    public String removerDisciplina(ModelMap model, HttpServletRequest httpServletRequest) {
        Long idDisciplinaRemover = Long.valueOf(httpServletRequest.getParameter("idDisciplinaRemover"));
        if (idDisciplinaRemover != null && !cadastroService.existeDisciplinaEmAlgumPlano(idDisciplinaRemover)
                && !cadastroService.existeDisciplinaEmBlocoAprovado(idDisciplinaRemover)) {
            cadastroService.removeObject(Disciplina.class, idDisciplinaRemover);
            model.addAttribute("sucesso", "Disciplina removida com sucesso");
        } else {
            model.addAttribute("erro", "Disciplina já utilizada em algum plano.");
        }
        Busca busca = new Busca();
        model.addAttribute(busca);
        model.addAttribute("disciplinas", cadastroService.buscaAvancada("disciplina", busca));
        return "disciplinas";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String busca(@ModelAttribute("busca") Busca busca, ModelMap model) {
        List<Object> disciplinas = cadastroService.buscaAvancada("disciplina", busca);
        model.addAttribute("disciplinas", disciplinas);
        if (disciplinas.isEmpty()) {
            model.addAttribute("atencao", "Não foram encontradas disciplinas com estes critérios");
        }
        return "disciplinas";
    }

}
