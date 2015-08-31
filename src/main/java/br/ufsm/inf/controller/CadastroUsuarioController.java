package br.ufsm.inf.controller;

import br.ufsm.inf.model.Piec;
import br.ufsm.inf.model.Usuario;
import br.ufsm.inf.service.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Lucas on 29/09/2014.
 */
@Controller
@RequestMapping("/cadastro-usuario.htm")
@SessionAttributes("usuario")
public class CadastroUsuarioController {
    private CadastroService cadastroService;
    private CadastroUsuarioValidator cadastroUsuarioValidator;

    @Autowired
    public CadastroUsuarioController(CadastroUsuarioValidator cadastroUsuarioValidator) {
        this.cadastroUsuarioValidator = cadastroUsuarioValidator;
    }

    @Autowired
    public void setCadastroService(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showForm(ModelMap model, @RequestParam(value = "idUsuario", required = false) String idUsuario)  {
        Usuario usuario;
        if (idUsuario != null) {
            usuario = cadastroService.getUsuario(Long.valueOf(idUsuario));
        } else {
            usuario = new Usuario();
        }
        if (usuario.getMatricula() == null && !usuario.getMembroColegiado()) {
            model.addAttribute("informacao", "Por favor, informe a matricula para prosseguir!");
        }
        model.addAttribute(usuario);
        return "cadastro-usuario";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String salvar(@ModelAttribute("usuario") Usuario usuario, Errors errors, ModelMap model, HttpServletRequest httpServletRequest) {
        cadastroUsuarioValidator.validate(usuario, errors);
        if (!errors.hasErrors()) {
            Usuario usuarioSecao = (Usuario) httpServletRequest.getSession().getAttribute("usuarioLogado");
            if (usuario.getPiec() == null) {
                Piec piec = new Piec();
                piec.setAluno(usuario);
                usuario.setPiec(piec);
                cadastroService.saveObject(piec);
            }
            cadastroService.saveObject(usuario);
            if (usuario.getId().equals(usuarioSecao.getId())) {
                atualizaUsuarioSessao(usuario, cadastroService, httpServletRequest);
            }
            model.addAttribute("sucesso", "Usuário alterado com sucesso");
        }
        return "cadastro-usuario";
    }

    public static void atualizaUsuarioSessao(Usuario usuario, CadastroService cadastroService, HttpServletRequest httpServletRequest) {
        cadastroService.atualizaObjeto(usuario);
        httpServletRequest.getSession().removeAttribute("usuarioLogado");
        httpServletRequest.getSession().setAttribute("usuarioLogado", usuario);
    }
}
