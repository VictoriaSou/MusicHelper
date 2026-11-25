package br.com.musichelper.dao;

import br.com.musichelper.model.Musica;
import br.com.musichelper.util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MusicaDAO {

    public List<Musica> listar(int usuarioId) throws SQLException {
        String sql = "SELECT id, titulo, artista, tom, tipo, link_externo, anotacoes, arquivo_path "
                + "FROM musicas WHERE usuario_id = ? ORDER BY titulo ASC";
        List<Musica> lista = new ArrayList<>();
        try (Connection con = Conexao.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Musica m = new Musica();
                    m.setId(rs.getInt("id"));
                    m.setUsuarioId(usuarioId);
                    m.setTitulo(rs.getString("titulo"));
                    m.setArtista(rs.getString("artista"));
                    m.setTom(rs.getString("tom"));
                    m.setTipo(rs.getString("tipo"));
                    m.setLinkExterno(rs.getString("link_externo"));
                    m.setAnotacoes(rs.getString("anotacoes"));
                    m.setArquivoPath(rs.getString("arquivo_path"));
                    lista.add(m);
                }
            }
        }
        return lista;
    }

    /** Insere a música e registra no histórico, na mesma transação. */
    public void inserir(Musica m) throws SQLException {
        String sql = "INSERT INTO musicas (usuario_id, titulo, artista, tom, tipo, link_externo, anotacoes, "
                + "arquivo_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexao.getConnection()) {
            con.setAutoCommit(false);
            try {
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, m.getUsuarioId());
                    ps.setString(2, m.getTitulo());
                    ps.setString(3, m.getArtista());
                    ps.setString(4, m.getTom());
                    ps.setString(5, m.getTipo());
                    ps.setString(6, m.getLinkExterno());
                    ps.setString(7, m.getAnotacoes());
                    ps.setString(8, m.getArquivoPath());
                    ps.executeUpdate();
                }
                String tom = m.getTom() == null || m.getTom().isEmpty() ? "" : " Tom: " + m.getTom() + ".";
                String desc = "Adicionou a música '" + m.getTitulo() + "' (" + m.getArtista()
                        + ") ao seu repertório." + tom;
                HistoricoDAO.registrar(con, m.getUsuarioId(), "Repertório: Nova " + m.getTipo(), desc, "Geral");
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    public boolean excluir(int id, int usuarioId) throws SQLException {
        String sql = "DELETE FROM musicas WHERE id = ? AND usuario_id = ?";
        try (Connection con = Conexao.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, usuarioId);
            return ps.executeUpdate() > 0;
        }
    }
}
