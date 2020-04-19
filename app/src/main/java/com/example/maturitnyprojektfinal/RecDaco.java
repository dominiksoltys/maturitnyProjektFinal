package com.example.maturitnyprojektfinal;

public class RecDaco {
    private String Nazov;
    private int Pocet;
    private int Cena;

    public RecDaco(String Nazov, int Pocet, int Cena){
        this.Nazov=Nazov;
        this.Pocet=Pocet;
        this.Cena=Cena;
    }
    public RecDaco(){}//prazdny konstruktor pre firebase

    public String getNazov() {
        return Nazov;
    }
    public int getPocet() {
        return Pocet;
    }
    public int getCena() {
        return Cena;
    }
}
