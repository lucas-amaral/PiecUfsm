<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ include file="template/cabecalho.jspf"%>
<%--@elvariable id="instituicao" type="br.ufsm.inf.model.Instituicao"--%>
<div class="container pt" style="padding-top: 30px !important;">
    <form:form commandName="instituicao" action="cadastro-instituicao.htm" method="post">
        <%@ include file="template/mensagens.jsp"%>
        <form:errors path="*" delimiter="<br/>" element="div" cssClass="alert alert-danger" htmlEscape="false"/>
        <form:hidden path="id"/>
        <fieldset style="width: 100%; margin-bottom: 30px;">
            <legend style="color: #008289; border-bottom: 0;">Instituição</legend>
            <table style="width: 100%; margin-bottom: 30px;">
                <tr>
                    <td style="width: 10%;">
                        <form:label path="sigla">Sigla</form:label>
                        <form:input path="sigla" cssClass="form-control"/>
                    </td>
                    <td style="width: 60%;">
                        <form:label path="nome">Nome</form:label>
                        <form:input path="nome" cssClass="form-control"/>
                    </td>
                    <td>
                        <form:label path="estado" cssStyle="display: block;">Estado</form:label>
                        <form:select path="estado" cssClass="form-control">
                            <form:options items="${instituicao.ufs}"/>
                        </form:select>
                    </td>
                </tr>
            </table>
            <c:if test="${not empty instituicao.id}">
                <a href="${pageContext.request.contextPath}/cadastro-instituicao-remover.htm" style="float: right;"><input class="btn btn-danger" type="button" value="Excluir"/></a>
            </c:if>
            <input class="btn btn-success" type="submit" value="Salvar" style="margin-right: 5px; float: right;"/>
        </fieldset>
    </form:form>
</div>