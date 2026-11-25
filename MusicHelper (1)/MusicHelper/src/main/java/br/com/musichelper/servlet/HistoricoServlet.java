package br.com.musichelper.servlet;

import br.com.musichelper.dao.HistoricoDAO;
import br.com.musichelper.util.Web;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;

@WebServlet("/historico")
public class HistoricoServlet extends HttpServlet {

    private final HistoricoDAO dao = new HistoricoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("atividades", dao.listar(Web.usuarioId(req)));
        } catch (SQLException e) {
            req.setAttribute("atividades", Collections.emptyList());
            req.setAttribute("mensagemErro", "Erro ao carregar o histórico.");
        }
        req.getRequestDispatcher("/WEB-INF/views/historico.jsp").forward(req, resp);
    }
}
