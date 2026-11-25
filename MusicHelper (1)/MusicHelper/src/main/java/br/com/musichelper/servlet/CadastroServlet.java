package br.com.musichelper.servlet;

import br.com.musichelper.dao.UsuarioDAO;
import br.com.musichelper.util.Web;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/cadastro")
public class CadastroServlet extends HttpServlet {

    private final UsuarioDAO dao = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/cadastro.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String nome = Web.trim(req.getParameter("nome"));
        String email = Web.trim(req.getParameter("email"));
        String senha = req.getParameter("senha") == null ? "" : req.getParameter("senha");
        String confirmar = req.getParameter("confirmar_senha") == null ? "" : req.getParameter("confirmar_senha");
        String erro = null;

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmar.isEmpty()) {
            erro = "Por favor, preencha todos os campos!";
        } else if (!senha.equals(confirmar)) {
            erro = "As senhas não coincidem!";
        } else if (senha.length() < 6) {
            erro = "A senha deve ter no mínimo 6 caracteres!";
        } else {
            try {
                if (dao.emailExiste(email)) {
                    erro = "Este e-mail já está cadastrado!";
                } else {
                    dao.cadastrar(nome, email, BCrypt.hashpw(senha, BCrypt.gensalt(10)));
                    resp.sendRedirect(req.getContextPath() + "/login?sucesso=1");
                    return;
                }
            } catch (SQLException e) {
                erro = "Erro ao processar o cadastro. Tente novamente.";
            }
        }
        req.setAttribute("mensagemErro", erro);
        req.setAttribute("nomeDigitado", nome);
        req.setAttribute("emailDigitado", email);
        req.getRequestDispatcher("/WEB-INF/views/cadastro.jsp").forward(req, resp);
    }
}
