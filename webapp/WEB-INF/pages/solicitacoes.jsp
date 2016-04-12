<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ include file="template/cabecalho.jspf"%>
<%--@elvariable id="piec" type="br.ufsm.inf.model.Piec"--%>
<div class="container pt" style="padding-top: 30px !important;">
    <form:form action="solicitacoes.htm" method="post">
        <%@ include file="template/mensagens.jsp"%>
        <form:errors path="*" delimiter="<br/>" element="div" cssClass="alert alert-danger" htmlEscape="false"/>
        <fieldset style="width: 100%; margin-bottom: 30px;">
            <legend style="color: #008289; border: 0;">Solicitações</legend>
            <table style="width: 100%;" class="table table-striped">
                <tr>
                    <th>Matricula</th>
                    <th>Aluno</th>
                    <th style="text-align: right;">Carga horária aprovada</th>
                    <th colspan="2">&nbsp;</th>
                </tr>
                <c:forEach items="${piecs}" var="piec">
                    <tr>
                        <td>${piec.aluno.matricula}</td>
                        <td>${piec.aluno.nome}</td>
                        <td style="text-align: right;">${piec.cargaHorariaDiciplinasAprovadas}</td>
                        <td style="width: 1px; padding: 9px 1px;"><a class="tooltip-class" href="#" data-toggle="tooltip" data-placement="right" title="Visualizar pdf" onclick="window.open('gerar-pdf.htm?idPiec=${piec.id}');"\><img src="${pageContext.request.contextPath}/resources/img//Search.png"/></a></td>
                        <td style="width: 1px;"><a class="tooltip-class" href="${pageContext.request.contextPath}/cadastro-piec.htm?idPiec=${piec.id}" data-toggle="tooltip" data-placement="right" title="Editar piec"><img src="${pageContext.request.contextPath}/resources/img//Write.png"/></a></td>
                    </tr>
                </c:forEach>
            </table>
        </fieldset>
    </form:form>
</div>