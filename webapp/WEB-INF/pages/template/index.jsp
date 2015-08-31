<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>PIEC - Online</title>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="description" content="O programa PIEC Online foi desenvolvido com o intuido de auxiliar e automatizar o processo de registros de disciplinas complementares da UFSM.">
    <meta name="author" content="Lucas Amaral">

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="css/main.css" rel="stylesheet">

    <!-- Scripts -->
    <script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/font-awesome/css/font-awesome.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/js/colorbox/css/colorbox.css">
    <link rel="stylesheet" type="font" href="${pageContext.request.contextPath}/resources/css/font-awesome/fonts/FontAwesome.otf">
    <link rel="stylesheet" type="font" href="${pageContext.request.contextPath}/resources/css/font-awesome/fonts/fontawesome-webfont.eot">
    <link rel="stylesheet" type="font" href="${pageContext.request.contextPath}/resources/css/font-awesome/fonts/fontawesome-webfont.ttf">
    <link rel="stylesheet" type="font" href="${pageContext.request.contextPath}/resources/css/font-awesome/fonts/fontawesome-webfont.woff">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/hover.zoom.conf.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/hover.zoom.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/inicia-scripts.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/funcoes.js"></script>
</head>
<body>
    <!-- Static navbar -->
    <div class="navbar navbar-inverse navbar-static-top">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="">
                    <img class="logo" src="${pageContext.request.contextPath}/resources/img/logo.gif" style="width: 200px; height: 100px;" title="Voltar à capa"  alt=""/>
                </a>
            </div>
            <div class="navbar-collapse collapse">
                <c:if test="${not empty sessionScope.usuarioLogado}">
                    <ul class="nav navbar-nav navbar-right">
                        <c:choose>
                            <c:when test="${sessionScope.usuarioLogado.tipo eq 'Aluno'}">
                                <li><a href="cadastro-usuario.htm?idUsuario=${sessionScope.usuarioLogado.id}"><img src="${pageContext.request.contextPath}/resources/img/user.gif"/> Aluno</a></li>
                                <c:if test="${not empty sessionScope.usuarioLogado.piec}">
                                    <li><a href="cadastro-piec.htm?idPiec=${sessionScope.usuarioLogado.piec.id}"><img src="${pageContext.request.contextPath}/resources/img/piec.gif"/> Piec</a></li>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <li><a href="solicitacoes.htm"><img src="${pageContext.request.contextPath}/resources/img/solicitacao.gif"/> Solicitações</a></li>
                                <li><a href="piecs.htm"><img src="${pageContext.request.contextPath}/resources/img/piec.gif"/> Piecs</a></li>
                                <li><a href="usuarios.htm"><img src="${pageContext.request.contextPath}/resources/img/user.gif"/> Usuários</a></li>
                                <li><a href="disciplinas.htm"><img src="${pageContext.request.contextPath}/resources/img/diciplina.gif"/> Disciplinas</a></li>
                                <li><a href="instituicoes.htm"><img src="${pageContext.request.contextPath}/resources/img/home.gif"/> Instituições</a></li>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${not empty sessionScope.usuarioLogado.piec or sessionScope.usuarioLogado.tipo ne 'Aluno'}">
                            <li><a href="blocos.htm"><img src="${pageContext.request.contextPath}/resources/img/bloco.gif"/> Sugestões</a></li>
                        </c:if>
                        <li><a href="logout.htm"><img src="${pageContext.request.contextPath}/resources/img/cancelar.gif"/> Sair</a></li>
                    </ul>
                </c:if>
            </div>
        </div>
    </div>
    <div class="faixa-interna"></div>

    <div id="sub-conteudo" ><decorator:body/></div>

    <!-- Footer Section -->
    <div id="footer">
        <div class="container">
            <div class="row">
                <div class="col-lg-6 brasao-rodape">
                    <div class="fones">
                        <p>
                            <span><img src="${pageContext.request.contextPath}/resources/img/celular.png" alt=""/> (55) 3220 8523</span>
                            <span><img src="${pageContext.request.contextPath}/resources/img/celular.png" alt=""/> (55) 3220 8849</span>
                            <a href="mailto::coord-bcc@inf.ufsm.br">coord-bcc@inf.ufsm.br</a><br>
                        </p>
                    </div>
                </div>
                <div class="col-lg-6 menu-rodape">
                    <strong style="font-size: 1.2em">Centro de Tecnologia - UFSM</strong><br />
                    Avenida Roraima, 1000<br />
                    Santa Maria, RS | 97105-900<br />
                    Website desenvolvido por <a href="http://www.inf.ufsm.br" target="_blank">Informática - UFSM</a>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        $('.tooltip-class').tooltip();
    </script>
</body>
</html>
