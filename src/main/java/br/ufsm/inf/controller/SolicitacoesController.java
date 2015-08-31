package br.ufsm.inf.controller;

import br.ufsm.inf.model.Busca;
import br.ufsm.inf.model.Piec;
import br.ufsm.inf.model.Usuario;
import br.ufsm.inf.service.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by Lucas on 07/10/2014.
 */
@Controller
public class SolicitacoesController {
    private CadastroService cadastroService;

    @Autowired
    public void setCadastroService(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    @RequestMapping(value = "/solicitacoes.htm", method = RequestMethod.GET)
    public String mostraSolicitacoes(ModelMap model)  {
        List<Piec> solicitacoes = cadastroService.solicitacoes();
        if (solicitacoes != null && !solicitacoes.isEmpty()) {
            model.addAttribute("piecs", solicitacoes);
        } else {
            model.addAttribute("informacao", "Não existem novas solicitações");
        }
        return "solicitacoes";
    }
}
