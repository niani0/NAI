package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        File file;
        List<String> dane;
        List<Kwiat> kwiatTrains = new ArrayList<>();
        List<Kwiat> kwiatTests = new ArrayList<>();
        List<Double> wagi = new ArrayList<>();


        String pierwsza = "virginica";
        String druga = "versicolor";

        file = new File("iristrain.csv");
        dane = load(file);
        create(dane,kwiatTrains, pierwsza,druga );

        file = new File("iristest.csv");
        dane = load(file);
        dane = dane.subList(1, dane.size());
        create(dane, kwiatTests, pierwsza, druga);
        wagi = perceptronAlg(kwiatTrains);
        testy(kwiatTests, wagi);
        guiXD(wagi, dane, kwiatTests, kwiatTrains);

    }
    public static void guiXD(List<Double> wagi, List<String> dane, List<Kwiat> kwiatTests, List<Kwiat> kwiatTrains){
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("Czy chcesz wprowadzic nowy wektor T/N?");
            if (scanner.next().equals("N")) break;
            dane.clear();
            System.out.println("Wprowadź ilosc atrybutów");
            int a = scanner.nextInt();

            for (int i = 0; i < a; i++) {
                System.out.println("Wprowadź atrybut");
                dane.add(scanner.next());
            }

            System.out.println("Wprowadź nazwe");
            dane.add(scanner.next());
            kwiatTests.clear();
            kwiatTests.add(new Kwiat(dane.get(dane.size()-1),dane.subList(0, dane.size()-1)));
            if (kwiatTests.get(0).getLista().size() != kwiatTrains.get(0).getLista().size()){
                System.out.println("Inne ilosci danych");
            }
            else testy(kwiatTests, wagi);
        }
    }
    public static List<Double> perceptronAlg(List<Kwiat> train) {
        List<Double> wagi = new ArrayList<>();
        Random random = new Random();
        int net;

        for (int i = 0; i < train.get(0).getLista().size(); i++) {
            double f_random = random.nextFloat();
            wagi.add(random.nextDouble());
        }
        for (int i = 0; i < 500; i++) {

            for (Kwiat k : train) {
                net = net(wagi, k.getLista());
                if (net != k.number()) correctWeights(wagi, k.getLista(), net, k.number());
            }
        }

        return wagi;
    }
    public static void correctWeights(List<Double> wagi, List<Double> lista, int y, int d){
        double alfa = 0.1;
        for (int i = 0; i < wagi.size(); i++) {
            wagi.set(i, wagi.get(i) + alfa  * (d - y) * lista.get(i));
        }
    }
    public static int net(List<Double> wektory, List<Double> wejscie){
        double  suma = 0;
        int prog = 3;
        for (int i = 0; i < wektory.size(); i++) {
            suma+=wektory.get(i)*wejscie.get(i);
        }
        if(suma - prog < 0) return 0;
        else return 1;
    }
    public static void testy(List<Kwiat> tests, List<Double> wagi){
        int wszystkie  = tests.size();
        int dobrze = 0;
        int net;
        for (Kwiat k :tests) {
            net = net(wagi, k.getLista());
            if(net == k.number()) dobrze++;
        }
        float f = (float) (dobrze * 100) / wszystkie;
        System.out.println("Dobrze: " + dobrze);
        System.out.println("Źle: " + (wszystkie - dobrze));
        System.out.println("% trafionych " +  f);
    }
    public static List<String> load (File file) throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        return bufferedReader.lines().collect(Collectors.toList());
    }
    public static void create(List<String> dane, List<Kwiat> list, String klasa1, String klasa2){
        for (String s:dane){
            List<String> lista = Arrays.stream(s.replaceAll("\"", "")
                            .split(","))
                    .collect(Collectors.toList());
            if (lista.get(lista.size()-1).equals(klasa1) || lista.get(lista.size()-1).equals(klasa2))
            list.add(new Kwiat(lista.get(lista.size()-1), lista.subList(1, lista.size()-1)));
        }
    }

}
