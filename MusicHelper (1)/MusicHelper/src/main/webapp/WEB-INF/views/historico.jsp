<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Histórico - MusicHelper</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/historico.css">
</head>
<body>
<nav class="navbar">
    <div class="nav-left">
        <a href="${pageContext.request.contextPath}/painel"><i class="fas fa-arrow-left"></i> Voltar</a>
        <h2 style="margin-left: 10px;">Histórico de Atividades</h2>
    </div>
</nav>
<div class="container">
    <div class="card">
        <h2>Suas atividades</h2>
        <c:if test="${not empty mensagemErro}"><div class="alert alert-error"><c:out value="${mensagemErro}"/></div></c:if>
        <c:choose>
            <c:when test="${empty atividades}"><p class="no-data">📜 Nenhuma atividade registrada ainda.</p></c:when>
            <c:otherwise>
                <c:forEach var="h" items="${atividades}">
                    <div class="item ${h.cor}">
                        <h3><c:out value="${h.titulo}"/></h3>
                        <p><c:out value="${h.descricao}"/></p>
                        <small><i class="far fa-clock"></i> <c:out value="${h.dataFormatada}"/> · <c:out value="${h.categoria}"/></small>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>
