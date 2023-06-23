package com.company;

import java.util.ArrayList;
import java.util.List;

public class Kwiat implements Comparable<Kwiat>{
    private String nazwa;
    private List<Double> lista = new ArrayList<>();
    private double odleglosc = 0;

    public Kwiat(String nazwa, List<String> lista) {
        this.nazwa = nazwa;
        for (String s : lista) {
            this.lista.add(Double.parseDouble(s));
        }
    }

    public void setOdleglosc(double odleglosc) {
        this.odleglosc = odleglosc;
    }

    public double getOdleglosc() {
        return odleglosc;
    }

    public String getNazwa() {
        return nazwa;
    }

    public List<Double> getLista() {
        return lista;
    }

    @Override
    public int compareTo(Kwiat o1) {
        if(this.odleglosc - o1.getOdleglosc() < 0) return 1;
        else if(this.odleglosc - o1.getOdleglosc() > 0) return -1;
        return 0;
    }

    @Override
    public String toString() {
        return odleglosc + "";
    }
}
