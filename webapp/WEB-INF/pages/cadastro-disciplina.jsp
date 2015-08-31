<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ include file="template/cabecalho.jspf"%>
<%--@elvariable id="disciplina" type="br.ufsm.inf.model.Disciplina"--%>
<div class="container pt" style="padding-top: 30px !important;">
    <form:form commandName="disciplina" action="cadastro-disciplina.htm" method="post">
        <%@ include file="template/mensagens.jsp"%>
        <form:errors path="*" delimiter="<br/>" element="div" cssClass="alert alert-danger" htmlEscape="false"/>
        <form:hidden path="id"/>
        <fieldset style="width: 100%; margin-bottom: 30px;">
            <legend style="color: #008289; border-bottom: 0;">Disciplina</legend>
            <table style="width: 100%; margin-bottom: 30px;">
                <tr>
                    <td style="width: 20%;">
                        <form:label path="codigo">Código</form:label>
                        <form:input path="codigo" cssClass="form-control"/>
                    </td>
                    <td style="width: 50%;" colspan="2">
                        <form:label path="nome">Nome</form:label>
                        <form:input path="nome" cssClass="form-control"/>
                    </td>
                    <td>
                        <form:label path="cargaHoraria" cssStyle="display: block;">Carga horária</form:label>
                        <div class="input-group" style="width: 150px;">
                            <form:select path="cargaHoraria" cssClass="form-control" cssStyle="text-align: right;">
                                <c:forEach begin="10" end="200" varStatus="st">
                                    <form:option value="${st.index}"/>
                                </c:forEach>
                            </form:select>
                            <div class="input-group-addon">horas</div>
                        </div>
                    </td>
                    <td style="vertical-align: bottom;">
                        <form:label path="ativa">&nbsp;</form:label>
                        <div class="checkbox">
                            <form:checkbox path="ativa"/> Ativa
                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <form:label path="idInstituicao">Instituição</form:label>
                        <form:select path="idInstituicao" cssClass="form-control">
                            <c:forEach items="${instituicoes}" var="instituicao">
                                <form:option value="${instituicao.id}" label="${instituicao.nomeCompleto}"/>
                            </c:forEach>
                        </form:select>
                    </td>
                    <td colspan="2" style="vertical-align: bottom; white-space: nowrap;">
                        <div class="checkbox">
                            <form:label path="preAprovada">&nbsp;</form:label>
                            <form:checkbox path="preAprovada"/> Pré-aprovada
                        </div>
                    </td>
                    <td>&nbsp;</td>
                </tr>
            </table>
            <c:if test="${not empty disciplina.id}">
                <a href="${pageContext.request.contextPath}/cadastro-disciplina-remover.htm"  style="float: right;"><input class="btn btn-danger" type="button" value="Excluir"/></a>
            </c:if>
            <input id="salvar" class="btn btn-success" type="submit" value="Salvar" style="margin-right: 5px; float: right;"/>
        </fieldset>
    </form:form>
</div>