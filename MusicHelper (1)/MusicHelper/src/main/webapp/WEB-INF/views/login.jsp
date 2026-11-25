<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Login - MusicHelper</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
<div class="login-card">
    <div class="logo-area">
        <div class="logo"><i class="fas fa-music"></i></div>
        <h2>MusicHelper</h2>
        <p>Seu assistente musical pessoal</p>
    </div>
    <c:if test="${not empty mensagemErro}"><div class="alert-error"><c:out value="${mensagemErro}"/></div></c:if>
    <c:if test="${not empty mensagemSucesso}"><div class="alert-success"><c:out value="${mensagemSucesso}"/></div></c:if>
    <form action="${pageContext.request.contextPath}/login" method="POST">
        <div class="form-group">
            <label>Email</label>
            <div class="input-box"><i class="far fa-envelope"></i>
                <input type="email" name="email" placeholder="seu@email.com" value="<c:out value='${emailDigitado}'/>" required></div>
        </div>
        <div class="form-group">
            <label>Senha</label>
            <div class="input-box"><i class="fas fa-lock"></i>
                <input type="password" name="senha" placeholder="••••••••" required></div>
        </div>
        <button type="submit" class="btn-login">Entrar</button>
    </form>
    <div class="footer">Não tem uma conta? <a href="${pageContext.request.contextPath}/cadastro">Cadastre-se</a></div>
</div>
</body>
</html>
