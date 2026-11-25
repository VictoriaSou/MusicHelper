package br.com.musichelper.dao;

import br.com.musichelper.model.Historico;
import br.com.musichelper.util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HistoricoDAO {

    /** Registra uma atividade usando uma conexão existente (permite participar de transações). */
    public static void registrar(Connection con, int usuarioId, String titulo, String descricao, String categoria)
            throws SQLException {
        String sql = "INSERT INTO historico (usuario_id, titulo, descricao, categoria) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            ps.setString(2, titulo);
            ps.setString(3, descricao);
            ps.setString(4, categoria);
            ps.executeUpdate();
        }
    }

    public List<Historico> listar(int usuarioId) throws SQLException {
        String sql = "SELECT id, titulo, descricao, categoria, data_atividade FROM historico "
                + "WHERE usuario_id = ? ORDER BY data_atividade DESC, id DESC";
        List<Historico> lista = new ArrayList<>();
        try (Connection con = Conexao.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Historico h = new Historico();
                    h.setId(rs.getInt("id"));
                    h.setUsuarioId(usuarioId);
                    h.setTitulo(rs.getString("titulo"));
                    h.setDescricao(rs.getString("descricao"));
                    h.setCategoria(rs.getString("categoria"));
                    h.setDataAtividade(rs.getTimestamp("data_atividade").toLocalDateTime());
                    lista.add(h);
                }
            }
        }
        return lista;
    }
}
