package model;

import java.time.LocalDate;

public class Ganho {
    private  double valor;
    private String plataforma;
    private  LocalDate data;
    public double getValor() {
        return valor;
    }
    public String getPlataforma() {
        return plataforma;
    }
    public Ganho(double valor, String plataforma, LocalDate data) {
        this.valor = valor;
        this.plataforma = plataforma;
        this.data = data;
    }
    public LocalDate getData() {
        return data;
    }
}
