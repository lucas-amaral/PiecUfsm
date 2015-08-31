<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ include file="template/cabecalho.jspf"%>
<%@ include file="template/cabecalho.jspf"%>
<%--@elvariable id="usuario" type="br.ufsm.inf.model.Usuario"--%>
<div class="container pt" style="padding-top: 30px !important;">
    <form:form commandName="busca" action="usuarios.htm" method="post">
        <%@ include file="template/mensagens.jsp"%>
        <form:errors path="*" delimiter="<br/>" element="div" cssClass="alert alert-danger" htmlEscape="false"/>
        <fieldset style="width: 100%; margin-bottom: 30px;">
            <legend style="color: #008289; border: 0;">Usuários</legend>
            <table style="width: 100%; margin-bottom: 20px;">
                <tr>
                    <th style="width: 30%; padding-right: 10px;">
                        <form:select path="filtro" cssStyle="width: 100%;" cssClass="form-control">
                            <form:option value="nome" label="Nome"/>
                            <form:option value="matricula" label="Matricula"/>
                            <form:option value="login" label="Login"/>
                        </form:select>
                    </th>
                    <th style="width: 70%; padding-right: 10px;">
                        <form:input path="expressao" cssStyle="width: 100%;"  cssClass="form-control"/>
                    </th>
                    <th>
                        <button class="btn btn-default" type="submit">Buscar</button>
                    </th>
                </tr>
                <tr>
                    <td>
                        <form:checkbox path="ativo"/> Somente ativos
                    </td>
                </tr>
            </table>
            <table style="width: 100%;" class="table table-striped">
                <tr>
                    <th>Matricula</th>
                    <th>Nome</th>
                    <th>Login</th>
                    <th>&nbsp;</th>
                </tr>
                <c:forEach items="${usuarios}" var="usuario">
                    <tr>
                        <td>
                            <c:choose>
                                <c:when test="${usuario.membroColegiado}">Membro do colegiado</c:when>
                                <c:otherwise>${usuario.matricula}</c:otherwise>
                            </c:choose>
                        </td>
                        <td>${usuario.nome}</td>
                        <td>${usuario.login}</td>
                        <td style="width: 1px; padding: 9px 1px;"><a class="tooltip-class" href="${pageContext.request.contextPath}/cadastro-usuario.htm?idUsuario=${usuario.id}" data-placement="right" data-toggle="modal" title="Editar usuario"><img src="${pageContext.request.contextPath}/resources/img/Write.png"/></a></td>
                    </tr>
                </c:forEach>
            </table>
        </fieldset>
    </form:form>
</div>
