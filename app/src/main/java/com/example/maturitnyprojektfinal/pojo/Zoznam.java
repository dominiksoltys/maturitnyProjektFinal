package com.example.maturitnyprojektfinal.pojo;

public class Zoznam {
    private String ZID;
    private String Nazov;
    private long Pocet;
    private double Cena;

    public Zoznam(String ID, String nazov, long pocet, double cena) {
        ZID = ID;
        Nazov = nazov;
        Pocet = pocet;
        Cena = cena;
    }
    public String getZID() { return ZID; }
    public String getNazov() { return Nazov; }
    public long getPocet() {
        return Pocet;
    }
    public double getCena() {
        return Cena;
    }
}
