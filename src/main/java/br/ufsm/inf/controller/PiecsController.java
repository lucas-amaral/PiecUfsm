package br.ufsm.inf.controller;

import br.ufsm.inf.model.*;
import br.ufsm.inf.service.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.*;

/**
 * Created by Lucas on 07/10/2014.
 */
@Controller
@SessionAttributes("busca")
public class PiecsController {
    private CadastroService cadastroService;

    @Autowired
    public void setCadastroService(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    @RequestMapping(value = "/piecs.htm", method = RequestMethod.GET)
    public String mostraPiecs(ModelMap model)  {
        Busca busca = new Busca();
        List<Object> piecs = cadastroService.buscaAvancada("piec", busca);
        model.addAttribute(busca);
        model.addAttribute("piecs", piecs);
        return "piecs";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String busca(@ModelAttribute("busca") Busca busca, ModelMap model) {
        Collection<Object> piecs = new LinkedHashSet<Object>();
        if (busca.getFiltro().equals("data_aprovacao")) {
            piecs = cadastroService.buscaAvancada("piec", busca);
        } else if (busca.getFiltro().equals("nome") || busca.getFiltro().equals("matricula")) {
            List<Object> alunos = cadastroService.buscaAvancada("usuario", busca);
            for (Object aluno : alunos) {
                piecs.add(((Usuario) aluno).getPiec());
            }
        } else if (busca.getFiltro().equals("disciplina.nome") || busca.getFiltro().equals("codigo")) {
            if (busca.getFiltro().equals("disciplina.nome")) {
                busca.setFiltro("nome");
            }
            List<Object> piecsExistentes = cadastroService.buscaAvancada("piec", new Busca());
            List<Object> disciplinas = cadastroService.buscaAvancada("disciplina", busca);
            for (Object disciplina : disciplinas) {
                for (Object piec : piecsExistentes) {
                     if (((Piec) piec).getDisciplinas().contains(disciplina)) {
                        piecs.add(piec);
                    }
                }
            }
            if (busca.getFiltro().equals("nome")) {
                busca.setFiltro("disciplina.nome");
            }
        }
        model.addAttribute("piecs", piecs);
        if (piecs.isEmpty()) {
            model.addAttribute("atencao", "Não foram encontrados planos de estudos complementares com estes critérios");
        }
        return "piecs";
    }

}
