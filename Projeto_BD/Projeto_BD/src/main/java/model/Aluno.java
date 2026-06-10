package model;

public class Aluno {
    private int idAluno;
    private String nome;
    private int idade;
    private String email;

    public Aluno() {}

    public Aluno(String nome, int idade, String email) {
        this.nome = nome;
        this.idade = idade;
        this.email = email;
    }

    public Aluno(int idAluno, String nome, int idade, String email) {
        this.idAluno = idAluno;
        this.nome = nome;
        this.idade = idade;
        this.email = email;
    }

    // Getters e Setters
    public int getIdAluno() { return idAluno; }
    public void setIdAluno(int idAluno) { this.idAluno = idAluno; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}