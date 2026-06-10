package model;

public class Professor {

    private int idProfessor;
    private String nome;
    private String especialidade;
    private String email;

    public Professor() {
    }

    public Professor(String nome, String especialidade, String email) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.email = email;
    }

    public Professor(int idProfessor, String nome, String especialidade, String email) {
        this.idProfessor = idProfessor;
        this.nome = nome;
        this.especialidade = especialidade;
        this.email = email;
    }

    // Getters e Setters
    public int getIdProfessor() { return idProfessor; }
    public void setIdProfessor(int idProfessor) { this.idProfessor = idProfessor; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}