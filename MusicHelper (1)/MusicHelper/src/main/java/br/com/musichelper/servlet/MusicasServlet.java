package br.com.musichelper.servlet;

import br.com.musichelper.dao.MusicaDAO;
import br.com.musichelper.model.Musica;
import br.com.musichelper.util.Uploads;
import br.com.musichelper.util.Web;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@WebServlet("/musicas")
@MultipartConfig(maxFileSize = 10 * 1024 * 1024, maxRequestSize = 12 * 1024 * 1024)
public class MusicasServlet extends HttpServlet {

    private static final List<String> EXTENSOES = List.of("pdf", "jpg", "jpeg", "png", "gif", "doc", "docx");
    private final MusicaDAO dao = new MusicaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int uid = Web.usuarioId(req);
        String deletar = req.getParameter("deletar");
        if (deletar != null) {
            try {
                if (dao.excluir(Web.toInt(deletar, 0), uid)) {
                    Web.sucesso(req, "Música removida com sucesso!");
                }
            } catch (SQLException e) {
                Web.erro(req, "Erro ao remover a música.");
            }
            resp.sendRedirect(req.getContextPath() + "/musicas");
            return;
        }
        Web.carregarFlash(req);
        try {
            req.setAttribute("repertorio", dao.listar(uid));
        } catch (SQLException e) {
            req.setAttribute("repertorio", Collections.emptyList());
            req.setAttribute("mensagemErro", "Erro ao carregar o repertório.");
        }
        req.getRequestDispatcher("/WEB-INF/views/musicas.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int uid = Web.usuarioId(req);

        String titulo = Web.trim(req.getParameter("titulo"));
        String artista = Web.trim(req.getParameter("artista"));
        String tipo = Web.trim(req.getParameter("tipo"));
        String arquivoPath = null;
        String erro = null;

        try {
            Part arquivo = req.getPart("arquivo");
            if (Uploads.temArquivo(arquivo)) {
                if (!EXTENSOES.contains(Uploads.extensao(arquivo))) {
                    erro = "Formato de arquivo não permitido! Envie PDF, imagem (jpg, png, gif) ou documento Word (doc, docx).";
                } else if (arquivo.getSize() > 10 * 1024 * 1024) {
                    erro = "O arquivo enviado é muito grande! O tamanho máximo permitido é 10MB.";
                } else {
                    arquivoPath = Uploads.salvar(arquivo, "musicas", "musica", uid);
                }
            }
        } catch (IllegalStateException e) {
            erro = "O arquivo enviado é muito grande! O tamanho máximo permitido é 10MB.";
        }

        if (erro == null && (titulo.isEmpty() || artista.isEmpty() || tipo.isEmpty())) {
            erro = "Por favor, preencha o Título, o Artista e o Tipo!";
        }

        if (erro != null) {
            Web.erro(req, erro);
        } else {
            Musica m = new Musica();
            m.setUsuarioId(uid);
            m.setTitulo(titulo);
            m.setArtista(artista);
            m.setTom(Web.trim(req.getParameter("tom")));
            m.setTipo(tipo);
            m.setLinkExterno(Web.trim(req.getParameter("link_externo")));
            m.setAnotacoes(Web.trim(req.getParameter("anotacoes")));
            m.setArquivoPath(arquivoPath);
            try {
                dao.inserir(m);
                Web.sucesso(req, "Música adicionada ao seu repertório!");
            } catch (SQLException e) {
                Web.erro(req, "Erro ao salvar a música. Tente novamente.");
            }
        }
        resp.sendRedirect(req.getContextPath() + "/musicas");
    }
}
