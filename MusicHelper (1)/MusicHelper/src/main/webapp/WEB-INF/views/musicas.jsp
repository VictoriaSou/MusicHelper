<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Repertório - MusicHelper</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/musicas.css">
</head>
<body>
<nav class="navbar">
    <div class="nav-left">
        <a href="${pageContext.request.contextPath}/painel"><i class="fas fa-arrow-left"></i> Voltar</a>
        <h2 style="margin-left: 10px;">Partituras &amp; Cifras</h2>
    </div>
</nav>
<div class="container">
    <div class="card">
        <h2>Adicionar ao Repertório</h2>
        <c:if test="${not empty mensagemSucesso}"><div class="alert alert-success"><c:out value="${mensagemSucesso}"/></div></c:if>
        <c:if test="${not empty mensagemErro}"><div class="alert alert-error"><c:out value="${mensagemErro}"/></div></c:if>
        <form action="${pageContext.request.contextPath}/musicas" method="POST" enctype="multipart/form-data">
            <div class="form-group"><label>Nome da Música</label>
                <input type="text" name="titulo" placeholder="Ex: Littleroot Town" required></div>
            <div class="form-group"><label>Artista / Banda</label>
                <input type="text" name="artista" placeholder="Ex: Pokémon" required></div>
            <div class="form-group" style="display: grid; grid-template-columns: 1fr 1fr; gap: 10px;">
                <div><label>Tom Principal</label><input type="text" name="tom" placeholder="Ex: C, Am, G#m"></div>
                <div><label>Tipo de Arquivo</label>
                    <select name="tipo"><option value="Cifra">Cifra</option><option value="Partitura">Partitura</option><option value="Letra">Apenas Letra</option></select></div>
            </div>
            <div class="form-group"><label>Link Externo (YouTube, Drive, PDF)</label>
                <input type="url" name="link_externo" placeholder="https://exemplo.com/meu-pdf-ou-video"></div>
            <div class="form-group"><label>Arquivo (PDF, imagem ou documento)</label>
                <label class="file-input-label" id="fileLabel">
                    <i class="fas fa-cloud-upload-alt"></i>
                    <span id="fileLabelText">Clique para selecionar um arquivo (máx. 10MB)</span>
                    <input type="file" name="arquivo" id="arquivoInput" accept=".pdf,.jpg,.jpeg,.png,.gif,.doc,.docx" onchange="mostrarNomeArquivo(this)">
                </label>
                <div class="file-name-preview" id="fileNamePreview"></div></div>
            <div class="form-group"><label>Cifra, Dedilhado ou Letra (Texto)</label>
                <textarea name="anotacoes" rows="8" placeholder="Cole aqui o texto da cifra ou notas do instrumento..."></textarea></div>
            <button type="submit" class="btn-adicionar">Salvar Música</button>
        </form>
    </div>

    <div class="card" style="min-height: 500px;">
        <h2>Minhas Músicas</h2>
        <c:choose>
            <c:when test="${empty repertorio}"><p class="no-data">🎵 Nenhuma partitura ou cifra cadastrada ainda.</p></c:when>
            <c:otherwise>
                <c:forEach var="m" items="${repertorio}">
                    <div class="music-item">
                        <div class="music-header">
                            <div>
                                <span class="music-title">🎤 <c:out value="${m.titulo}"/></span>
                                <span style="color: #6b7280; font-size: 14px;"> - <c:out value="${m.artista}"/></span>
                            </div>
                            <a href="${pageContext.request.contextPath}/musicas?deletar=${m.id}" class="btn-deletar" onclick="return confirm('Excluir esta música do repertório?')"><i class="far fa-trash-alt"></i></a>
                        </div>
                        <div class="music-meta">
                            <span class="tag-tipo"><c:out value="${m.tipo}"/></span>
                            <c:if test="${not empty m.tom}"><span class="tag-tom">🎹 Tom: <c:out value="${m.tom}"/></span></c:if>
                            <c:if test="${not empty m.linkExterno}">
                                <a href="<c:out value='${m.linkExterno}'/>" target="_blank" rel="noopener" class="music-link"><i class="fas fa-external-link-alt"></i> Ver Link/PDF</a>
                            </c:if>
                            <c:if test="${not empty m.arquivoPath}">
                                <a href="${pageContext.request.contextPath}/arquivo/<c:out value='${m.arquivoPath}'/>" target="_blank" class="music-link"><i class="fas fa-paperclip"></i> Ver Arquivo Anexado</a>
                            </c:if>
                        </div>
                        <c:if test="${not empty m.anotacoes}"><div class="anotacoes-bloco"><c:out value="${m.anotacoes}"/></div></c:if>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</div>
<script>
function mostrarNomeArquivo(input) {
    const preview = document.getElementById('fileNamePreview');
    const labelText = document.getElementById('fileLabelText');
    if (input.files && input.files.length > 0) {
        preview.textContent = "📎 " + input.files[0].name;
        labelText.textContent = "Arquivo selecionado:";
    } else {
        preview.textContent = "";
        labelText.textContent = "Clique para selecionar um arquivo (máx. 10MB)";
    }
}
</script>
</body>
</html>
