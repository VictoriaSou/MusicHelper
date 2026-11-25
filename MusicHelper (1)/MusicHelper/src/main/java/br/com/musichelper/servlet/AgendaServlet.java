package br.com.musichelper.servlet;

import br.com.musichelper.dao.CompromissoDAO;
import br.com.musichelper.model.Compromisso;
import br.com.musichelper.util.Web;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Collections;

@WebServlet("/agenda")
public class AgendaServlet extends HttpServlet {

    private final CompromissoDAO dao = new CompromissoDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int uid = Web.usuarioId(req);
        try {
            // Marcar como realizado -> move para o histórico
            String realizado = req.getParameter("realizado");
            if (realizado != null) {
                dao.concluir(Web.toInt(realizado, 0), uid);
                resp.sendRedirect(req.getContextPath() + "/historico");
                return;
            }
            // Exclusão
            String deletar = req.getParameter("deletar");
            if (deletar != null) {
                if (dao.excluir(Web.toInt(deletar, 0), uid)) {
                    Web.sucesso(req, "Compromisso removido com sucesso!");
                }
                resp.sendRedirect(req.getContextPath() + "/agenda");
                return;
            }
        } catch (SQLException e) {
            Web.erro(req, "Erro ao acessar o banco de dados.");
            resp.sendRedirect(req.getContextPath() + "/agenda");
            return;
        }
        mostrar(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int uid = Web.usuarioId(req);

        String titulo = Web.trim(req.getParameter("titulo"));
        String tipo = Web.trim(req.getParameter("tipo"));
        String data = Web.trim(req.getParameter("data_compromisso"));
        String inicio = Web.trim(req.getParameter("hora_inicio"));
        String fim = Web.trim(req.getParameter("hora_fim"));
        String local = Web.trim(req.getParameter("local_compromisso"));
        int participantes = Math.max(1, Web.toInt(req.getParameter("participantes"), 1));

        if (titulo.isEmpty() || data.isEmpty() || inicio.isEmpty() || local.isEmpty()) {
            Web.erro(req, "Por favor, preencha todos os campos obrigatórios!");
        } else {
            try {
                Compromisso c = new Compromisso();
                c.setUsuarioId(uid);
                c.setTitulo(titulo);
                c.setTipo(tipo.isEmpty() ? "Ensaio" : tipo);
                c.setData(LocalDate.parse(data));
                c.setHoraInicio(LocalTime.parse(inicio));
                c.setHoraFim(fim.isEmpty() ? null : LocalTime.parse(fim));
                c.setLocal(local);
                c.setParticipantes(participantes);

                if (c.getData().isBefore(LocalDate.now())) {
                    Web.erro(req, "Não é possível agendar compromissos em datas anteriores ao dia de hoje!");
                } else {
                    dao.agendar(c);
                    Web.sucesso(req, "Compromisso agendado com sucesso!");
                }
            } catch (DateTimeParseException e) {
                Web.erro(req, "Data ou hora inválida!");
            } catch (SQLException e) {
                Web.erro(req, "Erro ao agendar o compromisso. Tente novamente.");
            }
        }
        resp.sendRedirect(req.getContextPath() + "/agenda");
    }

    private void mostrar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Web.carregarFlash(req);
        try {
            req.setAttribute("compromissos", dao.listar(Web.usuarioId(req)));
        } catch (SQLException e) {
            req.setAttribute("compromissos", Collections.emptyList());
            req.setAttribute("mensagemErro", "Erro ao carregar a agenda.");
        }
        req.setAttribute("hoje", LocalDate.now().toString());
        req.getRequestDispatcher("/WEB-INF/views/agenda.jsp").forward(req, resp);
    }
}
