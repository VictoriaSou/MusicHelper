package br.com.musichelper.model;

public class Usuario {

    private int id;
    private String nome;
    private String email;
    private String senha;
    private String instrumentos;
    private String experiencia;
    private String banda;
    private String repertorio;
    private String biografia;
    private String fotoPerfil;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getInstrumentos() { return instrumentos; }
    public void setInstrumentos(String instrumentos) { this.instrumentos = instrumentos; }

    public String getExperiencia() { return experiencia; }
    public void setExperiencia(String experiencia) { this.experiencia = experiencia; }

    public String getBanda() { return banda; }
    public void setBanda(String banda) { this.banda = banda; }

    public String getRepertorio() { return repertorio; }
    public void setRepertorio(String repertorio) { this.repertorio = repertorio; }

    public String getBiografia() { return biografia; }
    public void setBiografia(String biografia) { this.biografia = biografia; }

    public String getFotoPerfil() { return fotoPerfil; }
    public void setFotoPerfil(String fotoPerfil) { this.fotoPerfil = fotoPerfil; }
}
