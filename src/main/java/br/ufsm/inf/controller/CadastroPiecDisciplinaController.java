package br.ufsm.inf.controller;

import br.ufsm.inf.model.Arquivo;
import br.ufsm.inf.model.Disciplina;
import br.ufsm.inf.model.PiecDisciplina;
import br.ufsm.inf.service.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Lucas on 29/09/2014.
 */
@Controller
@SessionAttributes("piecDisciplina")
public class CadastroPiecDisciplinaController {
    private CadastroService cadastroService;
    private CadastroPiecDisciplinaValidator cadastroPiecDisciplinaValidator;

    @Autowired
    public CadastroPiecDisciplinaController(CadastroPiecDisciplinaValidator cadastroPiecDisciplinaValidator) {
        this.cadastroPiecDisciplinaValidator = cadastroPiecDisciplinaValidator;
    }

    @Autowired
    public void setCadastroService(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    @RequestMapping(value = "/cadastro-piec-disciplina.htm", method = RequestMethod.GET)
    public String showForm(ModelMap model, @RequestParam(value = "idPiecDisciplina", required = false) String idPiecDisciplina)  {
        PiecDisciplina piecDisciplina;
        if (idPiecDisciplina != null) {
            piecDisciplina = cadastroService.getPiecDisciplina(Long.valueOf(idPiecDisciplina));
        } else {
            piecDisciplina = new PiecDisciplina();
        }
        model.addAttribute(piecDisciplina);
        return "cadastro-piec-disciplina";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String salvar(@ModelAttribute("piecDisciplina") PiecDisciplina piecDisciplina, Errors errors, ModelMap model, @RequestParam(value = "acao", required = false) String acao) {
        cadastroPiecDisciplinaValidator.validate(piecDisciplina, errors, acao);
        if (!errors.hasErrors()) {
            if (acao != null && acao.equals("RemoverPiecDisciplina")) {
                return remover(piecDisciplina, model);
            } else if (piecDisciplina.getArquivoPlanoEnsino() != null && !piecDisciplina.getArquivoPlanoEnsino().isEmpty()) {
                Arquivo arquivo = piecDisciplina.getPlanoEnsino();
                piecDisciplina.setPlanoEnsino(null);
                cadastroService.atualizaObjeto(piecDisciplina);
                piecDisciplina.setPlanoEnsino(cadastroService.carregarArquivo(piecDisciplina.getArquivoPlanoEnsino(), piecDisciplina.getPlanoEnsino(), Arquivo.TIPO_PLANO_ENSINO));
                cadastroService.removeObject(Arquivo.class, arquivo.getId());
            }
            cadastroService.saveObject(piecDisciplina);
            model.addAttribute("sucesso", "Disciplina do piec alterada com sucesso");
        }
        return "cadastro-piec-disciplina";
    }

    public String remover(PiecDisciplina piecDisciplina, ModelMap model) {
        Long idPiec = piecDisciplina.getPiec().getId();
        removeDisciplinaOrfa(cadastroService, piecDisciplina);
        cadastroService.removeObject(PiecDisciplina.class, piecDisciplina.getId());
        model.addAttribute("sucesso", "Disciplina removida do piec com sucesso");
        return "redirect:cadastro-piec.htm?idPiec=" + idPiec;
    }

    public static void removeDisciplinaOrfa(CadastroService cadastroService, PiecDisciplina piecDisciplina) {
        Disciplina disciplina = piecDisciplina.getDisciplina();
        piecDisciplina.setDisciplina(null);
        cadastroService.atualizaObjeto(piecDisciplina);
        List<PiecDisciplina> piecsDisciplinas = cadastroService.piecsComDisciplina(disciplina.getId());
        if ((piecsDisciplinas == null || piecsDisciplinas.isEmpty()) && !disciplina.getPreAprovada()) {
            cadastroService.removeObject(Disciplina.class, disciplina.getId());
        }
    }
}
