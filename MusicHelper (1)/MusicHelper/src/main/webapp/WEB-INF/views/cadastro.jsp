<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Criar Conta - MusicHelper</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/cadastro.css">
</head>
<body>
<div class="card">
    <div class="header">
        <div class="logo"><i class="fas fa-music"></i></div>
        <h2>Criar Conta</h2>
        <p>Comece sua jornada musical</p>
    </div>
    <c:if test="${not empty mensagemErro}"><div class="alert-error"><c:out value="${mensagemErro}"/></div></c:if>
    <form action="${pageContext.request.contextPath}/cadastro" method="POST">
        <div class="form-group"><label>Nome</label>
            <div class="input-box"><i class="far fa-user"></i>
                <input type="text" name="nome" placeholder="Seu nome completo" value="<c:out value='${nomeDigitado}'/>" required></div></div>
        <div class="form-group"><label>Email</label>
            <div class="input-box"><i class="far fa-envelope"></i>
                <input type="email" name="email" placeholder="seu@email.com" value="<c:out value='${emailDigitado}'/>" required></div></div>
        <div class="form-group"><label>Senha</label>
            <div class="input-box"><i class="fas fa-lock"></i>
                <input type="password" name="senha" placeholder="••••••••" required></div></div>
        <div class="form-group"><label>Confirmar Senha</label>
            <div class="input-box"><i class="fas fa-lock"></i>
                <input type="password" name="confirmar_senha" placeholder="••••••••" required></div></div>
        <button type="submit" class="btn">Cadastrar</button>
    </form>
    <div class="footer">Já tem uma conta? <a href="${pageContext.request.contextPath}/login">Faça login</a></div>
</div>
</body>
</html>
