package model;

import java.sql.Timestamp;

public class Matricula {

    private int alunoIdAluno;
    private int turmaIdTurma;
    private Timestamp dataMatricula;

    public Matricula() {}

    public Matricula(int alunoIdAluno, int turmaIdTurma) {
        this.alunoIdAluno = alunoIdAluno;
        this.turmaIdTurma = turmaIdTurma;
    }

    public Matricula(int alunoIdAluno, int turmaIdTurma, Timestamp dataMatricula) {
        this.alunoIdAluno = alunoIdAluno;
        this.turmaIdTurma = turmaIdTurma;
        this.dataMatricula = dataMatricula;
    }

    // Getters e Setters
    public int getAlunoIdAluno() { return alunoIdAluno; }
    public void setAlunoIdAluno(int alunoIdAluno) { this.alunoIdAluno = alunoIdAluno; }
    public int getTurmaIdTurma() { return turmaIdTurma; }
    public void setTurmaIdTurma(int turmaIdTurma) { this.turmaIdTurma = turmaIdTurma; }
    public Timestamp getDataMatricula() { return dataMatricula; }
    public void setDataMatricula(Timestamp dataMatricula) { this.dataMatricula = dataMatricula; }
}