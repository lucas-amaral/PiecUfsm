<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ include file="template/cabecalho.jspf"%>
<%--@elvariable id="piec" type="br.ufsm.inf.model.Piec"--%>
<div class="container pt" style="padding-top: 30px !important;">
    <form:form commandName="busca" action="piecs.htm" method="post">
        <%@ include file="template/mensagens.jsp"%>
        <form:errors path="*" delimiter="<br/>" element="div" cssClass="alert alert-danger" htmlEscape="false"/>
        <fieldset style="width: 100%; margin-bottom: 30px;">
            <legend style="color: #008289; border: 0;">Planos individuais de estudos complementares</legend>
            <table style="width: 100%; margin-bottom: 20px;">
                <tr>
                    <th style="width: 30%; padding-right: 10px;">
                        <form:select path="filtro" cssStyle="width: 100%;" cssClass="form-control">
                            <form:option value="data_aprovacao" label="Data de aprovação"/>
                            <form:option value="nome" label="Nome do aluno"/>
                            <form:option value="matricula" label="Matricula do aluno"/>
                            <form:option value="codigo" label="Código"/>
                            <form:option value="disciplina.nome" label="Disciplina"/>
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
                    <th>Matricula</th>
                    <th>Aluno</th>
                    <th style="text-align: right;">Carga horária aprovada</th>
                    <th style="text-align: center;">Aprovado</th>
                    <th colspan="2">&nbsp;</th>
                </tr>
                <c:forEach items="${piecs}" var="piec">
                    <tr>
                        <td>${piec.aluno.matricula}</td>
                        <td>${piec.aluno.nome}</td>
                        <td style="text-align: right;">${piec.cargaHorariaDiciplinasAprovadas}</td>
                        <td style="text-align: center;">
                            <c:choose>
                                <c:when test="${not empty piec.dataAprovacao}"><img class="tooltip-class" src="${pageContext.request.contextPath}/resources/img//Ok.png" data-toggle="tooltip" data-placement="left" title="Aprovado em <fmt:formatDate value='${piec.dataAprovacao}' pattern='dd/MM/yyyy HH:mm'/>"/></c:when>
                                <c:otherwise><img class="tooltip-class" src="${pageContext.request.contextPath}/resources/img//Cancel.png" data-toggle="tooltip" data-placement="left" title="Não finalizado"/></c:otherwise>
                            </c:choose>
                        </td>
                        <td style="width: 1px; padding: 9px 1px;"><a class="tooltip-class" href="#" data-toggle="tooltip" data-placement="right" title="Visualizar pdf" onclick="window.open('gerar-pdf.htm?idPiec=${piec.id}');"\><img src="${pageContext.request.contextPath}/resources/img//Search.png"/></a></td>
                        <td style="width: 1px; padding: 9px 1px;"><a class="tooltip-class" href="${pageContext.request.contextPath}/cadastro-piec.htm?idPiec=${piec.id}" data-toggle="tooltip" data-placement="right" title="Editar piec"><img src="${pageContext.request.contextPath}/resources/img//Write.png"/></a></td>
                    </tr>
                </c:forEach>
            </table>
        </fieldset>
    </form:form>
</div>