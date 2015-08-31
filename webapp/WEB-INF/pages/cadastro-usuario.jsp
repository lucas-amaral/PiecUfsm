<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ include file="template/cabecalho.jspf"%>
<%--@elvariable id="usuario" type="br.ufsm.inf.model.Usuario"--%>
<div class="container pt" style="padding-top: 30px !important;">
    <form:form commandName="usuario" action="cadastro-usuario.htm" method="post">
        <%@ include file="template/mensagens.jsp"%>
        <form:errors path="*" delimiter="<br/>" element="div" cssClass="alert alert-danger" htmlEscape="false"/>
        <form:hidden path="id"/>
        <fieldset style="width: 100%; margin-bottom: 30px;">
            <legend style="color: #008289; border-bottom: 0;">
                <c:choose>
                    <c:when test="${sessionScope.usuarioLogado.id eq usuario.id and not sessionScope.usuarioLogado.membroColegiado}">Aluno</c:when>
                    <c:otherwise>Usuário</c:otherwise>
                </c:choose>
            </legend>
            <table style="width: 100%; margin-bottom: 30px;">
                <tr>
                    <td style="width: 20%;">
                        <form:label path="matricula">Matricula</form:label>
                        <form:input path="matricula" cssClass="form-control"/>
                    </td>
                    <td style="width: 50%;">
                        <form:label path="nome">Nome</form:label>
                        <form:input path="nome" cssClass="form-control" disabled="true"/>
                    </td>
                    <td style="width: 215px;">
                        <form:label path="login">Login</form:label>
                        <form:input path="login" cssClass="form-control" cssStyle="width: 200px;" disabled="true"/>
                    </td>
                    <td style="vertical-align: bottom; white-space: nowrap;">
                        <c:if test="${not empty sessionScope.usuarioLogado.piec or sessionScope.usuarioLogado.tipo ne 'Aluno'}">
                            <label style="display: block;">Piec</label>
                            <a class="tooltip-class" href="cadastro-piec.htm?idPiec=${usuario.piec.id}"><img src="${pageContext.request.contextPath}/resources/img/Document2.png" data-toggle="tooltip" data-placement="left" title="Editar piec"/></a>
                        </c:if>
                    </td>
                </tr>
                <tr>
                    <td style="<c:if test="${sessionScope.usuarioLogado.tipo ne 'Colegiado'}">display: none;</c:if>">
                        <form:label path="tipo">Tipo</form:label>
                        <form:select path="tipo" cssClass="form-control">
                            <form:option value="Aluno"/>
                            <form:option value="Colegiado"/>
                        </form:select>
                    </td>
                </tr>
            </table>
            <input class="btn btn-success" type="submit" value="Salvar" style="float: right;"/>
        </fieldset>
    </form:form>
</div>