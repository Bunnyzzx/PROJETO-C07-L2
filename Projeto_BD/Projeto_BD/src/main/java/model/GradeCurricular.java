package model;

public class GradeCurricular {

    private int turmaIdTurma;
    private int disciplinaIdDisciplina;

    public GradeCurricular() {}

    public GradeCurricular(int turmaIdTurma, int disciplinaIdDisciplina) {
        this.turmaIdTurma = turmaIdTurma;
        this.disciplinaIdDisciplina = disciplinaIdDisciplina;
    }
    // Getters e Setters
    public int getTurmaIdTurma() { return turmaIdTurma; }
    public void setTurmaIdTurma(int turmaIdTurma) { this.turmaIdTurma = turmaIdTurma; }
    public int getDisciplinaIdDisciplina() { return disciplinaIdDisciplina; }
    public void setDisciplinaIdDisciplina(int disciplinaIdDisciplina) { this.disciplinaIdDisciplina = disciplinaIdDisciplina; }
}