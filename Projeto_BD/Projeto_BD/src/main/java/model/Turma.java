package model;

public class Turma {

    private int idTurma;
    private String nome;
    private int ano;
    private int salaIdSala;

    public Turma() {
    }

    public Turma(String nome, int ano, int salaIdSala) {
        this.nome = nome;
        this.ano = ano;
        this.salaIdSala = salaIdSala;
    }

    public Turma(int idTurma, String nome, int ano, int salaIdSala) {
        this.idTurma = idTurma;
        this.nome = nome;
        this.ano = ano;
        this.salaIdSala = salaIdSala;
    }

    // Getters e Setters
    public int getIdTurma() { return idTurma; }
    public void setIdTurma(int idTurma) { this.idTurma = idTurma; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }
    public int getSalaIdSala() { return salaIdSala; }
    public void setSalaIdSala(int salaIdSala) { this.salaIdSala = salaIdSala;}
}