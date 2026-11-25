package br.com.musichelper.servlet;

import br.com.musichelper.dao.CompromissoDAO;
import br.com.musichelper.util.Web;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;

@WebServlet("/aviso")
public class AvisoServlet extends HttpServlet {

    private final CompromissoDAO dao = new CompromissoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int uid = Web.usuarioId(req);
        try {
            String vista = req.getParameter("marcar_vista");
            if (vista != null) {
                dao.marcarVista(Web.toInt(vista, 0), uid);
                resp.sendRedirect(req.getContextPath() + "/aviso");
                return;
            }
            req.setAttribute("notificacoes", dao.avisosDeHoje(uid));
        } catch (SQLException e) {
            req.setAttribute("notificacoes", Collections.emptyList());
            req.setAttribute("mensagemErro", "Erro ao carregar os avisos.");
        }
        req.getRequestDispatcher("/WEB-INF/views/aviso.jsp").forward(req, resp);
    }
}
