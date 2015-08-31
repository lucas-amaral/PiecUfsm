<%@include file="cabecalho.jspf"%>
<%--@elvariable id="ex" type="java.lang.String"--%>
<%--@elvariable id="erro" type="java.lang.String"--%>
<%--@elvariable id="erros" type="java.util.Collection<java.lang.String>"--%>
<%--@elvariable id="sucesso" type="java.lang.String"--%>
<%--@elvariable id="atencao" type="java.lang.String"--%>
<%--@elvariable id="informacao" type="java.lang.String"--%>
<c:if test="${not empty erro or not empty erros}">
    <div class="alert alert-danger">
        <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Fechar</span></button>
        <c:choose>
            <c:when test="${not empty ex}">
                <h4 onclick="document.getElementById('exception').style.display = 'block';">Erro!</h4>
            </c:when>
            <c:otherwise>
                <h4>Erro!</h4>
            </c:otherwise>
        </c:choose>
        <c:if test="${not empty erro}">
            ${erro}
        </c:if>
        <c:if test="${not empty erros}">
            <c:forEach items="${erros}" var="err" varStatus="stErr">
                <c:if test="${not stErr.first or not empty erro}"><br/></c:if>${err}
            </c:forEach>
        </c:if>
    </div>
    <c:if test="${not empty ex}">
        <div id="exception" style="display: none;">
            <h4>Erro!</h4>
            <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Fechar</span></button>
            ${ex}
        </div>
    </c:if>
</c:if>
<c:if test="${not empty sucesso}">
    <div class="alert alert-success">
        <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Fechar</span></button>
        <h4>Sucesso!</h4>
        ${sucesso}
    </div>
</c:if>
<c:if test="${not empty atencao}">
    <div class="alert alert-warning">
        <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Fechar</span></button>
        <h4>Atenção!</h4>
        <c:out value="${atencao}" escapeXml="false"/>
    </div>
</c:if>
<c:if test="${not empty informacao}">
    <div class="alert alert-info">
        <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Fechar</span></button>
        <h4>Aviso!</h4>
        <c:out value="${informacao}" escapeXml="false"/>
    </div>
</c:if>