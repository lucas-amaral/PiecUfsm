<%@ page contentType="text/html;charset=ISO-8859-1" language="java" %>
<%@ include file="template/cabecalho.jspf"%>
<div class="container pt" style="padding-top: 30px !important;">
    <form:form commandName="piec" action="cadastro-piec.htm" method="post" enctype="multipart/form-data">
        <%@ include file="template/mensagens.jsp"%>
        <form:errors path="*" delimiter="<br/>" element="div" cssClass="alert alert-danger" htmlEscape="false"/>
        <form:hidden path="id"/>
        <fieldset style="width: 100%; margin-bottom: 30px;">
            <legend style="color: #008289; border-bottom: 0;">PIEC</legend>
            <table style="width: 100%; margin-bottom: 30px;">
                <tr>
                    <td>
                        <label>Nome</label>
                        <input class="form-control" disabled="disabled" value="${piec.aluno.nome}"/>
                    </td>
                    <td>
                        <label>Matricula</label>
                        <input class="form-control" disabled="disabled" value="${piec.aluno.matricula}" style="width: auto;"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <form:label path="perfil">Descrição do perfil que o aluno deseja obter através deste plano individual de estudos</form:label>
                        <form:textarea path="perfil" cssClass="form-control" cssStyle="width: 100%;" disabled="${sessionScope.usuarioLogado.id ne piec.aluno.id}"
                                       rows="${2+((fn:length(piec.perfil)/350)*2)}"/>
                    </td>
                </tr>
            </table>
            <c:if test="${piec.possuiDisciplinasExternas}">
                <fieldset id="documentos" style="width: 100%;">
                    <legend style="color: #008289; border-bottom: 0;">Documentos</legend>
                    <table style="width: 100%;" class="table">
                        <tr class="active">
                            <th>Nome arquivo</th>
                            <th colspan="2">&nbsp;</th>
                        </tr>
                        <c:forEach items="${piec.documentos}" var="documento">
                            <tr>
                                <td>${documento.nome}</td>
                                <td style="width: 1px; text-align: right; padding: 9px 1px;">
                                    <img class="tooltip-class" src="${pageContext.request.contextPath}/resources/img/Search.png" data-toggle="tooltip" data-placement="left" title="Ver arquivo" onclick="window.open('carregar-arquivo.htm?id=${documento.id}&abrir=true');"/>
                                </td>
                                <td style="width: 1px; text-align: right; padding: 9px 1px;">
                                    <c:if test="${sessionScope.usuarioLogado.id eq piec.aluno.id}">
                                        <a href="${pageContext.request.contextPath}/cadastro-piec-arquivo-remover.htm?idArquivoRemover=${documento.id}" class="tooltip-class">
                                            <img src="${pageContext.request.contextPath}/resources/img/Cancel.png" data-toggle="tooltip" data-placement="left" title="Remover arquivo"/>
                                        </a>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${sessionScope.usuarioLogado.id eq piec.aluno.id}">
                            <tr>
                                <td>
                                    <input type="file" name="arquivoHistoricoEscolar" id="arquivoHistoricoEscolar" style="margin: 0; display: inline-block;"/>
                                </td>
                                <td style="white-space: nowrap !important; text-align: right;" colspan="2">
                                    <a href="#" onclick="adicionarItem('Arquivo', 'piec');">
                                        <img src="${pageContext.request.contextPath}/resources/img/Plus.png"/>
                                    </a>
                                </td>
                            </tr>
                        </c:if>
                    </table>
                </fieldset>
            </c:if>
            <c:if test="${empty piec.disciplinas}">
                <input class="btn btn-default center-block" type="button" name="acao" value="Inserir disciplinas" onclick=" document.getElementById('inserir_disciplina').style.display= ''; this.style.display = 'none';"/>
            </c:if>
            <fieldset id="inserir_disciplina" style="width: 100%; <c:if test="${empty piec.disciplinas}">display: none;</c:if>">
                <legend style="color: #008289; border-bottom: 0;">Disciplinas</legend>
                <table style="width: 100%; margin-bottom: 0;" class="table">
                    <c:forEach items="${piec.piecDisciplinas}" var="piecDisciplina" varStatus="st">
                        <c:if test="${st.first}">
                            <tr class="active">
                                <th>Cod.</th>
                                <th>Nome</th>
                                <th style="text-align: right; white-space: nowrap;">Carga horária</th>
                                <th style="text-align: center;">Situação</th>
                                <th style="text-align: center;">Semestre/Ano de realização</th>
                                <th colspan="2">&nbsp;</th>
                            </tr>
                        </c:if>
                        <tr>
                            <td>${piecDisciplina.disciplina.codigo}</td>
                            <td style="white-space: nowrap;">${piecDisciplina.disciplina.nome}</td>
                            <td style="text-align: right;">${piecDisciplina.disciplina.cargaHoraria} horas</td>
                            <td style="text-align: center;">
                                <c:set var="dataAvaliacao"><fmt:formatDate value="${piecDisciplina.dataAprovacaoReprovacao}" pattern="dd/MM/yyyy"/></c:set>
                                <c:choose>
                                    <c:when test="${empty piecDisciplina.aprovada}">
                                        <c:set var="caminhoIcone" value="${pageContext.request.contextPath}/resources/img/atencao.png"/>
                                        <c:set var="titleImagem" value="Disciplina não julgada pelo colegiado"/>
                                    </c:when>
                                    <c:when test="${piecDisciplina.aprovada}">
                                        <c:set var="caminhoIcone" value="${pageContext.request.contextPath}/resources/img/aceito.png"/>
                                        <c:set var="titleImagem" value="Disciplina aprovada (${dataAvaliacao})"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="caminhoIcone" value="${pageContext.request.contextPath}/resources/img/excluir.png"/>
                                        <c:set var="titleImagem" value="Disciplina negada (${dataAvaliacao})"/>
                                    </c:otherwise>
                                </c:choose>
                                <c:if test="${not empty piecDisciplina.aprovada and sessionScope.usuarioLogado.membroColegiado and not empty piecDisciplina.usuarioAprovacao}">
                                    <c:set var="titleImagem" value="${titleImagem} (${piecDisciplina.usuarioAprovacao.login})"/>
                                </c:if>
                                <img id="avaliacao_${piecDisciplina.id}"  src="${caminhoIcone}" data-toggle="tooltip" data-placement="left" title="${titleImagem}" class="tooltip-class">
                            </td>
                            <td style="text-align: center;">
                                ${piecDisciplina.semestreAnoRealizacao}<c:if test="${empty piecDisciplina.semestreAnoRealizacao}"> - </c:if>
                            </td>
                            <td style="width: 1px; text-align: right; padding: 9px 1px;"  <c:if test="${sessionScope.usuarioLogado.id eq piec.aluno.id}">colspan="2" </c:if>>
                                <a class="tooltip-class" href="${pageContext.request.contextPath}/cadastro-piec-disciplina.htm?idPiecDisciplina=${piecDisciplina.id}" data-toggle="tooltip" data-placement="left" title="Editar disciplina"><img src="${pageContext.request.contextPath}/resources/img/Write.png"/></a>
                                <c:if test="${(empty piecDisciplina.aprovada or piecDisciplina.aprovada) and sessionScope.usuarioLogado.id eq piec.aluno.id}">
                                    <a href="${pageContext.request.contextPath}/cadastro-disciplina-piec-remover.htm?idPiecDisciplinaRemover=${piecDisciplina.id}">
                                        <img src="${pageContext.request.contextPath}/resources/img/Cancel.png" data-toggle="tooltip" data-placement="right" title="Remover disciplina" class="tooltip-class"/>
                                    </a>
                                </c:if>
                            </td>
                            <c:if test="${sessionScope.usuarioLogado.membroColegiado and piec.solicitarAvalacao}">
                                <td>
                                    <button type="button" class="btn btn-default btn-xs" data-toggle="modal" data-target="#avaliar_${piecDisciplina.id}">
                                        Avaliar
                                    </button>
                                    <div class="modal fade" id="avaliar_${piecDisciplina.id}" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Fechar</span></button>
                                                    <h4 class="modal-title" id="myModalLabel" style="color: #008289; font-weight: normal;">${piecDisciplina.disciplina.nome}</h4>
                                                </div>
                                                <div class="modal-body">
                                                    <table style="width: 100%; margin-bottom: 30px;">
                                                        <tr>
                                                            <td>
                                                                <label>Código</label>
                                                                <input class="form-control input-sm" style="width: auto;" disabled="true" value="${piecDisciplina.disciplina.codigo}"/>
                                                            </td>
                                                            <td>
                                                                <label>Nome</label>
                                                                <input class="form-control input-sm" disabled="true" value="${piecDisciplina.disciplina.nome}"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <label>Instituição</label>
                                                                <input class="form-control input-sm" style="width: auto;" disabled="true" value="${piecDisciplina.disciplina.instituicao.sigla}"/>
                                                            </td>
                                                            <td>
                                                                <label>Carga horária</label>
                                                                <input class="form-control input-sm"  style="width: auto;" disabled="true" value="${piecDisciplina.disciplina.cargaHoraria} horas"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <label>Semestre/Ano realização</label>
                                                                <input class="form-control input-sm" style="width: auto;" disabled="true" value="${piecDisciplina.semestreAnoRealizacao}"/>
                                                            </td>
                                                            <td>
                                                                <label>Curso ofertante</label>
                                                                <input class="form-control input-sm" style="width: auto;" value="${piecDisciplina.cursoOfertante}" disabled="true"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td colspan="2">
                                                                <c:if test="${not piecDisciplina.disciplina.preAprovada}">
                                                                    <label>Plano de ensino</label>
                                                                    <c:if test="${not empty piecDisciplina.planoEnsino}">
                                                                        <img class="tooltip-class" src="${pageContext.request.contextPath}/resources/img/Search.png" title="Ver arquivo" data-toggle="tooltip" data-placement="left" onclick="window.open('carregar-arquivo.htm?id=${piecDisciplina.planoEnsino.id}&abrir=true');"/>
                                                                    </c:if>
                                                                </c:if>
                                                            </td>
                                                        </tr>
                                                        <c:if test="${not piecDisciplina.disciplina.preAprovada}">
                                                            <tr>
                                                                <td colspan="2">
                                                                    <label>Relevancia da integralização</label>
                                                                    <textarea class="form-control input-sm" style="width: 100%;" disabled="true">${piecDisciplina.relevanciaIntegralizacao}</textarea>
                                                                </td>
                                                            </tr>
                                                        </c:if>
                                                    </table>
                                                </div>
                                                <div class="modal-footer">
                                                    <a href="${pageContext.request.contextPath}/cadastro-disciplina-piec-avaliar.htm?idPiecDisciplina=${piecDisciplina.id}&avaliacao=${true}" onclick="mudaImagemCampoBoolean(document.getElementById('avaliacao_${piecDisciplina.id}'), true, this, '${piecDisciplina.id}');">
                                                        <button type="button" class="btn btn-success">Aprovar</button>
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/cadastro-disciplina-piec-avaliar.htm?idPiecDisciplina=${piecDisciplina.id}&avaliacao=${false}" onclick="mudaImagemCampoBoolean(document.getElementById('avaliacao_${piecDisciplina.id}'), false, this, '${piecDisciplina.id}');">
                                                        <button type="button" class="btn btn-danger">Negar</button>
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </c:if>
                        <tr>
                    </c:forEach>
                    <c:if test="${not empty piec.piecDisciplinas}">
                        <tr>
                            <th colspan="2">Carga horária aprovada</th>
                            <th style="text-align: right; white-space: nowrap;">${piec.cargaHorariaDiciplinasAprovadas}</th>
                            <th colspan="3">&nbsp;</th>
                            <c:if test="${sessionScope.usuarioLogado.membroColegiado and piec.solicitarAvalacao or sessionScope.usuarioLogado.id eq piec.aluno.id}">
                                <th>&nbsp;</th>
                            </c:if>
                        </tr>
                    </c:if>
                    <c:if test="${sessionScope.usuarioLogado.id eq piec.aluno.id}">
                        <tr>
                            <td colspan="2" style="width: 60%;">
                                <form:select path="idDisciplinaAdicionar" cssStyle="width: 100% !important;" cssClass="form-control"
                                        onchange="mostraEscondeValor(this.value, ['-1'] , 'relevancia');
                                        mostraEscondeValor(this.value, ['-1'] , 'plano'); mostraEscondeValor(this.value, ['-1'] , 'nome');
                                        mostraEscondeValor(document.getElementById('p_' + this.value).value, ['false'] , 'plano');
                                        mostraEscondeValor(document.getElementById('p_' + this.value).value, ['false'] , 'relevancia');">
                                    <c:forEach items="${disciplinasAtivas}" var="disciplina">
                                        <form:option value="${disciplina.id}" label="${disciplina.codigo} - ${disciplina.nome} (${disciplina.cargaHoraria}h)"/>
                                    </c:forEach>
                                    <form:option value="-1" label="Adicionar outra disciplina" cssStyle="color: #949494;"/>
                                </form:select>
                                <c:forEach items="${disciplinasAtivas}" var="d">
                                    <input type="hidden" name="p_${d.id}" id="p_${d.id}" value="${d.preAprovada}"/>
                                </c:forEach>
                            </td>
                            <td colspan="2" style="width: 40%;">
                                <form:input path="piecDisciplinaAdicionar.cursoOfertante" placeholder="Curso ofertante" cssClass="form-control"/>
                            </td>
                            <td colspan="2">
                                <form:input path="piecDisciplinaAdicionar.semestreAnoRealizacao" cssClass="form-control" cssStyle="width: auto;" placeholder="Semestre/Ano"/>
                            </td>
                            <td style="text-align: right;" colspan="2">
                                <a href="#" class="tooltip-class" data-toggle="tooltip" data-placement="right" title="Adicionar disciplina no PIEC" onclick="adicionarItem('PiecDisciplina', 'piec');">
                                    <img src="${pageContext.request.contextPath}/resources/img/Plus.png"/>
                                </a>
                            </td>
                        </tr>
                    </table>
                    <table class="table">
                        <tr id="nome" style="display: none;">
                            <td>
                                <form:input path="novaDisciplina.codigo" class="form-control" placeholder="Código"/>
                            </td>
                            <td colspan="2" style="">
                                <form:input path="novaDisciplina.nome" class="form-control" placeholder="Nome"/>
                            </td>
                            <td>
                                <form:select path="novaDisciplina.idInstituicao" cssClass="form-control" cssStyle="width: auto;"
                                             onchange="mostraEscondeValor(this.value, ['-1'] , 'nova_instituicao');">
                                    <c:forEach items="${instituicoes}" var="instituicao">
                                        <form:option value="${instituicao.id}" label="${instituicao.nomeCompleto}"/>
                                    </c:forEach>
                                    <form:option value="-1" label="Adicionar outra instituição" cssStyle="color: #949494;"/>
                                </form:select>
                            </td>
                            <td style="width: 130px;" colspan="4">
                                <form:select path="novaDisciplina.cargaHoraria" class="form-control" cssStyle="width: 130px;">
                                    <c:forEach begin="10" end="200" varStatus="st">
                                        <form:option value="${st.index}" label="${st.index} horas"/>
                                    </c:forEach>
                                </form:select>
                            </td>
                        </tr>
                        <tr id="nova_instituicao" style="display: none;">
                            <td colspan="3"></td>
                            <td>
                                <form:input path="novaInstituicao.nome" class="form-control" placeholder="Nome instituição"/>
                            </td>
                            <td colspan="4">
                                <form:input path="novaInstituicao.sigla" class="form-control" placeholder="Sigla"/>
                            </td>
                        </tr>
                        <tr id="relevancia" style="display: none;">
                            <td colspan="8">
                                <form:textarea path="piecDisciplinaAdicionar.relevanciaIntegralizacao" cssClass="form-control"
                                               cssStyle="width: 100%;" placeholder="Relevância na integralização"  rows="${2+((fn:length(piec.piecDisciplinaAdicionar.relevanciaIntegralizacao)/350)*2)}"/>
                            </td>
                        </tr>
                        <tr id="plano" style="display: none;">
                            <td colspan="8">
                                <label>Plano de ensino</label>
                                <input type="file" id="piecDisciplinaAdicionar.arquivoPlanoEnsino" name="piecDisciplinaAdicionar.arquivoPlanoEnsino"/>
                            </td>
                        </tr>
                    </c:if>
                </table>
            </fieldset>
            <c:if test="${sessionScope.usuarioLogado.membroColegiado}">
                <table style="width: 100%; margin-top: 20px;">
                    <tr>
                        <td>
                            <form:label path="parecerRelator">Parecer do relator</form:label>
                            <form:textarea path="parecerRelator" cssClass="form-control" cssStyle="width: 100%;" rows="${2+((fn:length(piec.parecerRelator)/350)*2)}"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <form:label path="parecerColegiado">Parecer do colegiado</form:label>
                            <form:textarea path="parecerColegiado" cssClass="form-control" cssStyle="width: 100%;" rows="${2+((fn:length(piec.parecerColegiado)/350)*2)}"/>
                        </td>
                    </tr>
                </table>
            </c:if>
        </fieldset>
        <c:if test="${sessionScope.usuarioLogado.id eq piec.aluno.id and not empty piec.piecDisciplinas}">
            <input class="btn btn-success" type="submit" name="acao" value="Solicitar avaliação do plano ao colegiado do curso"/>
        </c:if>
        <c:if test="${sessionScope.usuarioLogado.id eq piec.aluno.id or sessionScope.usuarioLogado.membroColegiado and not piec.solicitarAvalacao}">
            <input class="btn btn-success" type="submit" name="acao" value="Salvar" style="float: right;"/>
        </c:if>
        <c:if test="${sessionScope.usuarioLogado.membroColegiado and piec.solicitarAvalacao}">
            <input class="btn btn-success" type="submit" name="acao" value="Aprovar" style="float: right;"/>
        </c:if>
    </form:form>
</div>
<script type="text/javascript">
    if (document.getElementById('idDisciplinaAdicionar') != null) {
        document.getElementById('idDisciplinaAdicionar').onchange();
    }
</script>