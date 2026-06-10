package model;

public class Sala {

    private int idSala;
    private String numero;
    private int capacidade;

    public Sala() {
    }

    public Sala(String numero, int capacidade) {
        this.numero = numero;
        this.capacidade = capacidade;
    }

    public Sala(int idSala, String numero, int capacidade) {
        this.idSala = idSala;
        this.numero = numero;
        this.capacidade = capacidade;
    }

    // Getters e Setters
    public int getIdSala() { return idSala; }
    public void setIdSala(int idSala) { this.idSala = idSala; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public int getCapacidade() { return capacidade; }
    public void setCapacidade(int capacidade) { this.capacidade = capacidade; }
}