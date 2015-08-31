package br.ufsm.inf.controller;

import br.ufsm.inf.model.*;
import br.ufsm.inf.service.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Lucas on 30/09/2014.
 */
@Controller
@SessionAttributes("piec")
public class CadastroPiecController {
    private CadastroService cadastroService;
    private CadastroPiecValidator cadastroPiecValidator;

    @Autowired
    public CadastroPiecController(CadastroPiecValidator cadastroPiecValidator) {
        this.cadastroPiecValidator = cadastroPiecValidator;
    }

    @Autowired
    public void setCadastroService(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    @RequestMapping(value = "/cadastro-piec.htm", method = RequestMethod.GET)
    public String showForm(ModelMap model, @RequestParam(value = "idPiec" , required = false) String idPiec, HttpServletRequest httpServletRequest)  {
        Usuario usuarioSecao = (Usuario) httpServletRequest.getSession().getAttribute("usuarioLogado");
        Piec piec;
        if (idPiec != null) {
            piec = cadastroService.getPiec(Long.valueOf(idPiec));
            if (usuarioSecao.getTipo().equals(Usuario.TIPO_COLEGIADO) || piec.getId().equals(usuarioSecao.getPiec().getId())) {
                model.addAttribute(piec);
                model.addAttribute("instituicoes", cadastroService.instituicoes());
                model.addAttribute("disciplinasAtivas", cadastroService.disciplinasAtivas());
                return "cadastro-piec";
            }
        }
        return "redirect:login.htm";
    }

    @RequestMapping(value = "/cadastro-piec.htm", method = RequestMethod.POST)
    public String salvar(@ModelAttribute("piec") Piec piec, Errors errors, ModelMap model,
                         @RequestParam(value = "acao", required = false) String acao, HttpServletRequest httpServletRequest) {
        cadastroPiecValidator.validate(piec, errors, acao);
        if (!errors.hasErrors()) {
            if (acao != null && acao.equals("AdicionarPiecDisciplina")) {
                adicionarDisciplina(piec, httpServletRequest);
                piec.setNovaDisciplina(new Disciplina());
                piec.setNovaInstituicao(new Instituicao());
                piec.setPiecDisciplinaAdicionar(new PiecDisciplina());
            } else if (acao != null && acao.equals("AdicionarArquivo")) {
                Arquivo arquivo = cadastroService.carregarArquivo(piec.getArquivoHistoricoEscolar(), null, Arquivo.TIPO_HISTORICO_ESCOLAR);
                arquivo.setPiec(piec);
                cadastroService.saveObject(arquivo);
                piec.getDocumentos().add(arquivo);
                cadastroService.atualizaObjeto(piec);
            } else if (acao != null && acao.equals("Aprovar")) {
                piec.setDataAprovacao(new Date());
                piec.setSolicitarAvalacao(false);
                cadastroService.criaNovoBloco(piec.getDisciplinas());
            } else if (acao != null && acao.equals("Solicitar avaliação do plano ao colegiado do curso")) {
                piec.setSolicitarAvalacao(true);
            }
            cadastroService.atualizaObjeto(piec);
            model.addAttribute("sucesso", "Piec alterado com sucesso");
        }
        model.addAttribute(piec);
        model.addAttribute("disciplinasAtivas", cadastroService.disciplinasAtivas());
        model.addAttribute("instituicoes", cadastroService.instituicoes());
        return "cadastro-piec";
    }

    @RequestMapping(value = "/cadastro-disciplina-piec-remover.htm")
    public String removerDisciplina(@ModelAttribute("piec") Piec piec, ModelMap model, HttpServletRequest httpServletRequest,
                                    @RequestParam(value = "idPiecDisciplinaRemover", required = true) Long idPiecDisciplinaRemover) {
        PiecDisciplina piecDisciplinaRemover = cadastroService.getPiecDisciplina(idPiecDisciplinaRemover);
        piec.getPiecDisciplinas().remove(piecDisciplinaRemover);
        CadastroPiecDisciplinaController.removeDisciplinaOrfa(cadastroService, piecDisciplinaRemover);
        cadastroService.removeObject(PiecDisciplina.class, idPiecDisciplinaRemover);
        model.addAttribute(piec);
        model.addAttribute("disciplinasAtivas", cadastroService.disciplinasAtivas());
        model.addAttribute("instituicoes", cadastroService.instituicoes());
        model.addAttribute("sucesso", "Disciplina removida do piec com sucesso");
        CadastroUsuarioController.atualizaUsuarioSessao(piec.getAluno(), cadastroService, httpServletRequest);
        return "cadastro-piec";
    }

    @RequestMapping(value = "/cadastro-piec-arquivo-remover.htm")
    public String removerArquivo(@ModelAttribute("piec") Piec piec, ModelMap model, HttpServletRequest httpServletRequest) {
        Arquivo arquivoRemover = cadastroService.getArquivo(Long.valueOf(httpServletRequest.getParameter("idArquivoRemover")));
        piec.getDocumentos().remove(arquivoRemover);
        cadastroService.removeObject(Arquivo.class, Long.valueOf(httpServletRequest.getParameter("idArquivoRemover")));
        model.addAttribute(piec);
        model.addAttribute("disciplinasAtivas", cadastroService.disciplinasAtivas());
        model.addAttribute("instituicoes", cadastroService.instituicoes());
        model.addAttribute("sucesso", "Documento removido do piec com sucesso");
        CadastroUsuarioController.atualizaUsuarioSessao(piec.getAluno(), cadastroService, httpServletRequest);
        return "cadastro-piec";
    }

    public String adicionarDisciplina(Piec piec, HttpServletRequest httpServletRequest) {
        Disciplina disciplina = cadastroService.getDisciplina(piec.getIdDisciplinaAdicionar());
        if (disciplina == null) {
            disciplina = piec.getNovaDisciplina();
            disciplina.setAtiva(false);
            disciplina.setInstituicao(cadastroService.getInstituicao(disciplina.getIdInstituicao()));
            if (disciplina.getInstituicao() == null) {
                Instituicao instituicao = piec.getNovaInstituicao();
                disciplina.setInstituicao(instituicao);
                cadastroService.saveObject(instituicao);
            }
            cadastroService.saveObject(disciplina);
        }
        PiecDisciplina piecDisciplina = piec.getPiecDisciplinaAdicionar();
        piecDisciplina.setDisciplina(disciplina);
        piecDisciplina.setPiec(piec);
        if (disciplina.getPreAprovada()) {
            piecDisciplina.setAprovada(true);
            piecDisciplina.setDataAprovacaoReprovacao(new Date());
        }
        cadastroService.saveObject(piecDisciplina);
        if (piecDisciplina.getArquivoPlanoEnsino() != null && !piecDisciplina.getArquivoPlanoEnsino().isEmpty()) {
            piecDisciplina.setPlanoEnsino(cadastroService.carregarArquivo(piecDisciplina.getArquivoPlanoEnsino(), piecDisciplina.getPlanoEnsino(), Arquivo.TIPO_PLANO_ENSINO));
            cadastroService.atualizaObjeto(piecDisciplina);
        }
        piec.getPiecDisciplinas().add(piecDisciplina);
        piec.setPiecDisciplinaAdicionar(new PiecDisciplina());
        CadastroUsuarioController.atualizaUsuarioSessao(piec.getAluno(), cadastroService, httpServletRequest);
        return "Disciplina adicionada ao piec com sucesso";
    }

    @RequestMapping("/cadastro-disciplina-piec-avaliar.htm")
    public String alteraStatusPiecDisciplina(ModelMap model, @RequestParam(value = "idPiecDisciplina", required = true) Long idPiecDisciplina
            , @RequestParam(value = "avaliacao", required = false) Boolean avaliacao, HttpServletRequest httpServletRequest) {
        PiecDisciplina piecDisciplina = cadastroService.getPiecDisciplina(idPiecDisciplina);
        Usuario usuario = (Usuario) httpServletRequest.getSession().getAttribute("usuarioLogado");
        piecDisciplina.setAprovada(avaliacao);
        piecDisciplina.setDataAprovacaoReprovacao(new Date());
        piecDisciplina.setUsuarioAprovacao(usuario);
        if (avaliacao) {
            piecDisciplina.getDisciplina().setAtiva(true);
            cadastroService.atualizaObjeto(piecDisciplina.getDisciplina());
        }
        cadastroService.atualizaObjeto(piecDisciplina);
        model.addAttribute(piecDisciplina.getPiec());
        model.addAttribute("disciplinasAtivas", cadastroService.disciplinasAtivas());
        model.addAttribute("instituicoes", cadastroService.instituicoes());
        model.addAttribute("sucesso", "Avaliação de disciplina realizada com sucesso");
        CadastroUsuarioController.atualizaUsuarioSessao(usuario, cadastroService, httpServletRequest);
        return "cadastro-piec";
    }
}
