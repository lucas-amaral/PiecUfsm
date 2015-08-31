package br.ufsm.inf.interceptor;

import br.ufsm.inf.model.Usuario;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * Created by Lucas on 22/10/2014.
 */
public class AutorizadorInterceptor extends HandlerInterceptorAdapter {
    private static Collection<String> urisAluno = new LinkedHashSet<String>();

    static {
        urisAluno.add("blocos.htm");
        urisAluno.add("inserir-bloco-piec.htm");
        urisAluno.add("cadastro-piec.htm");
        urisAluno.add("cadastro-piec-disciplina.htm");
        urisAluno.add("cadastro-usuario.htm");
        urisAluno.add("cadastro-disciplina-piec-remover.htm");
        urisAluno.add("cadastro-piec-arquivo-remover.htm");
        urisAluno.add("carregar-arquivo.htm");
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object controller) throws Exception {
        String uri = request.getRequestURI();
        if(uri.endsWith("login.htm") || uri.contains("resources")){
            return true;
        }
        if(request.getSession().getAttribute("usuarioLogado") != null) {
            Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");
            if (!usuario.getAtivo()) {
                if (uri.contains("cadastro-usuario.htm")) {
                    return true;
                }
                response.sendRedirect("login.htm");
                return false;
            } else if (usuario.getTipo().equals(Usuario.TIPO_ALUNO)) {
                for (String uriAluno : urisAluno) {
                    if (uri.contains(uriAluno)) {
                        return true;
                    }
                }
                response.sendRedirect("login.htm");
                return false;
            }
            return true;
        }
        response.sendRedirect("login.htm");
        return false;
    }
}
