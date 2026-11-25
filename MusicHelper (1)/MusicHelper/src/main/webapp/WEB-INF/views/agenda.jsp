<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Agenda - MusicHelper</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/agenda.css">
</head>
<body>
<nav class="navbar">
    <div class="nav-left">
        <a href="${pageContext.request.contextPath}/painel"><i class="fas fa-arrow-left"></i> Voltar</a>
        <h2 style="margin-left: 10px;">Minha Agenda Musical</h2>
    </div>
</nav>
<div class="container">
    <div class="card">
        <h2>Agendar Compromisso</h2>
        <c:if test="${not empty mensagemSucesso}"><div class="alert alert-success"><c:out value="${mensagemSucesso}"/></div></c:if>
        <c:if test="${not empty mensagemErro}"><div class="alert alert-error"><c:out value="${mensagemErro}"/></div></c:if>
        <form action="${pageContext.request.contextPath}/agenda" method="POST">
            <div class="form-group"><label>Título do Evento</label>
                <input type="text" name="titulo" placeholder="Ex: Ensaio Geral, Show no Bar" required></div>
            <div class="form-group"><label>Tipo</label>
                <select name="tipo"><option value="Ensaio">Ensaio</option><option value="Apresentação">Apresentação</option></select></div>
            <div class="form-group"><label>Data</label>
                <input type="date" name="data_compromisso" min="${hoje}" required></div>
            <div class="form-group"><label>Hora de Início</label>
                <input type="time" name="hora_inicio" required></div>
            <div class="form-group"><label>Hora de Término</label>
                <input type="time" name="hora_fim"></div>
            <div class="form-group"><label>Local / Estúdio</label>
                <input type="text" name="local_compromisso" placeholder="Ex: Estúdio Central, Teatro" required></div>
            <div class="form-group"><label>Nº de Participantes</label>
                <input type="number" name="participantes" value="1" min="1"></div>
            <button type="submit" class="btn-adicionar">Adicionar à Agenda</button>
        </form>
    </div>

    <div class="card" style="min-height: 450px;">
        <h2>Próximos Compromissos</h2>
        <c:choose>
            <c:when test="${empty compromissos}">
                <p class="no-events">🎉 Nenhuma atividade agendada! Você está livre.</p>
            </c:when>
            <c:otherwise>
                <c:forEach var="c" items="${compromissos}">
                    <div class="event ${c.cor}">
                        <div class="event-header">
                            <div>
                                <h3><c:out value="${c.titulo}"/></h3>
                                <span class="tag"><c:out value="${c.tipo}"/></span>
                            </div>
                            <a href="${pageContext.request.contextPath}/agenda?deletar=${c.id}" class="btn-deletar-evento" onclick="return confirm('Deseja remover este compromisso?')"><i class="far fa-trash-alt"></i></a>
                        </div>
                        <div class="event-info">
                            <span><i class="far fa-calendar"></i> <c:out value="${c.dataFormatada}"/></span>
                            <span><i class="far fa-clock"></i> <c:out value="${c.inicioFormatado}"/><c:if test="${not empty c.fimFormatado}"> - <c:out value="${c.fimFormatado}"/></c:if></span>
                            <span><i class="fas fa-location-dot"></i> <c:out value="${c.local}"/></span>
                            <span><i class="fas fa-users"></i> ${c.participantes} integrante(s)</span>
                        </div>
                        <div style="margin-top: 12px; text-align: right;">
                            <a href="${pageContext.request.contextPath}/agenda?realizado=${c.id}" class="btn-realizado-evento" onclick="return confirm('Marcar este compromisso como realizado? Ele será movido para o Histórico.')"><i class="fas fa-check-circle"></i> Marcar como Realizado</a>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>
