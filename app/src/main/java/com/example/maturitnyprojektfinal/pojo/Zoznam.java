package com.example.maturitnyprojektfinal.pojo;

public class Zoznam {
    private String Nazov;
    private double Pocet;
    private double Cena;

    public Zoznam(String nazov, double pocet, double cena) {
        Nazov = nazov;
        Pocet = pocet;
        Cena = cena;
    }

    public String getNazov() {
        return Nazov;
    }

    public double getPocet() {
        return Pocet;
    }

    public double getCena() {
        return Cena;
    }
}
