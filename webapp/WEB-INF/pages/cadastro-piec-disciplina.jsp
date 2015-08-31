<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ include file="template/cabecalho.jspf"%>
<%@ include file="template/cabecalho.jspf"%>
<%--@elvariable id="piecDisciplina" type="br.ufsm.inf.model.PiecDisciplina"--%>
<div class="container pt" style="padding-top: 30px !important;">
    <form:form commandName="piecDisciplina" action="cadastro-piec-disciplina.htm" method="post" enctype="multipart/form-data">
        <%@ include file="template/mensagens.jsp"%>
        <form:errors path="*" delimiter="<br/>" element="div" cssClass="alert alert-danger" htmlEscape="false"/>
        <form:hidden path="id"/>
        <fieldset style="width: 100%; margin-bottom: 30px;">
            <legend style="color: #008289; border-bottom: 0;">Disciplina solicitada</legend>
            <table style="width: 100%; margin-bottom: 30px;">
                <tr>
                    <td style="width: 20%;">
                        <form:label path="disciplina.codigo">Código</form:label>
                        <form:input path="disciplina.codigo" cssClass="form-control" cssStyle="width: auto;" disabled="true"/>
                    </td>
                    <td style="width: 40%;">
                        <form:label path="disciplina.nome">Nome</form:label>
                        <form:input path="disciplina.nome" cssClass="form-control" disabled="true"/>
                    </td>
                    <td>
                        <form:label path="disciplina.instituicao">Instituição</form:label>
                        <form:input path="disciplina.instituicao.sigla" cssClass="form-control" cssStyle="width: auto;" disabled="true"/>
                    </td>
                    <td>
                        <form:label path="disciplina.cargaHoraria" cssStyle="display: block;">Carga horária</form:label>
                        <div class="input-group" style="width: 150px;">
                            <form:input path="disciplina.cargaHoraria" cssClass="form-control" cssStyle="text-align: right;" disabled="true"/>
                            <div class="input-group-addon">horas</div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form:label path="cursoOfertante">Curso ofertante</form:label>
                        <form:input path="cursoOfertante" cssClass="form-control" cssStyle="width: auto;"/>
                    </td>
                    <td>
                        <form:label path="semestreAnoRealizacao" cssStyle="display: block;">Semetre/Ano de realização</form:label>
                        <form:input path="semestreAnoRealizacao" cssClass="form-control" cssStyle="width: auto;" placeholder="I/AAAA ou II/AAAA"/>
                    </td>
                    <td colspan="2">
                        <c:if test="${not piecDisciplina.disciplina.preAprovada}">
                            <label style="display: block;">Plano de ensino</label>
                            <input type="file" name="arquivoPlanoEnsino" id="arquivoPlanoEnsino" style="margin: 0; display: inline-block;"/>
                            <c:if test="${not empty piecDisciplina.planoEnsino}">
                                <img class="tooltip-class" src="${pageContext.request.contextPath}/resources/img/Search.png" title="Ver arquivo" data-toggle="tooltip" data-placement="left" onclick="window.open('carregar-arquivo.htm?id=${piecDisciplina.planoEnsino.id}&abrir=true');"/>
                                <img class="tooltip-class" src="${pageContext.request.contextPath}/resources/img/Cancel.png" title="Remover arquivo" data-toggle="tooltip" data-placement="left" onclick="submeteAcao('RemoverArquivo', document.getElementById('piecDisciplina'));"/>
                            </c:if>
                        </c:if>
                    </td>
                </tr>
                <c:if test="${not piecDisciplina.disciplina.preAprovada}">
                    <tr>
                        <td colspan="4">
                            <form:label path="relevanciaIntegralizacao">Relevancia da integralização</form:label>
                            <form:textarea path="relevanciaIntegralizacao" cssClass="form-control" cssStyle="width: 100%;"
                                           rows="${2+((fn:length(piecDisciplina.relevanciaIntegralizacao)/350)*2)}"/>
                        </td>
                    </tr>
                </c:if>
            </table>

            <c:if test="${(empty piecDisciplina.aprovada or piecDisciplina.aprovada) and sessionScope.usuarioLogado.id eq piecDisciplina.piec.aluno.id and not piecDisciplina.piec.solicitarAvalacao}">
                <a href="#" onclick="submeteAcao('RemoverPiecDisciplina', document.getElementById('piecDisciplina'));" style="float: right;">
                    <input class="btn btn-danger" type="button" value="Remover"/>
                </a>
            </c:if>
            <c:if test="${sessionScope.usuarioLogado.membroColegiado or not piecDisciplina.piec.solicitarAvalacao}">
                <input class="btn btn-success" type="submit" value="Salvar" style="margin-right: 5px; float: right;"/>
            </c:if>
            <a href="${pageContext.request.contextPath}/cadastro-piec.htm?idPiec=${piecDisciplina.piec.id}"><button class="btn btn-default" type="button">Voltar para piec</button></a>
        </fieldset>
    </form:form>
</div>