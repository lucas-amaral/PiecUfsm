package br.ufsm.inf.controller;

import br.ufsm.inf.model.*;
import br.ufsm.inf.service.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by Lucas on 31/10/2014.
 */
@Controller
public class BlocosController {
    private CadastroService cadastroService;

    @Autowired
    public void setCadastroService(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    @RequestMapping(value = "/blocos.htm", method = RequestMethod.GET)
    public String mostraDisciplina(ModelMap model)  {
        Busca busca = new Busca();
        model.addAttribute(busca);
        model.addAttribute("blocosAprovados", cadastroService.blocosAprovados());
        return "blocos";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String sugestoes(@ModelAttribute("busca") Busca busca, ModelMap model, HttpServletRequest httpServletRequest) {
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuarioLogado");
        List<BlocoAprovado> blocos;
        if (busca.getFiltro().equals("Somente")) {
            blocos = cadastroService.possiveisBlocosAprovados(usuario.getPiec().getDisciplinas());
        } else {
            blocos = cadastroService.blocosAprovados();
        }
        if (blocos.isEmpty()) {
            model.addAttribute("atencao", "Não foram encontradas opções que contenham todas as disciplinas existentes em seu PIEC");
        }
        model.addAttribute("blocosAprovados", blocos);
        return "blocos";
    }

    @RequestMapping(value = "/inserir-bloco-piec.htm")
    public String inserirBlocoPiec(HttpServletRequest httpServletRequest) {
        String idBlocoAprovado = httpServletRequest.getParameter("idBlocoAprovado");
        Usuario usuario = cadastroService.getUsuario(Long.valueOf(httpServletRequest.getParameter("idUsuario")));
        BlocoAprovado blocoAprovado = cadastroService.getBlocoAprovado(Long.valueOf(idBlocoAprovado));
        for (Disciplina disciplina : blocoAprovado.getDisciplinas()) {
            if (!usuario.getPiec().getDisciplinas().contains(disciplina)) {
                PiecDisciplina piecDisciplina = new PiecDisciplina();
                piecDisciplina.setDisciplina(disciplina);
                piecDisciplina.setPiec(usuario.getPiec());
                piecDisciplina.setAprovada(true);
                piecDisciplina.setDataAprovacaoReprovacao(new Date());
                cadastroService.saveObject(piecDisciplina);
                CadastroUsuarioController.atualizaUsuarioSessao(usuario, cadastroService, httpServletRequest);
            }
        }
        for (PiecDisciplina piecDisciplina : usuario.getPiec().getPiecDisciplinas()) {
            piecDisciplina.setAprovada(true);
            piecDisciplina.setDataAprovacaoReprovacao(new Date());
            cadastroService.atualizaObjeto(piecDisciplina);
        }
        return "redirect:cadastro-piec.htm?idPiec=" + usuario.getPiec().getId();
    }

    @RequestMapping(value = "/remover-bloco.htm")
    public String removerBlocoPiec(@ModelAttribute("busca") Busca busca, ModelMap model, HttpServletRequest httpServletRequest) {
        String idBlocoAprovado = httpServletRequest.getParameter("idBlocoAprovado");
        Usuario usuario = cadastroService.getUsuario(Long.valueOf(httpServletRequest.getParameter("idUsuario")));
        BlocoAprovado blocoAprovado = cadastroService.getBlocoAprovado(Long.valueOf(idBlocoAprovado));
        cadastroService.removeObject(BlocoAprovado.class, blocoAprovado.getId());
        model.addAttribute("sucesso", "Opção removida com sucesso");
        model.addAttribute("blocosAprovados", cadastroService.blocosAprovados());
        return "blocos";
    }
}
