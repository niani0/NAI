package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Perceptron {

    private String aktywacja;
    private List<Double> wagi;
    Random random = new Random();
    private double theta = random.nextDouble();

    public double getNetD(int[] znaki) {
        return max(znaki);
    }

    public String getAktywacja() {
        return aktywacja;
    }

    public Perceptron(String aktywacja) {
        this.aktywacja = aktywacja;
        wagi = losowanieWag(26);
        normalize();
    }
    public void perceptronAlg(Wektor w, int ile) {
        int d;
        for (int i = 0; i < ile; i++) {
            d = 0;
            if (aktywacja.equals(w.getNazwa())) {
                d = 1;
            }
                correctWeights(w.getZnaki(), net(w.getZnaki()), d);
        }

    }
    public void correctWeights(int[] znaki, double y, int d){
        double alfa = 0.01;
        double w;
        double wzor;
        double suma = 0;
        for (int j : znaki) suma += j;
        for (int i = 0; i < wagi.size(); i++) {
            w = wagi.get(i);
            wzor = alfa * (d - y) * (znaki[i]/suma);
            wagi.set(i, w + wzor);
        }
        normalize();
        theta -= (d - y) * alfa;
    }

    private void normalize() {
        double suma  = 0;
        for (Double d : wagi) suma += d;
        for (int i = 0; i < wagi.size(); i++) wagi.set(i,wagi.get(i)/suma);
    }

    public double max(int[] znaki){
        double  suma = 0;
        for (int i = 0; i < wagi.size(); i++) {
            suma+=wagi.get(i)*znaki[i];
        }
        return suma;
    }

    public List<Double> getWagi() {
        return wagi;
    }

    public double net(int[] znaki){
        double  suma = 0;
        for (int i = 0; i < wagi.size(); i++) {
            suma+= wagi.get(i) * znaki[i];
        }
        if(suma >= theta) return 1;
        else return 0;
    }
    public List<Double> losowanieWag(int size){
        List<Double> lista = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            lista.add(random.nextDouble());
        }
        return lista;
    }
}

