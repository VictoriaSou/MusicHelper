<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>V-Music - Painel</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/painel.css">
</head>
<body>
<nav class="navbar">
    <a href="${pageContext.request.contextPath}/painel" class="logo"><i class="fas fa-music"></i><span>V-Music</span></a>
    <div class="nav-right">
        <a href="${pageContext.request.contextPath}/aviso" style="color:inherit; position: relative; margin-right: 5px;">
            <i class="far fa-bell"></i>
            <c:if test="${totalNotificacoes > 0}">
                <span style="position: absolute; top: -5px; right: -5px; background: #ef4444; color: white; font-size: 10px; width: 16px; height: 16px; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-weight: bold;">${totalNotificacoes}</span>
            </c:if>
        </a>
        <a href="${pageContext.request.contextPath}/logout" class="btn-sair"><i class="fas fa-sign-out-alt"></i> Sair</a>
    </div>
</nav>
<div class="container">
    <h1>Bem-vindo de volta, <c:out value="${empty sessionScope.usuarioNome ? 'Músico' : sessionScope.usuarioNome}"/>!</h1>
    <p class="subtitle">O que você gostaria de fazer hoje?</p>
    <div class="cards">
        <a href="${pageContext.request.contextPath}/agenda" class="card-link"><div class="card"><div class="icon blue"><i class="far fa-calendar"></i></div><h3>Agenda</h3><p>Organize seus ensaios e apresentações</p></div></a>
        <a href="${pageContext.request.contextPath}/historico" class="card-link"><div class="card"><div class="icon green"><i class="fas fa-history"></i></div><h3>Histórico</h3><p>Veja seu histórico de atividades</p></div></a>
        <a href="${pageContext.request.contextPath}/notas" class="card-link"><div class="card"><div class="icon orange"><i class="far fa-file-alt"></i></div><h3>Bloco de Notas</h3><p>Anote suas ideias musicais</p></div></a>
        <a href="${pageContext.request.contextPath}/musicas" class="card-link"><div class="card"><div class="icon purple"><i class="fas fa-music"></i></div><h3>Partituras e Cifras</h3><p>Importe e organize seus repertórios e links</p></div></a>
        <a href="${pageContext.request.contextPath}/perfil" class="card-link"><div class="card"><div class="icon violet"><i class="far fa-user"></i></div><h3>Perfil</h3><p>Gerencie suas informações e preferências</p></div></a>
    </div>
</div>
</body>
</html>
