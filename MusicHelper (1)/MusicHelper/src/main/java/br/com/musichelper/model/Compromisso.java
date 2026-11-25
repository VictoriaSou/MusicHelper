package br.com.musichelper.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Compromisso {

    private static final DateTimeFormatter DATA_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("HH:mm");

    private int id;
    private int usuarioId;
    private String titulo;
    private String tipo;
    private LocalDate data;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private String local;
    private int participantes = 1;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFim() { return horaFim; }
    public void setHoraFim(LocalTime horaFim) { this.horaFim = horaFim; }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

    public int getParticipantes() { return participantes; }
    public void setParticipantes(int participantes) { this.participantes = participantes; }

    // ---- Auxiliares para a tela ----

    public String getDataFormatada() {
        return data == null ? "" : data.format(DATA_BR);
    }

    public String getInicioFormatado() {
        return horaInicio == null ? "" : horaInicio.format(HORA);
    }

    /** Vazio quando não há hora de término (00:00), como na versão em PHP. */
    public String getFimFormatado() {
        return (horaFim == null || horaFim.equals(LocalTime.MIDNIGHT)) ? "" : horaFim.format(HORA);
    }

    /** Classe CSS do cartão: verde para Ensaio, roxo para Apresentação. */
    public String getCor() {
        return "ensaio".equalsIgnoreCase(tipo) ? "green" : "purple";
    }
}
