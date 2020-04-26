package com.example.maturitnyprojektfinal.pojo;

public class Zoznam {
    private String ZID;
    private String Nazov;
    private long Pocet;
    private double Cena;
    private String Obchod;

    public Zoznam(String ID, String nazov, long pocet, double cena, String obchod) {
        ZID = ID;
        Nazov = nazov;
        Pocet = pocet;
        Cena = cena;
        Obchod=obchod;
    }
    public String getZID() { return ZID; }
    public String getNazov() { return Nazov; }
    public long getPocet() { return Pocet; }
    public double getCena() { return Cena; }
    public String getObchod() {return Obchod;}
}
