package model;

public class Disciplina {

    private int idDisciplina;
    private String nome;
    private int cargaHoraria;
    private int professorIdProfessor;

    public Disciplina() {
    }
    public Disciplina(String nome, int cargaHoraria, int professorIdProfessor) {
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.professorIdProfessor = professorIdProfessor;
    }
    public Disciplina(int idDisciplina, String nome, int cargaHoraria, int professorIdProfessor) {
        this.idDisciplina = idDisciplina;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
        this.professorIdProfessor = professorIdProfessor;
    }

    // Getters e Setters
    public int getIdDisciplina() { return idDisciplina; }
    public void setIdDisciplina(int idDisciplina) { this.idDisciplina = idDisciplina; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public int getCargaHoraria() { return cargaHoraria; }
    public void setCargaHoraria(int cargaHoraria) { this.cargaHoraria = cargaHoraria; }
    public int getProfessorIdProfessor() { return professorIdProfessor; }
    public void setProfessorIdProfessor(int professorIdProfessor) { this.professorIdProfessor = professorIdProfessor; }
}