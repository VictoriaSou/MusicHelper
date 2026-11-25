<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Bloco de Notas - MusicHelper</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/notas.css">
</head>
<body>
<nav class="navbar">
    <div class="nav-left">
        <a href="${pageContext.request.contextPath}/painel"><i class="fas fa-arrow-left"></i> Voltar</a>
        <h2>Bloco de Notas</h2>
    </div>
</nav>
<div class="container">
    <div class="card-form">
        <h3>Nova Anotação</h3>
        <c:if test="${not empty mensagemSucesso}"><div class="alert alert-success"><c:out value="${mensagemSucesso}"/></div></c:if>
        <c:if test="${not empty mensagemErro}"><div class="alert alert-error"><c:out value="${mensagemErro}"/></div></c:if>
        <form action="${pageContext.request.contextPath}/notas" method="POST">
            <div class="form-group"><label>Título</label>
                <input type="text" name="titulo" placeholder="Ex: Ideia de Solo, Acordes da música..." required></div>
            <div class="form-group"><label>Categoria</label>
                <select name="categoria">
                    <option value="Ideia">💡 Ideia</option>
                    <option value="Lembrete">🔔 Lembrete</option>
                    <option value="Repertório">🎵 Repertório</option>
                </select></div>
            <div class="form-group"><label>Conteúdo</label>
                <textarea name="conteudo" placeholder="Escreva suas anotações aqui..." required></textarea></div>
            <button type="submit" class="btn-salvar">Salvar Nota</button>
        </form>
    </div>

    <div class="notes-grid">
        <c:choose>
            <c:when test="${empty notas}">
                <p style="color:#6b7280; grid-column: 1/-1; text-align:center; padding-top:40px;">Você ainda não criou nenhuma nota musical.</p>
            </c:when>
            <c:otherwise>
                <c:forEach var="n" items="${notas}">
                    <div class="note-card">
                        <div>
                            <div class="note-header"><div>
                                <div class="note-title"><c:out value="${n.titulo}"/></div>
                                <span class="badge ${n.badge}"><c:out value="${n.categoria}"/></span>
                            </div></div>
                            <div class="note-content"><c:out value="${n.conteudo}"/></div>
                        </div>
                        <div class="note-footer">
                            <span><i class="far fa-calendar-alt"></i> <c:out value="${n.dataFormatada}"/></span>
                            <a href="${pageContext.request.contextPath}/notas?deletar=${n.id}" class="btn-deletar" onclick="return confirm('Tem certeza que deseja excluir esta nota?')"><i class="far fa-trash-alt"></i></a>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>
