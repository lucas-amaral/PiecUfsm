<%--
  Created by IntelliJ IDEA.
  User: Lucas
  Date: 27/09/2014
  Time: 19:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ include file="template/cabecalho.jspf"%>
<div class="container pt">
    <%@ include file="template/mensagens.jsp"%>
    <div class="col-md-4 column">
        <form:form commandName="usuario" action="login.htm" method="post">
            <div class="form-group">
                <form:label path="login">Login</form:label>
                <form:input path="login" cssClass="form-control" type="text"/>
            </div>
            <div class="form-group">
                <form:label path="senha">Senha</form:label>
                <form:input path="senha" cssClass="form-control" type="password"/>
            </div>
            <button class="btn btn-default" type="submit">Entrar</button>
        </form:form>
    </div>
</div>