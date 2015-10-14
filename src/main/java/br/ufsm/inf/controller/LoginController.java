package br.ufsm.inf.controller;

import br.ufsm.inf.model.Usuario;
import br.ufsm.inf.service.CadastroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.servlet.http.HttpSession;
import java.util.Hashtable;

/**
 * Created by Lucas on 27/09/2014.
 */
@Controller

@SessionAttributes("usuario")
public class LoginController {
    private CadastroService cadastroService;

    @Autowired
    public void setCadastroService(CadastroService cadastroService) {
        this.cadastroService = cadastroService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "redirect:login.htm";
    }

    @RequestMapping(value = "/login.htm", method = RequestMethod.GET)
    public String login(ModelMap model) {
        Usuario usuario;
        usuario = new Usuario();
        model.addAttribute(usuario);
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String efetuaLogin(ModelMap model, HttpSession session, @ModelAttribute("usuario") Usuario usuario) {
        usuario = autenticaLdap(usuario, model);
        if(usuario != null && usuario.getAtivo()) {
            session.setAttribute("usuarioLogado", usuario);
            if (usuario.getTipo().equals(Usuario.TIPO_ALUNO)) {
                if (usuario.getPiec() != null) {
                    return "redirect:cadastro-piec.htm?idPiec=" + usuario.getPiec().getId();
                } else {
                    return "redirect:cadastro-usuario.htm?idUsuario=" + usuario.getId();
                }
            } else {
                return "redirect:solicitacoes.htm";
            }
        } else if (usuario == null) {
            model.addAttribute("erro", "Login e/ou senha não confere");
        } else {
            model.addAttribute("erro", "Seu usuário encontra-se desativado, por favor, entre em contato com a administração para mais informações.");
        }
        return "login";
    }

    @RequestMapping("logout.htm")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:login.htm";
    }

    public Usuario autenticaLdap(Usuario usuario, ModelMap model) {
        Hashtable env = new Hashtable(2);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
//        env.put(Context.PROVIDER_URL, "ldap://localhost:1080/");
        env.put(Context.PROVIDER_URL, "ldap://ldap.inf.ufsm.br/");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "uid=" + usuario.getLogin() + ",ou=People,dc=inf,dc=ufsm,dc=br");
        env.put(Context.SECURITY_CREDENTIALS, usuario.getSenha());
        DirContext ctx = null;
        try {
            ctx = new InitialDirContext(env);
            Attributes matchAttrs = new BasicAttributes(false);
            // Array de atributos que deseja retornar do LDAP
            String[] atributosRetorno = new String[] {"cn"};
            // Parametros de busca
            matchAttrs.put(new BasicAttribute("uid", usuario.getLogin()));
            try {
                // Efetua a busca
                NamingEnumeration resultado = ctx.search("ou=People,dc=inf,dc=ufsm,dc=br", matchAttrs,atributosRetorno);
                // Exibindo o resultado, se houver
                while (resultado.hasMore()) {
                    SearchResult sr = (SearchResult) resultado.next();
                    // Obtem os atributos
                    Attributes atributos = sr.getAttributes();
                    for (NamingEnumeration todosAtributos = atributos.getAll(); todosAtributos.hasMore();) {
                        Attribute attrib = (Attribute) todosAtributos.next();
                        // Exibe todos os valores do atributo
                        for (NamingEnumeration e = attrib.getAll(); e.hasMore();) {
                            usuario.setNome(e.next().toString());
                            break;
                        }
                    }
                }
                Usuario usuarioCadastrado = cadastroService.getUsuario(usuario.getNome());
                if (usuarioCadastrado != null) {
                    usuarioCadastrado.setLogin(usuario.getLogin());
                    cadastroService.atualizaObjeto(usuarioCadastrado);
                    return usuarioCadastrado;
                } else {
                    cadastroService.saveObject(usuario);
                    return usuario;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
