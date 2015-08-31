<c:if test="${fn:contains(requestScope['javax.servlet.forward.servlet_path'], 'htm')}">
    <script type="text/javascript">
        function mudaImagem(elemento) {
            if (elemento.style.backgroundImage.indexOf("aberto") > -1 && elemento.childNodes.length > 1) {
                elemento.style.backgroundImage = "url('img/icons/envelope_fechado.png')";
            } else if (elemento.style.backgroundImage.indexOf("aberto") > -1) {
                elemento.style.backgroundImage = "url('img/icons/envelope_opaco.png')";
            } else {
                elemento.style.backgroundImage = "url('img/icons/envelope_aberto.png')";
            }
        }
    </script>
    <%--@elvariable id="contextoAjuda" type="br.com.mega.silas.model.system.Ajuda"--%>
    <div class="clearfix" id="cabecalhoCorpo" style="margin: 10px 0 0 0; padding:0;">
        <div id="IconeMostraEscondeMenu" onclick="mostraEscondeOffset('menu', 'block');" style="display: none; cursor: pointer; border-right: 1px solid #cccccc; margin-top: 3px; width: 28px; padding: 2px 8px 2px 4px;">
            <div style="width: 23px; border-radius: 1px; border: 2px solid #dfdfdf; margin: 6px 0; padding: 0;"></div>
            <div style="width: 23px; border-radius: 1px; border: 2px solid #dfdfdf; margin: 6px 0; padding: 0;"></div>
            <div style="width: 23px; border-radius: 1px; border: 2px solid #dfdfdf; margin: 6px 0; padding: 0;"></div>
        </div>
        <c:if test="${not empty sessionScope.user}">
            <div id="menuCabecalho" style="float: right; padding: 0; margin: 0;">
                <c:catch>
                    <c:forEach items="${sessionScope.moduloAnalise.secoesCabecalhoInterno}" var="pagina">
                        <c:set value="" var="funcaoAposMostrarPaginaCabecalhoInterno"/>
                        <c:choose>
                            <c:when test="${pagina.buscas[0].automatica}">
                                <c:set value="executaBuscaCabecalho(${pagina.buscas[0].id})" var="funcaoAposMostrarPaginaCabecalhoInterno"/>
                            </c:when>
                            <c:when test="${pagina.abas[0].buscas[0].automatica}">
                                <c:set value="${funcaoAposMostrarPaginaCabecalhoInterno} executaBuscaCabecalho(${pagina.abas[0].buscas[0].id})" var="funcaoAposMostrarPaginaCabecalhoInterno"/>
                            </c:when>
                        </c:choose>
                        <c:choose>
                            <c:when test="${not empty pagina.caminhoIcone and pagina.tipo eq 'Est�tica'}">
                                <c:choose>
                                    <c:when test="${fn:contains(pagina.link, '.htm')}">
                                        <a href="${pagina.link}" style="display: inline-block; vertical-align: middle; border-bottom: 0 !important;"><img src="${pagina.caminhoIcone}"/></a>
                                    </c:when>
                                    <c:otherwise>
                                        <img src="${pagina.caminhoIcone}" onclick="mostraEscondeUrl('mensagens', '${pagina.link}'); return false;" style="display: inline-block; cursor: pointer; vertical-align:middle;"/>
                                    </c:otherwise>
                                </c:choose>
                            </c:when>
                            <c:when test="${not empty pagina.caminhoIcone}">
                                <img src="${pagina.caminhoIcone}" onclick="mostraEscondeUrlFuncao('mensagens', '${pagina.linkAjax}', 'selecionarPrimeiroElemento(\'BuscaCommand_${pagina.buscas[0].id}\', \'INPUT\', \'text\'); ${funcaoAposMostrarPaginaCabecalhoInterno}'); return false;" style="display: inline-block; cursor: pointer; vertical-align:middle;"/>
                            </c:when>
                            <c:otherwise>
                                <div class="menu" style="display: inline-block; padding: 6px !important; cursor: pointer; text-align: center;" onclick="mostraEscondeUrlFuncao('mensagens', '${pagina.linkAjax}', 'selecionarPrimeiroElemento(\'BuscaCommand_${pagina.buscas[0].id}\', \'INPUT\', \'text\'); ${funcaoAposMostrarPaginaCabecalhoInterno}'); return false;">
                                    <a href="">${pagina.titulo}</a>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:catch>
            </div>
        </c:if>
        <h1 style="margin: 0; padding: 10px 0 0;" <c:if test="${not empty idTituloPagina}">id="${idTituloPagina}"</c:if>>${tituloPagina}&nbsp;</h1>
        <c:if test="${not empty sessionScope.user}">
            <div id="chats" style="background-color: #fafafa; margin: 10px 0 0;">
                <c:if test="${not empty sessionScope.salas}">
                    <c:forEach items="${sessionScope.salas}" var="sala">
                        <tags:sala sala="${sala.value}"/>
                    </c:forEach>
                </c:if>
            </div>
            <div id="mensagens" style="border: 1px solid #a9a9a9; display:none; padding: 10px; background-color: #fafafa; margin: 10px 0 0;"></div>
            <div id="caixaAjuda" style="border: 1px solid #6495ed; display:none; padding: 10px; background-color: #edf2fa; color: #6f85ac; margin: 10px 0 0;"></div>
            <c:if test="${not empty sessionScope.user}"><script type="text/javascript">verificaChat();</script></c:if>
        </c:if>
    </div>
</c:if>