package br.com.musichelper.servlet;

import br.com.musichelper.dao.UsuarioDAO;
import br.com.musichelper.model.Usuario;
import br.com.musichelper.util.Web;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UsuarioDAO dao = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s != null && s.getAttribute("usuarioId") != null) {
            resp.sendRedirect(req.getContextPath() + "/painel");
            return;
        }
        if ("1".equals(req.getParameter("sucesso"))) {
            req.setAttribute("mensagemSucesso", "Cadastro realizado com sucesso! Faça seu login.");
        }
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String email = Web.trim(req.getParameter("email"));
        String senha = req.getParameter("senha") == null ? "" : req.getParameter("senha");
        String erro = null;

        if (email.isEmpty() || senha.isEmpty()) {
            erro = "Por favor, preencha todos os campos!";
        } else {
            try {
                Usuario u = dao.buscarParaLogin(email);
                if (u == null) {
                    erro = "E-mail não cadastrado!";
                } else if (!senhaConfere(senha, u.getSenha())) {
                    erro = "Senha incorreta!";
                } else {
                    req.changeSessionId();
                    HttpSession s = req.getSession();
                    s.setAttribute("usuarioId", u.getId());
                    s.setAttribute("usuarioNome", u.getNome());
                    resp.sendRedirect(req.getContextPath() + "/painel");
                    return;
                }
            } catch (SQLException e) {
                erro = "Erro ao acessar o banco de dados: " + e.getMessage();
            }
        }
        req.setAttribute("mensagemErro", erro);
        req.setAttribute("emailDigitado", email);
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    /** Os hashes do PHP começam com $2y$; o jBCrypt espera $2a$ (o algoritmo é o mesmo). */
    private boolean senhaConfere(String senha, String hash) {
        try {
            if (hash.startsWith("$2y$")) {
                hash = "$2a$" + hash.substring(4);
            }
            return BCrypt.checkpw(senha, hash);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
