package br.com.musichelper.servlet;

import br.com.musichelper.dao.UsuarioDAO;
import br.com.musichelper.model.Usuario;
import br.com.musichelper.util.Uploads;
import br.com.musichelper.util.Web;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/perfil")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024, maxRequestSize = 7 * 1024 * 1024)
public class PerfilServlet extends HttpServlet {

    private static final List<String> EXTENSOES = List.of("jpg", "jpeg", "png", "gif", "webp");
    private final UsuarioDAO dao = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Web.carregarFlash(req);
        try {
            req.setAttribute("usuario", dao.buscarPorId(Web.usuarioId(req)));
        } catch (SQLException e) {
            req.setAttribute("mensagemErro", "Erro ao carregar o perfil.");
        }
        req.getRequestDispatcher("/WEB-INF/views/perfil.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int uid = Web.usuarioId(req);

        try {
            // Excluir conta
            if (req.getParameter("excluir_conta") != null) {
                dao.excluir(uid);
                req.getSession().invalidate();
                resp.sendRedirect(req.getContextPath() + "/login");
                return;
            }

            // Salvar perfil
            Usuario atual = dao.buscarPorId(uid);
            String nome = Web.trim(req.getParameter("nome"));
            String foto = atual.getFotoPerfil();
            String erro = null;

            try {
                Part p = req.getPart("foto_perfil");
                if (Uploads.temArquivo(p)) {
                    if (!EXTENSOES.contains(Uploads.extensao(p))) {
                        erro = "Formato de imagem não permitido! Use JPG, PNG, GIF ou WEBP.";
                    } else if (p.getSize() > 5 * 1024 * 1024) {
                        erro = "A imagem é muito grande! O tamanho máximo permitido é 5MB.";
                    } else {
                        foto = Uploads.salvar(p, "perfil", "perfil", uid);
                    }
                }
            } catch (IllegalStateException e) {
                erro = "A imagem é muito grande! O tamanho máximo permitido é 5MB.";
            }

            if (erro == null && nome.isEmpty()) {
                erro = "O campo Nome Completo não pode ficar vazio!";
            }

            if (erro != null) {
                Web.erro(req, erro);
            } else {
                Usuario u = new Usuario();
                u.setId(uid);
                u.setNome(nome);
                u.setInstrumentos(Web.trim(req.getParameter("instrumentos")));
                u.setExperiencia(Web.trim(req.getParameter("experiencia")));
                u.setBanda(Web.trim(req.getParameter("banda")));
                u.setRepertorio(Web.trim(req.getParameter("repertorio")));
                u.setBiografia(Web.trim(req.getParameter("biografia")));
                u.setFotoPerfil(foto);
                dao.atualizar(u);
                req.getSession().setAttribute("usuarioNome", nome);
                Web.sucesso(req, "Perfil atualizado com sucesso!");
            }
        } catch (SQLException e) {
            Web.erro(req, "Erro ao atualizar o perfil no banco de dados.");
        }
        resp.sendRedirect(req.getContextPath() + "/perfil");
    }
}
