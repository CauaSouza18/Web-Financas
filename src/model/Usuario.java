package model;

public class Usuario {
    private String nome;
    private String email;
    private String senha;
    private int id;

    public Usuario (String nome, String email, String senha, int id) {
    this.nome = nome;
    this.email = email;
    this.senha = senha;
    this.id = id;
}
    public String getNome() {
        return nome;
    }
    public String getEmail() {
        return email;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getId() {
        return id;
    }
}
