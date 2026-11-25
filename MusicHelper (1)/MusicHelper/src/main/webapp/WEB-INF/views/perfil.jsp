<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Meu Perfil - MusicHelper</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/perfil.css">
</head>
<body>
<div class="profile-card">
    <a href="${pageContext.request.contextPath}/painel" style="color: #6b7280; text-decoration: none; font-size: 14px; display: inline-block; margin-bottom: 15px;"><i class="fas fa-arrow-left"></i> Voltar ao Painel</a>

    <div class="avatar-area">
        <div class="avatar-icon">
            <c:choose>
                <c:when test="${not empty usuario.fotoPerfil}"><img src="${pageContext.request.contextPath}/arquivo/<c:out value='${usuario.fotoPerfil}'/>" alt="Foto de perfil"></c:when>
                <c:otherwise><i class="fas fa-sliders-h"></i></c:otherwise>
            </c:choose>
        </div>
        <h2><c:out value="${usuario.nome}"/></h2>
        <p><c:out value="${usuario.email}"/></p>
    </div>
    <c:if test="${not empty mensagemSucesso}"><div class="alert alert-success"><c:out value="${mensagemSucesso}"/></div></c:if>
        <c:if test="${not empty mensagemErro}"><div class="alert alert-error"><c:out value="${mensagemErro}"/></div></c:if>

    <div id="viewMode">
        <div class="info-group"><label>Nome Completo</label><div class="fixed-text"><c:out value="${usuario.nome}"/></div></div>
        <div class="info-group"><label>E-mail (Identificador da conta)</label><div class="fixed-text"><c:out value="${usuario.email}"/></div></div>
        <div class="info-group"><label>Instrumentos que Toca</label><div class="fixed-text"><c:out value="${empty usuario.instrumentos ? 'Não informado' : usuario.instrumentos}"/></div></div>
        <div class="info-group"><label>Tempo de Experiência</label><div class="fixed-text"><c:out value="${empty usuario.experiencia ? 'Não informado' : usuario.experiencia}"/></div></div>
        <div class="info-group"><label>Banda ou Grupo Musical</label><div class="fixed-text"><c:out value="${empty usuario.banda ? 'Nenhuma banda vinculada' : usuario.banda}"/></div></div>
        <div class="info-group"><label>Repertório Musical (Músicas principais)</label><div class="fixed-text textarea-box"><c:out value="${empty usuario.repertorio ? 'Nenhuma música listada' : usuario.repertorio}"/></div></div>
        <div class="info-group"><label>Biografia / Trajetória</label><div class="fixed-text textarea-box"><c:out value="${empty usuario.biografia ? 'Nenhuma biografia adicionada' : usuario.biografia}"/></div></div>

        <div class="btn-container">
            <button type="button" class="btn btn-purple" onclick="habilitarEdicao()"><i class="far fa-edit"></i> Alterar Informações do Perfil</button>
            <form action="${pageContext.request.contextPath}/perfil" method="POST" onsubmit="return confirm('ATENÇÃO: Tem certeza absoluta que deseja EXCLUIR sua conta? Todos os seus dados serão apagados permanentemente!')">
                <button type="submit" name="excluir_conta" value="1" class="btn btn-danger"><i class="far fa-trash-alt"></i> Excluir a Conta</button>
            </form>
        </div>
    </div>

    <form id="editMode" class="modo-edicao" action="${pageContext.request.contextPath}/perfil" method="POST" enctype="multipart/form-data">
        <div class="avatar-upload-wrapper">
            <div class="avatar-icon" id="previewAvatar">
                <c:choose>
                    <c:when test="${not empty usuario.fotoPerfil}"><img src="${pageContext.request.contextPath}/arquivo/<c:out value='${usuario.fotoPerfil}'/>" alt="Foto de perfil"></c:when>
                    <c:otherwise><i class="fas fa-sliders-h"></i></c:otherwise>
                </c:choose>
            </div>
            <label class="avatar-edit-btn" for="fotoPerfilInput" title="Alterar foto"><i class="fas fa-camera"></i></label>
            <input type="file" name="foto_perfil" id="fotoPerfilInput" accept="image/*" onchange="pretextoFoto(this)">
        </div>
        <div class="info-group"><label>Nome Completo</label>
            <div class="input-box"><i class="far fa-user"></i><input type="text" name="nome" value="<c:out value='${usuario.nome}'/>" required></div></div>
        <div class="info-group"><label>E-mail (Não alterável)</label>
            <div class="input-box"><i class="far fa-envelope"></i><input type="email" value="<c:out value='${usuario.email}'/>" disabled style="background: #f3f4f6; color: #9ca3af;"></div></div>
        <div class="info-group"><label>Instrumentos que Toca</label>
            <div class="input-box"><i class="fas fa-guitar"></i><input type="text" name="instrumentos" value="<c:out value='${usuario.instrumentos}'/>" placeholder="Ex: Guitarra, Violão"></div></div>
        <div class="info-group"><label>Tempo de Experiência</label>
            <div class="input-box"><i class="fas fa-user-clock"></i><input type="text" name="experiencia" value="<c:out value='${usuario.experiencia}'/>" placeholder="Ex: 5 anos"></div></div>
        <div class="info-group"><label>Banda ou Grupo Musical</label>
            <div class="input-box"><i class="fas fa-users"></i><input type="text" name="banda" value="<c:out value='${usuario.banda}'/>" placeholder="Ex: Nome da Banda, Solo, Orquestra"></div></div>
        <div class="info-group"><label>Repertório Musical (Músicas principais)</label>
            <textarea name="repertorio" rows="4" placeholder="Digite as músicas do seu setlist ou repertório (uma por linha)..."><c:out value="${usuario.repertorio}"/></textarea></div>
        <div class="info-group"><label>Biografia / Trajetória</label>
            <textarea name="biografia" rows="4" placeholder="Conte um pouco sobre suas influências e história na música..."><c:out value="${usuario.biografia}"/></textarea></div>
        <div class="btn-container">
            <button type="submit" name="salvar_perfil" value="1" class="btn btn-purple"><i class="fas fa-save"></i> Salvar Informações</button>
            <button type="button" class="btn btn-outline" onclick="cancelarEdicao()">Cancelar</button>
        </div>
    </form>
</div>
<script>
function habilitarEdicao() { document.getElementById('viewMode').style.display = 'none'; document.getElementById('editMode').style.display = 'block'; }
function cancelarEdicao() { document.getElementById('editMode').style.display = 'none'; document.getElementById('viewMode').style.display = 'block'; }
function pretextoFoto(input) {
    if (input.files && input.files[0]) {
        const reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('previewAvatar').innerHTML = '<img src="' + e.target.result + '" alt="Pré-visualização da foto">';
        };
        reader.readAsDataURL(input.files[0]);
    }
}
</script>
</body>
</html>
