<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ include file="template/cabecalho.jspf"%>
<%--@elvariable id="blocoAprovado" type="br.ufsm.inf.model.BlocoAprovado"--%>
<div class="container pt" style="padding-top: 30px !important;">
    <form:form commandName="busca" action="blocos.htm" method="post">
        <%@ include file="template/mensagens.jsp"%>
        <form:errors path="*" delimiter="<br/>" element="div" cssClass="alert alert-danger" htmlEscape="false"/>
        <fieldset style="width: 100%; margin-bottom: 30px;">
            <legend style="color: #008289; border: 0;">Sugestões</legend>
            <c:if test="${sessionScope.usuarioLogado.tipo eq 'Aluno'}">
                <table style="width: 100%; margin-bottom: 20px;">
                    <tr>
                        <th style="width: 100%;">
                            <form:select path="filtro" cssStyle="width: 100%;" cssClass="form-control"
                                         onchange="document.getElementById('busca').submit();">
                                <form:option value="Apresentar todas as possibilidades"/>
                                <form:option value="Somente" label="Apresentar somente as possibilidades que possuem minhas disciplinas"/>
                            </form:select>
                        </th>
                    </tr>
                </table>
            </c:if>
            <c:forEach items="${blocosAprovados}" var="blocoAprovado" varStatus="st">
                <table style="width: 100%; margin-bottom: 20px;" class="table table-striped">
                    <tr>
                        <th colspan="4" style="width: 90%;">Opção ${st.index + 1}</th>
                    </tr>
                    <c:forEach items="${blocoAprovado.disciplinas}" var="disciplina">
                        <tr <c:if test="${fn:contains(sessionScope.usuarioLogado.piec.disciplinas, disciplina)}">style="color: lightgray;"</c:if>>
                            <td style="width: 5%;">${disciplina.codigo}</td>
                            <td style="width: 35%;">${disciplina.nome}</td>
                            <td style="width: 35%; text-align: center; white-space: nowrap">${disciplina.instituicao.nomeCompleto}</td>
                            <td>${disciplina.cargaHoraria} horas</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td colspan="4">
                            <c:choose>
                                <c:when test="${sessionScope.usuarioLogado.tipo eq 'Aluno'}">
                                    <a href="${pageContext.request.contextPath}/inserir-bloco-piec.htm?idBlocoAprovado=${blocoAprovado.id}&idUsuario=${sessionScope.usuarioLogado.id}" style="float: right;">
                                        <button class="btn btn-default" type="button">Inserir opção ${st.index + 1} no piec</button>
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/remover-bloco.htm?idBlocoAprovado=${blocoAprovado.id}&idUsuario=${sessionScope.usuarioLogado.id}" style="float: right;">
                                        <input class="btn btn-danger" type="button" value="Excluir opção ${st.index + 1}"/>
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </table>
            </c:forEach>
        </fieldset>
    </form:form>
</div>