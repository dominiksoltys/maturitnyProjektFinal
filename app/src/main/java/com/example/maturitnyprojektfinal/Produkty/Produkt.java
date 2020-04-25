package com.example.maturitnyprojektfinal.Produkty;

public class Produkt {
    private String PID;
    private String Nazov;
    private long Pocet;

    public Produkt(String ID, String nazov, long pocet) {
        PID = ID;
        Nazov = nazov;
        Pocet = pocet;
    }
    public String getPID() { return PID; }
    public String getNazov() { return Nazov; }
    public long getPocet() { return Pocet; }
    public void setPID(String PID) {PID = PID;}
}