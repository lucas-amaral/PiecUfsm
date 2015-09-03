<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ include file="template/cabecalho.jspf"%>
<%--@elvariable id="disciplina" type="br.ufsm.inf.model.Disciplina"--%>
<div class="container pt" style="padding-top: 30px !important;">
    <form:form commandName="busca" action="disciplinas.htm" method="post">
        <%@ include file="template/mensagens.jsp"%>
        <form:errors path="*" delimiter="<br/>" element="div" cssClass="alert alert-danger" htmlEscape="false"/>
        <fieldset style="width: 100%; margin-bottom: 30px;">
            <legend style="color: #008289; border: 0;">Disciplinas</legend>
            <table style="width: 100%; margin-bottom: 20px;">
                <tr>
                    <th style="width: 30%; padding-right: 10px;">
                        <form:select path="filtro" cssStyle="width: 100%;" cssClass="form-control">
                            <form:option value="nome" label="Nome"/>
                            <form:option value="codigo" label="Código"/>
                            <form:option value="id_instituicao" label="Instituição"/>
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
                        <form:checkbox path="ativo"/> Ativas
                    </td>
                    <td colspan="2">
                        <form:checkbox path="preAprovadas"/> Pré-aprovadas
                    </td>
                </tr>
            </table>
            <table style="width: 100%;" class="table table-striped">
                <tr>
                    <th>Código</th>
                    <th>Nome</th>
                    <th style="text-align: center;">Instituição</th>
                    <th style="text-align: right;">Carga horária</th>
                    <th colspan="2">&nbsp;</th>
                </tr>
                <c:forEach items="${disciplinas}" var="disciplina">
                    <tr>
                        <td>${disciplina.codigo}</td>
                        <td>${disciplina.nome}</td>
                        <td style="text-align: center;">${disciplina.instituicao.nomeCompleto}</td>
                        <td style="text-align: right;">${disciplina.cargaHoraria} horas</td>
                        <td style="width: 1px; padding: 9px 1px;"><a class="tooltip-class" href="${pageContext.request.contextPath}/cadastro-disciplina.htm?idDisciplina=${disciplina.id}" data-toggle="tooltip" data-placement="left" title="Editar disciplina"><img src="${pageContext.request.contextPath}/resources/img/Write.png"/></a></td>
                        <td style="width: 1px; padding: 9px 1px;">
                            <a class="tooltip-class" data-toggle="tooltip" data-placement="right" href="${pageContext.request.contextPath}/disciplina-remover.htm?idDisciplinaRemover=${disciplina.id}"  title="Remover disciplina">
                                <img src="${pageContext.request.contextPath}/resources/img/Cancel.png" />
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </fieldset>
        <a href="${pageContext.request.contextPath}/cadastro-disciplina.htm" style="float: right;"><button class="btn btn-success" type="button">Cadastrar nova disciplina</button></a>
    </form:form>
</div>