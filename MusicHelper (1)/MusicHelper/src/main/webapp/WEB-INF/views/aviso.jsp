<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Notificações - MusicHelper</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/aviso.css">
</head>
<body>
<div class="container">
    <a href="${pageContext.request.contextPath}/painel" class="btn-voltar"><i class="fas fa-arrow-left"></i> Voltar ao Painel</a>
    <h1><i class="fas fa-bell"></i> Central de Avisos</h1>
    <c:choose>
        <c:when test="${empty notificacoes}">
            <p class="no-notif">🔔 Tudo limpo por aqui! Nenhuma notificação pendente para hoje.</p>
        </c:when>
        <c:otherwise>
            <c:forEach var="n" items="${notificacoes}">
                <div class="notif-item">
                    <div class="notif-content">
                        <p class="notif-title">Hoje tem <c:out value="${n.tipo}"/>: <c:out value="${n.titulo}"/></p>
                        <p class="notif-time"><i class="far fa-clock"></i> Início programado para as <c:out value="${n.inicioFormatado}"/></p>
                    </div>
                    <a href="${pageContext.request.contextPath}/aviso?marcar_vista=${n.id}" class="btn-check" title="Marcar como lida"><i class="fas fa-check"></i></a>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
