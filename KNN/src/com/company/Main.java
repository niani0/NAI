package com.company;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.pow;

public class Main {

    private static List<String> lista;

    public static void main(String[] args) throws FileNotFoundException {

        File file;
        List<String> dane;
        List<Kwiat> kwiatTrains = new ArrayList<>();
        List<Kwiat> kwiatTests = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        file = new File("iristrain.csv");
        dane = load(file);
        dane = dane.subList(1,dane.size());
        create(dane,kwiatTrains);

        file = new File("iristest.csv");
        dane = load(file);
        dane = dane.subList(1,dane.size());
        create(dane, kwiatTests);

        System.out.println("Wprowadź k");
        int k = scanner.nextInt();
        testy(kwiatTests, kwiatTrains, k);

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
            System.out.println("Wprowadź k");
            if (kwiatTests.get(0).getLista().size() != kwiatTrains.get(0).getLista().size()){
                System.out.println("Inne ilosci danych");
            }
            else testy(kwiatTests, kwiatTrains, scanner.nextInt());
            }


    }
    public static void create(List<String> dane, List<Kwiat> list){
        for (String s:dane){
            lista = Arrays.stream(s.replaceAll("\"","")
                            .split(","))
                    .collect(Collectors.toList());
            list.add(new Kwiat(lista.get(lista.size()-1),lista.subList(1, lista.size()-1)));
        }
    }
    public static List<String> load (File file) throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        return bufferedReader.lines().collect(Collectors.toList());
    }
    public static void testy(List<Kwiat> tests , List<Kwiat> trains, int k){

        if (k == 0){ System.out.println("0%"); return;}

        PriorityQueue<Kwiat> heap = new PriorityQueue<>();
        HashMap<String, Integer> wynik = new HashMap<>();
        String max;
        int ilosc = 0;

        for (Kwiat testowy: tests) {
            for (Kwiat treningowy: trains) {
                treningowy.setOdleglosc(odleglosc(testowy.getLista(), treningowy.getLista()));
                if (heap.size() < k) heap.add(treningowy);
                else {
                    assert heap.peek() != null;
                    if(heap.peek().getOdleglosc() > treningowy.getOdleglosc()) {
                        heap.poll();
                        heap.add(treningowy);
                    }
                }
            }
            for (Kwiat k1: heap) {
                if(wynik.containsKey(k1.getNazwa())) {
                    wynik.put(k1.getNazwa(), wynik.get(k1.getNazwa())+1);
                }
                wynik.putIfAbsent(k1.getNazwa(), 1);
            }
            max = Collections.max(wynik.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
            if (max.equals(testowy.getNazwa())) ilosc++;
            wynik.clear();
            heap.clear();
        }
        System.out.println("Poprawne jest " + ilosc + " / " + tests.size());
        System.out.println(ilosc * 100 / tests.size() + "%");
    }
    public static double odleglosc(List<Double> list1, List<Double> list2){
        double result = 0;
        for (int i = 0; i < list1.size(); i++) {
            result += pow(list1.get(i) - list2.get(i),2);
        }
        return result;
    }

}
