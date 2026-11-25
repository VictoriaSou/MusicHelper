package br.com.musichelper.servlet;

import br.com.musichelper.dao.CompromissoDAO;
import br.com.musichelper.util.Web;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/painel")
public class PainelServlet extends HttpServlet {

    private final CompromissoDAO dao = new CompromissoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("totalNotificacoes", dao.contarAvisosDeHoje(Web.usuarioId(req)));
        } catch (SQLException e) {
            req.setAttribute("totalNotificacoes", 0);
        }
        req.getRequestDispatcher("/WEB-INF/views/painel.jsp").forward(req, resp);
    }
}
