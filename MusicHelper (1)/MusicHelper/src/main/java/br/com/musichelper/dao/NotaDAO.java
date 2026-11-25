package br.com.musichelper.dao;

import br.com.musichelper.model.Nota;
import br.com.musichelper.util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotaDAO {

    public List<Nota> listar(int usuarioId) throws SQLException {
        String sql = "SELECT id, titulo, categoria, conteudo, data_criacao FROM notas "
                + "WHERE usuario_id = ? ORDER BY id DESC";
        List<Nota> lista = new ArrayList<>();
        try (Connection con = Conexao.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, usuarioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Nota n = new Nota();
                    n.setId(rs.getInt("id"));
                    n.setUsuarioId(usuarioId);
                    n.setTitulo(rs.getString("titulo"));
                    n.setCategoria(rs.getString("categoria"));
                    n.setConteudo(rs.getString("conteudo"));
                    n.setDataCriacao(rs.getDate("data_criacao").toLocalDate());
                    lista.add(n);
                }
            }
        }
        return lista;
    }

    public void inserir(Nota n) throws SQLException {
        // data_criacao é NOT NULL e sem valor padrão no banco, então enviamos CURRENT_DATE
        String sql = "INSERT INTO notas (usuario_id, titulo, categoria, conteudo, data_criacao) "
                + "VALUES (?, ?, ?, ?, CURRENT_DATE)";
        try (Connection con = Conexao.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, n.getUsuarioId());
            ps.setString(2, n.getTitulo());
            ps.setString(3, n.getCategoria());
            ps.setString(4, n.getConteudo());
            ps.executeUpdate();
        }
    }

    public boolean excluir(int id, int usuarioId) throws SQLException {
        String sql = "DELETE FROM notas WHERE id = ? AND usuario_id = ?";
        try (Connection con = Conexao.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setInt(2, usuarioId);
            return ps.executeUpdate() > 0;
        }
    }
}
