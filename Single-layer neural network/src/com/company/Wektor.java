package com.company;

public class Wektor {
    private int[] znaki;
    private String nazwa;

    public Wektor(int[] znaki, String nazwa) {
        this.znaki = znaki;
        this.nazwa = nazwa;
    }
    public void setZnaki(int[] znaki) {
        this.znaki = znaki;
    }
    public int[] getZnaki() {
        return znaki;
    }
    public String getNazwa() {
        return nazwa;
    }

}
