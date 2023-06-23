package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        List<List<Wektor>> listaW = new ArrayList<>();
        List<Perceptron> listaP = new ArrayList<>();
        List<Wektor> wektoryP = listaWektorow("dane/Polish","Polish");
        List<Wektor> wektoryE = listaWektorow("dane/English","English");
        List<Wektor> wektoryG = listaWektorow("dane/German","German");
        Perceptron p1 = new Perceptron("Polish");
        Perceptron p2 = new Perceptron("English");
        Perceptron p3 = new Perceptron("German");
        listaP.add(p1);
        listaP.add(p2);
        listaP.add(p3);
        listaW.add(wektoryP);
        listaW.add(wektoryE);
        listaW.add(wektoryG);

        for (Perceptron p : listaP){
            for (List<Wektor> lw : listaW){
                for (Wektor w : lw) {
                    p.perceptronAlg(w,100);
                }
            }
        }
        guiXD(listaP);
    }
    public static void guiXD(List<Perceptron> lp) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Wprowadz tekst");
        List<String> ls = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            ls.add(line);
            if (line != null && line.equalsIgnoreCase("END")) {
                break;
            }
        }
        String nazwa = "";
            double maks = lp.get(0).getNetD(znaki(ls));
            nazwa = lp.get(0).getAktywacja();
            for (Perceptron p : lp) {
                if (p.getNetD(znaki(ls)) > maks) {
                    maks = p.getNetD(znaki(ls));
                    nazwa = p.getAktywacja();
                }
            }
        System.out.println("Wykryto jezyk: " + nazwa);
    }
    public static List<Wektor> listaWektorow(String dirPathname, String name) throws FileNotFoundException {
        List<Wektor> wektors = new ArrayList<>();
        File directory = new File(dirPathname);
        File[] plikiPolski = Objects.requireNonNull(directory.listFiles());

        for (File f : plikiPolski){
            List<String> wczytane = load(f);
            wektors.add(new Wektor(znaki(wczytane),name));
        }
        return wektors;
    }
    public static int[] znaki(List<String> wczytane){
        String dane = wczytane.toString();
        dane = dane.toLowerCase(Locale.ROOT);
        int[] znaki = new int[26];
        for (int i = 0; i < dane.length(); i++) {
            char znak = dane.charAt(i);
            if(znak >= 97 && znak <= 122 ) znaki[znak- 97] ++;
        }
        return znaki;
    }
    public static List<String> load (File file) throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        return bufferedReader.lines().collect(Collectors.toList());
    }
}
