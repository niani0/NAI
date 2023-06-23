package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.pow;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        Scanner scanner = new Scanner(System.in);
        int k;
        List<String> dane = load(new File("dane_testowe.csv"));
        List<Kwiat> lTrain = new ArrayList<>();
        create(dane,lTrain);
        System.out.println("Wprowadź k: ");
        k = scanner.nextInt();
        while (k > lTrain.size()) {
            System.out.println("złe k wprowadź ponownie!");
            k = scanner.nextInt();
        }
        List<Centroid> centroids = learn(k,lTrain);
        wypisanie(lTrain, centroids);
    }

    private static List<Centroid> learn(int k, List<Kwiat> lTrain) {
        HashSet<Integer> znaczniki = losujZnacznik(lTrain, k);
        List<Centroid> sCentroids = centroids(lTrain, znaczniki);
        orderPoints(sCentroids, lTrain);
        List<Centroid> nCentroids = centroids(lTrain, znaczniki);

        while (ifReady(sCentroids, nCentroids)) {
            sCentroids = centroids(lTrain, znaczniki);
            orderPoints(sCentroids, lTrain);
            nCentroids = centroids(lTrain, znaczniki);
        }

        return nCentroids;
    }
    private static void wypisanie(List<Kwiat> lTrain, List<Centroid> centroids) {
        int suma;
        int sumaS;
        int sumaV;
        int sumaVi;
        for (Centroid c : centroids){
            suma = 0;
            sumaV = 0;
            sumaVi = 0;
            sumaS = 0;
            for (Kwiat k : lTrain){
                if(c.getZnacznik() == k.getZnacznik()) {
                    suma++;
                    if (k.getKlasa().equals("setosa")) sumaS++;
                    if (k.getKlasa().equals("virginica")) sumaVi++;
                    if (k.getKlasa().equals("versicolor")) sumaV++;
                }
            }
            System.out.println("Ilosc kwiatow w klastrze: " + suma);
            System.out.println("Proporcje : " + "Setosa: " + sumaS + ", Versicolor: "+ sumaV + ", Virgnica: " + sumaVi);
        }

    }
    private static boolean ifReady(List<Centroid> cL1, List<Centroid> cL2){
        if (cL2.size() != cL1.size()) return true;
        boolean rdy = false;
        for (int i = 0; i < cL1.size(); i++) {

            if (!compareLists(cL1.get(i).getWektor(), cL2.get(i).getWektor())) {
                rdy = true;
            }
        }
        return rdy;
    }
    private static boolean compareLists(List<Double> wektor, List<Double> wektor1) {
        if (wektor1.size() == 0) return false;
        boolean same = true;
        for (int i = 0; i < wektor.size(); i++) {
            if (!wektor.get(i).equals(wektor1.get(i))) {
                same = false;
                break;
            }
        }
        return same;
    }
    private static void orderPoints(List<Centroid> centroids, List<Kwiat> lTrain) {
        double suma;
        double sumaA = 0;
        double minL;
        Centroid minC;

        for (Kwiat k : lTrain) {
            minL = odleglosc(k.getWektor(), centroids.get(0).getWektor());
            minC = centroids.get(0);
            for (Centroid c : centroids) {
                if (odleglosc(c.getWektor(), k.getWektor()) < minL) {
                    minL = odleglosc(c.getWektor(), k.getWektor());
                    minC = c;
                }
            }
            k.setZnacznik(minC.getZnacznik());
        }
        for (Centroid c : centroids) {
            suma = 0;
            for (Kwiat k : lTrain) {
                if (k.getZnacznik() == c.getZnacznik()) suma += odleglosc(c.getWektor(),k.getWektor());
            }
            System.out.println("Centroid: " + c.getZnacznik() + ", odległość: " + suma);
            sumaA += suma;
        }
        System.out.println("Cala suma: " + sumaA);
    }
    public static double odleglosc(List<Double> list1, List<Double> list2){
        double result = 0;
        for (int i = 0; i < list1.size(); i++) {
            result += pow(list1.get(i) - list2.get(i),2);
        }
        return result;
    }
    private static List<Centroid> centroids(List<Kwiat> lTrain, HashSet<Integer> znaczniki) {
        List<Integer> nZnaczniki = new LinkedList<>(znaczniki);
        List<List<Kwiat>> lGrup = new ArrayList<>();
        List<Centroid> lCent = new LinkedList<>();
        List<Double> cent = new LinkedList<>();
        int count;
        int ktZnacznik = 0;
        for (Integer ignored : znaczniki) lGrup.add(new ArrayList<>());
            for (Kwiat k : lTrain) {
                for (Integer i : znaczniki) {
                    if (k.getZnacznik() == i) lGrup.get(i).add(k);
                }
            }
        for (List<Kwiat> lKwiat : lGrup) {
            count = 0;
            for (Kwiat k : lKwiat) {
                for (int i = 0; i < k.getWektor().size(); i++) {
                    if(cent.size() > i) cent.set(i, cent.get(i) + k.getWektor().get(i));
                    else cent.add(k.getWektor().get(i));
                }
                count++;
            }
            for (int i = 0; i < cent.size(); i++) {
                cent.set(i, cent.get(i)/count);
            }
            lCent.add(new Centroid(cent,nZnaczniki.get(ktZnacznik)));
            cent = new LinkedList<>();
            ktZnacznik++;
        }
        lCent.removeIf(c -> c.getWektor().size() == 0);
        return lCent;
    }
    private static HashSet<Integer> losujZnacznik(List<Kwiat> lTrain, int k) {
        Random random = new Random();
        int int_random;
        HashSet<Integer> znaczniki = new HashSet<>();
        while (znaczniki.size() < k){
            znaczniki = new HashSet<>();
        for (Kwiat kwiat : lTrain){
            int_random = random.nextInt(k);
            kwiat.setZnacznik(int_random);
        }
        for (Kwiat kwiat : lTrain) {
            znaczniki.add(kwiat.getZnacznik());
        }

        }
        return znaczniki;
    }
    public static List<String> load (File file) throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        return bufferedReader.lines().collect(Collectors.toList());
    }
    public static void create(List<String> dane, List<Kwiat> list){
        for (String s:dane){
            List<String> lista = Arrays.stream(s.replaceAll("\"", "")
                            .split(","))
                    .collect(Collectors.toList());
                list.add(new Kwiat(lista.get(lista.size()-1), lista.subList(1, lista.size()-1)));
        }
    }
}
