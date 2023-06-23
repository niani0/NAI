package com.company;

import java.util.LinkedList;
import java.util.List;

public class Centroid{
    private List<Double> wektor = new LinkedList<>();
    private int znacznik;

    public Centroid(List<Double> wektor, int znacznik) {
        this.wektor = wektor;
        this.znacznik = znacznik;
    }

    public void setWektor(List<Double> wektor) {
        this.wektor = wektor;
    }

    public List<Double> getWektor() {
        return wektor;
    }

    public int getZnacznik() {
        return znacznik;
    }

    @Override
    public String toString() {
        return wektor + ", " + znacznik;
    }
}
