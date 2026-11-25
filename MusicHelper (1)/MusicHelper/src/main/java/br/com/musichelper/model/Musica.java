package br.com.musichelper.model;

public class Musica {

    private int id;
    private int usuarioId;
    private String titulo;
    private String artista;
    private String tom;
    private String tipo;
    private String linkExterno;
    private String anotacoes;
    private String arquivoPath;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getArtista() { return artista; }
    public void setArtista(String artista) { this.artista = artista; }

    public String getTom() { return tom; }
    public void setTom(String tom) { this.tom = tom; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getLinkExterno() { return linkExterno; }
    public void setLinkExterno(String linkExterno) { this.linkExterno = linkExterno; }

    public String getAnotacoes() { return anotacoes; }
    public void setAnotacoes(String anotacoes) { this.anotacoes = anotacoes; }

    public String getArquivoPath() { return arquivoPath; }
    public void setArquivoPath(String arquivoPath) { this.arquivoPath = arquivoPath; }
}
