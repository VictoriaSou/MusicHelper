package br.com.musichelper.dao;

import br.com.musichelper.model.Usuario;
import br.com.musichelper.util.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public boolean emailExiste(String email) throws SQLException {
        String sql = "SELECT id FROM usuarios WHERE email = ?";
        try (Connection con = Conexao.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void cadastrar(String nome, String email, String senhaHash) throws SQLException {
        String sql = "INSERT INTO usuarios (nome, email, senha) VALUES (?, ?, ?)";
        try (Connection con = Conexao.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nome);
            ps.setString(2, email);
            ps.setString(3, senhaHash);
            ps.executeUpdate();
        }
    }

    /** Busca apenas os dados necessários para o login. */
    public Usuario buscarParaLogin(String email) throws SQLException {
        String sql = "SELECT id, nome, senha FROM usuarios WHERE email = ?";
        try (Connection con = Conexao.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNome(rs.getString("nome"));
                u.setSenha(rs.getString("senha"));
                return u;
            }
        }
    }

    public Usuario buscarPorId(int id) throws SQLException {
        String sql = "SELECT nome, email, instrumentos, experiencia, banda, repertorio, biografia, foto_perfil "
                + "FROM usuarios WHERE id = ?";
        try (Connection con = Conexao.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                Usuario u = new Usuario();
                u.setId(id);
                u.setNome(rs.getString("nome"));
                u.setEmail(rs.getString("email"));
                u.setInstrumentos(rs.getString("instrumentos"));
                u.setExperiencia(rs.getString("experiencia"));
                u.setBanda(rs.getString("banda"));
                u.setRepertorio(rs.getString("repertorio"));
                u.setBiografia(rs.getString("biografia"));
                u.setFotoPerfil(rs.getString("foto_perfil"));
                return u;
            }
        }
    }

    public void atualizar(Usuario u) throws SQLException {
        String sql = "UPDATE usuarios SET nome = ?, instrumentos = ?, experiencia = ?, banda = ?, "
                + "repertorio = ?, biografia = ?, foto_perfil = ? WHERE id = ?";
        try (Connection con = Conexao.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getNome());
            ps.setString(2, u.getInstrumentos());
            ps.setString(3, u.getExperiencia());
            ps.setString(4, u.getBanda());
            ps.setString(5, u.getRepertorio());
            ps.setString(6, u.getBiografia());
            ps.setString(7, u.getFotoPerfil());
            ps.setInt(8, u.getId());
            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        try (Connection con = Conexao.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
