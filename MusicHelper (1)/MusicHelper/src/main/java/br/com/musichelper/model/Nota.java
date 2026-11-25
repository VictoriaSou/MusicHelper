package br.com.musichelper.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Nota {

    private int id;
    private int usuarioId;
    private String titulo;
    private String categoria;
    private String conteudo;
    private LocalDate dataCriacao;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getConteudo() { return conteudo; }
    public void setConteudo(String conteudo) { this.conteudo = conteudo; }

    public LocalDate getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDate dataCriacao) { this.dataCriacao = dataCriacao; }

    public String getDataFormatada() {
        return dataCriacao == null ? "" : dataCriacao.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

    /** Classe CSS do selo de categoria. */
    public String getBadge() {
        if ("Ideia".equals(categoria)) {
            return "ideia";
        }
        if ("Lembrete".equals(categoria)) {
            return "lembrete";
        }
        return "repertorio";
    }
}
