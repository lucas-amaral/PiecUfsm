package br.ufsm.inf.controller;

import br.ufsm.inf.model.Busca;
import br.ufsm.inf.model.Disciplina;
import br.ufsm.inf.model.Usuario;
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
public class UsuariosController {
    private CadastroService cadastroService;

    @Autowired
    public void setCadastroService(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    @RequestMapping(value = "/usuarios.htm", method = RequestMethod.GET)
    public String mostraDisciplina(ModelMap model)  {
        Busca busca = new Busca();
        Collection<Usuario> usuarios = cadastroService.usuarios();
        model.addAttribute(busca);
        model.addAttribute("usuarios", usuarios);
        return "usuarios";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String busca(@ModelAttribute("busca") Busca busca, ModelMap model) {
        List<Object> usuarios = cadastroService.buscaAvancada("usuario", busca);
        model.addAttribute("usuarios", usuarios);
        if (usuarios.isEmpty()) {
            model.addAttribute("atencao", "Não foram encontrados usuários com estes critérios");
        }
        return "usuarios";
    }

}
