package com.company;

import java.util.ArrayList;
import java.util.List;

public class Kwiat {
    private int znacznik;
    private final String klasa;
    private final List<Double> wektor = new ArrayList<>();

    public Kwiat(String klasa, List<String> wektorS) {
        this.klasa = klasa;
        for (String s : wektorS) {
            this.wektor.add(Double.parseDouble(s));
        }
    }
    public void setZnacznik(int znacznik) {
        this.znacznik = znacznik;
    }
    public int getZnacznik() {
        return znacznik;
    }
    public String getKlasa() {
        return klasa;
    }
    public List<Double> getWektor() {
        return wektor;
    }

    @Override
    public String toString() {
        return znacznik + "";
    }
}
