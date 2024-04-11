package com.example.qrvlaky;

// aktivita pro získávání dat z databáze
public class Train {
    private int id;
    private String cislo;
    private String poznamka;
    private String datum;
    private String sort;

    public Train(int id, String cislo, String poznamka, String datum, String sort) {
        this.id = id;
        this.cislo = cislo;
        this.poznamka = poznamka;
        this.datum = datum;
        this.sort = sort;
    }

    public int getId() {
        return id;
    }

    public String getCislo() {
        return cislo;
    }

    public String getPoznamka() {
        return poznamka;
    }

    public String getDatum() {
        return datum;
    }
    public String getSort() {return sort; }
}
