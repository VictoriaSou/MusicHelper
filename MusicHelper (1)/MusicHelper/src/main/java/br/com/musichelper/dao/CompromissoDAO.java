package br.com.musichelper.dao;

import br.com.musichelper.model.Compromisso;
import br.com.musichelper.util.Conexao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CompromissoDAO {

    private static final String COLUNAS =
            "id, usuario_id, titulo, tipo, data_compromisso, hora_inicio, hora_fim, local_compromisso, participantes";

    private Compromisso mapear(ResultSet rs) throws SQLException {
        Compromisso c = new Compromisso();
        c.setId(rs.getInt("id"));
        c.setUsuarioId(rs.getInt("usuario_id"));
        c.setTitulo(rs.getString("titulo"));
        c.setTipo(rs.getString("tipo"));
        c.setData(rs.getDate("data_compromisso").toLocalDate());
        c.setHoraInicio(rs.getTime("hora_inicio").toLocalTime());
        Time fim = rs.getTime("hora_fim");
        c.setHoraFim(fim == null ? null : fim.toLocalTime());
        c.setLocal(rs.getString("local_compromisso"));
        c.setParticipantes(rs.getInt("participantes"));
        return c;
    }

    public List<Compromisso> listar(int usuarioId) throws SQLException {
        String sql = "SELECT " + COLUNAS + " FROM compromissos WHERE usuario_id = ? "
                + "ORDER BY data_compromisso ASC, hora_inicio ASC";
        List<Compromisso> lista = new ArrayList<>();
        try (Connection con = Conexao.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    /** Insere o compromisso e registra no histórico, na mesma transação. */
    public void agendar(Compromisso c) throws SQLException {
        String sql = "INSERT INTO compromissos (usuario_id, titulo, tipo, data_compromisso, hora_inicio, "
                + "hora_fim, local_compromisso, participantes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexao.getConnection()) {
            con.setAutoCommit(false);
            try {
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, c.getUsuarioId());
                    ps.setString(2, c.getTitulo());
                    ps.setString(3, c.getTipo());
                    ps.setDate(4, Date.valueOf(c.getData()));
                    ps.setTime(5, Time.valueOf(c.getHoraInicio()));
                    ps.setTime(6, Time.valueOf(c.getHoraFim() == null ? LocalTime.MIDNIGHT : c.getHoraFim()));
                    ps.setString(7, c.getLocal());
                    ps.setInt(8, c.getParticipantes());
                    ps.executeUpdate();
                }
                String desc = "Agendou o compromisso '" + c.getTitulo() + "' para o dia " + c.getDataFormatada()
                        + " às " + c.getHoraInicio().format(DateTimeFormatter.ofPattern("HH:mm"))
                        + " em '" + c.getLocal() + "'.";
                HistoricoDAO.registrar(con, c.getUsuarioId(), "Agenda: Novo " + c.getTipo(), desc, "Agenda");
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    public boolean excluir(int id, int usuarioId) throws SQLException {
        String sql = "DELETE FROM compromissos WHERE id = ? AND usuario_id = ?";
        try (Connection con = Conexao.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, usuarioId);
            return ps.executeUpdate() > 0;
        }
    }

    /** Move o compromisso para o histórico (registra a conclusão e remove da agenda). */
    public boolean concluir(int id, int usuarioId) throws SQLException {
        try (Connection con = Conexao.getConnection()) {
            con.setAutoCommit(false);
            try {
                Compromisso c = null;
                String sqlBusca = "SELECT " + COLUNAS + " FROM compromissos WHERE id = ? AND usuario_id = ?";
                try (PreparedStatement ps = con.prepareStatement(sqlBusca)) {
                    ps.setInt(1, id);
                    ps.setInt(2, usuarioId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            c = mapear(rs);
                        }
                    }
                }
                if (c == null) {
                    con.rollback();
                    return false;
                }
                String desc = "O compromisso '" + c.getTitulo() + "' em '" + c.getLocal()
                        + "' (dia " + c.getDataFormatada() + ") foi concluído.";
                HistoricoDAO.registrar(con, usuarioId, c.getTipo() + " realizado(a)", desc, "Agenda");
                try (PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM compromissos WHERE id = ? AND usuario_id = ?")) {
                    ps.setInt(1, id);
                    ps.setInt(2, usuarioId);
                    ps.executeUpdate();
                }
                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    // ---- Avisos (compromissos de hoje ainda não vistos) ----

    public List<Compromisso> avisosDeHoje(int usuarioId) throws SQLException {
        String sql = "SELECT " + COLUNAS + " FROM compromissos "
                + "WHERE usuario_id = ? AND data_compromisso = ? AND notificacao_vista = 0 "
                + "ORDER BY hora_inicio ASC";
        List<Compromisso> lista = new ArrayList<>();
        try (Connection con = Conexao.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    public int contarAvisosDeHoje(int usuarioId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM compromissos "
                + "WHERE usuario_id = ? AND data_compromisso = ? AND notificacao_vista = 0";
        try (Connection con = Conexao.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setDate(2, Date.valueOf(LocalDate.now()));
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    public void marcarVista(int id, int usuarioId) throws SQLException {
        String sql = "UPDATE compromissos SET notificacao_vista = 1 WHERE id = ? AND usuario_id = ?";
        try (Connection con = Conexao.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, usuarioId);
            ps.executeUpdate();
        }
    }
}
