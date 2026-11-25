package br.com.musichelper.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Historico {

    private int id;
    private int usuarioId;
    private String titulo;
    private String descricao;
    private String categoria;
    private LocalDateTime dataAtividade;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public LocalDateTime getDataAtividade() { return dataAtividade; }
    public void setDataAtividade(LocalDateTime dataAtividade) { this.dataAtividade = dataAtividade; }

    public String getDataFormatada() {
        return dataAtividade == null ? "" : dataAtividade.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    /** Classe CSS da categoria (agenda = verde, demais = cinza). */
    public String getCor() {
        return "Agenda".equals(categoria) ? "agenda" : "geral";
    }
}
