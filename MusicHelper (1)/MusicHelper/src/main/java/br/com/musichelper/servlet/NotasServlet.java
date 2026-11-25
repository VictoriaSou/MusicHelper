package br.com.musichelper.servlet;

import br.com.musichelper.dao.NotaDAO;
import br.com.musichelper.model.Nota;
import br.com.musichelper.util.Web;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;

@WebServlet("/notas")
public class NotasServlet extends HttpServlet {

    private final NotaDAO dao = new NotaDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int uid = Web.usuarioId(req);
        String deletar = req.getParameter("deletar");
        if (deletar != null) {
            try {
                if (dao.excluir(Web.toInt(deletar, 0), uid)) {
                    Web.sucesso(req, "Nota excluída com sucesso!");
                }
            } catch (SQLException e) {
                Web.erro(req, "Erro ao excluir a nota.");
            }
            resp.sendRedirect(req.getContextPath() + "/notas");
            return;
        }
        Web.carregarFlash(req);
        try {
            req.setAttribute("notas", dao.listar(uid));
        } catch (SQLException e) {
            req.setAttribute("notas", Collections.emptyList());
            req.setAttribute("mensagemErro", "Erro ao carregar as notas.");
        }
        req.getRequestDispatcher("/WEB-INF/views/notas.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String titulo = Web.trim(req.getParameter("titulo"));
        String categoria = Web.trim(req.getParameter("categoria"));
        String conteudo = Web.trim(req.getParameter("conteudo"));

        if (titulo.isEmpty() || conteudo.isEmpty()) {
            Web.erro(req, "Por favor, preencha o título e o conteúdo da nota!");
        } else {
            Nota n = new Nota();
            n.setUsuarioId(Web.usuarioId(req));
            n.setTitulo(titulo);
            n.setCategoria(categoria.isEmpty() ? "Ideia" : categoria);
            n.setConteudo(conteudo);
            try {
                dao.inserir(n);
                Web.sucesso(req, "Nota salva com sucesso!");
            } catch (SQLException e) {
                Web.erro(req, "Erro ao salvar a nota. Tente novamente.");
            }
        }
        resp.sendRedirect(req.getContextPath() + "/notas");
    }
}
