<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ include file="template/cabecalho.jspf"%>
<%--@elvariable id="instituicao" type="br.ufsm.inf.model.Instituicao"--%>
<div class="container pt" style="padding-top: 30px !important;">
    <form:form commandName="busca" action="instituicoes.htm" method="post">
        <%@ include file="template/mensagens.jsp"%>
        <form:errors path="*" delimiter="<br/>" element="div" cssClass="alert alert-danger" htmlEscape="false"/>
        <fieldset style="width: 100%; margin-bottom: 30px;">
            <legend style="color: #008289; border: 0;">Instituições</legend>
            <table style="width: 100%; margin-bottom: 20px;">
                <tr>
                    <th style="width: 30%; padding-right: 10px;">
                        <form:select path="filtro" cssStyle="width: 100%;" cssClass="form-control">
                            <form:option value="nome" label="Nome"/>
                            <form:option value="sigla" label="Sigla"/>
                            <form:option value="estado" label="Estado"/>
                        </form:select>
                    </th>
                    <th style="width: 70%; padding-right: 10px;">
                        <form:input path="expressao" cssStyle="width: 100%;"  cssClass="form-control"/>
                    </th>
                    <th>
                        <button class="btn btn-default" type="submit">Buscar</button>
                    </th>
                </tr>
            </table>
            <table style="width: 100%;" class="table table-striped">
                <tr>
                    <th>Sigla</th>
                    <th>Nome</th>
                    <th>Estado</th>
                    <th colspan="2">&nbsp;</th>
                </tr>
                <c:forEach items="${instituicoes}" var="instituicao">
                    <tr>
                        <td>${instituicao.sigla}</td>
                        <td>${instituicao.nome}</td>
                        <td>${instituicao.estado}</td>
                        <td style="width: 1px; padding: 9px 1px;"><a class="tooltip-class" href="${pageContext.request.contextPath}/cadastro-instituicao.htm?idInstituicao=${instituicao.id}" data-toggle="tooltip" data-placement="left" title="Editar instituição"><img src="${pageContext.request.contextPath}/resources/img/Write.png"/></a></td>
                        <td style="width: 1px; padding: 9px 1px;">
                            <a class="tooltip-class" data-toggle="tooltip" data-placement="right" href="${pageContext.request.contextPath}/instituicao-remover.htm?idInstituicaoRemover=${instituicao.id}" title="Remover instituição">
                                <img src="${pageContext.request.contextPath}/resources/img/Cancel.png"/>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </fieldset>
        <a href="${pageContext.request.contextPath}/cadastro-instituicao.htm" style="float: right;"><button class="btn btn-success" type="button">Cadastrar nova instituição</button></a>
    </form:form>
</div>