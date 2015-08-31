package br.ufsm.inf.service;

import br.ufsm.inf.dao.CadastroDao;
import br.ufsm.inf.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Lucas on 29/09/2014.
 */
@Service
public class CadastroService {
    private Integer quantidadeArquivosMemoria = 30;
    public Map<Long, ArquivoTemporario> cacheArquivos = new ConcurrentHashMap<Long, ArquivoTemporario>();

    @Autowired
    public CadastroDao dao;

    public CadastroDao getDao() {
        return dao;
    }

    public void setDao(CadastroDao dao) {
        this.dao = dao;
    }

    public void saveObject(Object objeto) {
        try {
            dao.saveObject(objeto);
        } catch (Exception e) {
            dao.atualizaObjeto(objeto);
        }
    }

    public void removeObject(Class classe, Object idObjeto) {
        dao.removeObject(classe, idObjeto);
    }

    public void refreshObject(Object objeto) {
        dao.refresh(objeto);
    }

    public void atualizaObjeto(Object objeto) {
        dao.atualizaObjeto(objeto);
    }

    public void flush() {
        dao.flush();
    }

    public Disciplina getDisciplina(Long idDisciplina) {
        return (Disciplina) dao.getObjeto(Disciplina.class, idDisciplina);
    }

    public Piec getPiec(Long idPiec) {
        return (Piec) dao.getObjeto(Piec.class, idPiec);
    }

    public Arquivo getArquivo(Long idArquivo) {
        return (Arquivo) dao.getObjeto(Arquivo.class, idArquivo);
    }

    public BlocoAprovado getBlocoAprovado(Long idBlocoAprovado) {
        return (BlocoAprovado) dao.getObjeto(BlocoAprovado.class, idBlocoAprovado);
    }

    public Instituicao getInstituicao(Long idInstituicao) {
        return (Instituicao) dao.getObjeto(Instituicao.class, idInstituicao);
    }

    public Usuario getUsuario(Long idUsuario) {
        return (Usuario) dao.getObjeto(Usuario.class, idUsuario);
    }

    public PiecDisciplina getPiecDisciplina(Long idPiecDisciplina) {
        return (PiecDisciplina) dao.getObjeto(PiecDisciplina.class, idPiecDisciplina);
    }

    public List<Object> buscaAvancada(String tabela, Busca busca) {
        String consulta = "select x from " + tabela + " x";
        String separador = " where ";
        if (tabela.equals("disciplina") && busca.getAtivo()) {
            consulta += separador + " ativa = " + busca.getAtivo();
            separador = " and ";
        }
        if (busca.getExpressao() != null && !busca.getExpressao().equals("")) {
            if (busca.getFiltro().contains("data")) {
                Date[] datas = FuncoesDatas.populaIntervaloData(busca.getExpressao());
                if (datas[0] != null) {
                    consulta += separador + busca.getFiltro() + " between '" + FuncoesDatas.sdf_sql.format(datas[0]) + "' and '" + FuncoesDatas.sdf_sql.format(datas[1]) + "'";
                } else if (datas[1] == null) {
                    return new ArrayList<Object>();
                }
            } else if (busca.getFiltro().equals("id_instituicao")) {
                consulta += separador + busca.getFiltro() + " in (select id from instituicao where nome like '%" + busca.getExpressao() + "%' or sigla like '%" + busca.getExpressao() + "%')";
            } else {
                consulta += separador + busca.getFiltro() + " like '%" + busca.getExpressao() + "%'";
            }
            separador = " and ";
        }
        if (busca.getPreAprovadas()) {
            consulta += separador + "pre_aprovada = " + busca.getPreAprovadas();
        }
        if (tabela.equals("disciplina") || tabela.equals("usuario") || tabela.equals("instituicao")) {
            consulta += " order by x.nome";
        }
        return dao.consulta(consulta);
    }

    public List<Usuario> usuarios() {
        return dao.consulta("select a from usuario a order by a.nome");
    }

    public List<PiecDisciplina> piecsComDisciplina(Long idDisciplina) {
        return dao.consulta("select dp from piec_disciplina dp where dp.disciplina = " + idDisciplina);
    }

    public List<BlocoAprovado> blocosAprovados() {
        return dao.consulta("select b from bloco_aprovado b");
    }

    public List<Disciplina> disciplinasAtivas() {
        return dao.consulta("select d from disciplina d where d.ativa=true order by d.nome");
    }

    public List<Instituicao> instituicoes() {
        return dao.consulta("select i from instituicao i order by i.nome");
    }

    public List<BlocoAprovado> possiveisBlocosAprovados(Collection<Disciplina> disciplinas) {
        List<BlocoAprovado> blocoAprovados = new ArrayList<BlocoAprovado>();
        for (BlocoAprovado blocoAprovado : blocosAprovados()) {
            if (blocoAprovado.getDisciplinas().containsAll(disciplinas)) {
                blocoAprovados.add(blocoAprovado);
            }
        }
        return blocoAprovados;
    }

    public Usuario getUsuario(String nome) {
        List<Usuario> usuarios = (List<Usuario>) dao.consulta("select u from usuario u where u.login ='" + nome + "'");
        if (!usuarios.isEmpty()) {
            return usuarios.iterator().next();
        }
        return null;
    }

    public Arquivo carregarArquivo(MultipartFile arq, Arquivo atual, String tipo) {
        if (arq != null && !arq.isEmpty()) {
            Arquivo arquivo = atual;
            if (arquivo == null) {
                arquivo = new Arquivo();
            } else {
                cacheArquivos.remove(atual.getId());
            }
            try {
                arquivo.setArquivo(dao.criaBlob(arq.getBytes()));
            } catch (IOException e) {
                return null;
            }
            arquivo.setContentType(arq.getContentType());
            arquivo.setDataCadastro(new Date());
            arquivo.setNome(arq.getOriginalFilename());
            arquivo.setTamanho(arq.getSize());
            arquivo.setTipo(tipo);
            if (atual == null) {
                try {
                    saveObject(arquivo);
                } catch (Exception e) {
                    return null;
                }
                return arquivo;
            } else {
                atualizaObjeto(arquivo);
            }
        }
        return atual;
    }

    public synchronized ArquivoTemporario getArquivoCache(Long idArquivo) {
        ArquivoTemporario arquivoTemporario = cacheArquivos.get(idArquivo);
        if (arquivoTemporario == null) {
            Arquivo arquivo = getArquivo(idArquivo);
            if (cacheArquivos.size() >= quantidadeArquivosMemoria) {
                ArquivoTemporario remover = cacheArquivos.values().iterator().next();
                cacheArquivos.remove(remover.getId());
            }
            arquivoTemporario = new ArquivoTemporario(arquivo);
            cacheArquivos.put(idArquivo, arquivoTemporario);
        }
        return arquivoTemporario;
    }

    public List<Piec> solicitacoes() {
        return dao.consulta("select p from piec p where solicitar_avaliacao is true");
    }

    public void criaNovoBloco(Collection<Disciplina> disciplinas) {
        boolean existeBloco = false;
        for (BlocoAprovado blocoAprovado : possiveisBlocosAprovados(disciplinas)) {
            if (blocoAprovado.getDisciplinas().containsAll(disciplinas) && disciplinas.containsAll(blocoAprovado.getDisciplinas())) {
                existeBloco = true;
                break;
            }
        }
        if (!existeBloco) {
            BlocoAprovado blocoAprovado = new BlocoAprovado();
            saveObject(blocoAprovado);
            for (Disciplina disciplina : disciplinas) {
                BlocoAprovadoDisciplina blocoAprovadoDisciplina = new BlocoAprovadoDisciplina();
                blocoAprovadoDisciplina.setDisciplina(disciplina);
                blocoAprovadoDisciplina.setBlocoAprovado(blocoAprovado);
                saveObject(blocoAprovadoDisciplina);
                blocoAprovado.getBlocoAprovadoDisciplinas().add(blocoAprovadoDisciplina);
            }
            atualizaObjeto(blocoAprovado);
        }
    }

    public boolean existeDisciplinaPlano(Long idPiec, Long idDisciplina) {
        return !dao.consulta("select d from piec_disciplina d where id_piec =" + idPiec + " and id_disciplina = " + idDisciplina + " and (aprovada is null or aprovada is true)").isEmpty();
    }

    public Boolean existeDisciplinaEmAlgumPlano(Long idDisciplina) {
        return !dao.consulta("select d from piec_disciplina d where id_disciplina = " + idDisciplina).isEmpty();
    }

    public Boolean existeDisciplinaEmBlocoAprovado(Long idDisciplina) {
        return !dao.consulta("select d from bloco_aprovado_disciplina d where id_disciplina = " + idDisciplina).isEmpty();
    }

    public Boolean existeDisciplina(String codigo) {
        return !dao.consulta("select d from disciplina d where codigo = '"+ codigo+"'").isEmpty();
    }

    public Boolean existeInstituicao(String sigla) {
        return !dao.consulta("select i from instituicao i where sigla = '"+ sigla+"'").isEmpty();
    }

    public Boolean existeInstituicaoEmDisciplina(Long idInstituicao) {
        return !dao.consulta("select d from disciplina d where id_instituicao = "+ idInstituicao).isEmpty();
    }

    public Boolean existeMatricula(Usuario usuario) {
        return !dao.consulta("select u from usuario u where matricula = "+ usuario.getMatricula() +" and id !=" + usuario.getId()).isEmpty();
    }
}
