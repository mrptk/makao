/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mazur.makao;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Klasa służąca do interakcji programu z użytkownikiem, oparta na obiekcie typu Scanner
 * @author ptkma
 */
public class WprowadzanieDanych {

    private Scanner skaner;
    private Gra Gra;

    public WprowadzanieDanych() {
        skaner = new Scanner(System.in);
    }
/**
 * Metoda pobierająca liczbę całkowitą od użytkonika
 * @return Wybór liczby graczy mających wziąć udział w grze
 * @throws Wyjątki Jeśli podana liczba jest mniejsza od 2 lub większa od 5.
 */   
    public int iluGraczy() throws Wyjątki{
        int Ile;
        
        System.out.println("Ilu graczy ma wziąć udział w grze? ....... ");
        
        String IluGraczy = skaner.nextLine().trim();
        
        try{
            Ile = Integer.parseInt(IluGraczy);
        } catch ( Exception ex ) {
            throw new Wyjątki(Wyjątki.BŁĘDNA_LICZBA_GRACZY);
        }
        
        if (Ile < 2 || Ile > 5) throw new Wyjątki(Wyjątki.BŁĘDNA_LICZBA_GRACZY);
        
        return Ile;
    }
/**
 * Metoda pobierająca nazwę gracza od użytkonika
 * @return Nazwa gracza
 * @param Gracze Lista graczy
 * @throws Wyjątki Jeśli nazwa jest już zajęta.
 */  
    public String nowyGracz(ArrayList<Gracz> Gracze) throws Wyjątki {
        System.out.print("Podaj nazwę gracza ..... ");
        
        String NazwaGracza = skaner.nextLine().trim();
        
        for (Gracz G : Gracze) if (G != null) if (G.jakaNazwa().compareTo(NazwaGracza) == 0) throw new Wyjątki(Wyjątki.NAZWA_ZAJĘTA);
        
        return NazwaGracza;
    }
/**
 * Metoda pobierająca liczbę całkowitą od użytkonika
 * @return Wybór indeksu karty z talii
 * @throws Wyjątki Jeśli nie uda się konwersja na typ całkowity
 */  
    public int numerKarty() throws Wyjątki {
        System.out.println("Podaj numer karty, którą chcesz rzucić. Naciśnij \"0\", aby spasować.");

        int NumerKarty;
        
        try {
            String NumerKartyStr = skaner.nextLine().trim();
            NumerKarty = Integer.parseInt(NumerKartyStr);
            
        } catch (Exception ex) {
            throw new Wyjątki(Wyjątki.BŁĘDNY_WYBÓR);
        }
            
        return NumerKarty;
    }
/**
 * Metoda pobierająca liczbę całkowitą od użytkonika
 * @return Wybór ineksu koloru z listy
 * @throws Wyjątki Jeśli nie uda się konwersja na typ całkowity, bądź podany indeks jest poza tablicą
 */     
    
    public Kolor nowyKolor() throws Wyjątki{
        System.out.println("Zmień kolor:");
        int Wybór;
        Kolor WybranyKolor = null;
        
        for (int i = 0; i < Kolor.values().length; i++) System.out.println((i + 1) + ". " + Kolor.values()[i] + ".");
        
        try{
            String WybórStr = skaner.nextLine().trim();
            Wybór = Integer.parseInt(WybórStr);
            WybranyKolor = Kolor.values()[Wybór - 1];
        }catch(Exception ex){
            throw new Wyjątki(Wyjątki.BŁĘDNY_WYBÓR);
        }
        return WybranyKolor;
    }
    
/**
 * Metoda pobierająca liczbę całkowitą od użytkonika
 * @return Wybór indeksu figury z listy
 * @throws Wyjątki Jeśli nie uda się konwersja na typ całkowity, bądź podany indeks jest poza tablicą
 */      
    public Figura jakaFigura() throws Wyjątki{
        System.out.println("Której figury żądasz? Wybierz \"0\", jeżeli nie żądasz niczego.");
        int Wybór;
        Figura WybranaFigura = null;
        
        for (int i = 0; i < 6; i++) System.out.println((i + 1) + ". " + Figura.values()[i + 3] + ".");
        System.out.println("7. " + Figura.values()[10] + ".");
        
        try{
            String WybórStr = skaner.nextLine().trim();
            Wybór = Integer.parseInt(WybórStr);
            if (Wybór < 7 && Wybór > 0) WybranaFigura = Figura.values()[Wybór + 2];
            else if (Wybór == 7) WybranaFigura = Figura.values()[Wybór + 3];
            else if (Wybór == 0) WybranaFigura = null;
            else throw new Wyjątki(Wyjątki.BŁĘDNY_WYBÓR);
        }catch (Exception ex){
            throw new Wyjątki(Wyjątki.BŁĘDNY_WYBÓR);
        }
        return WybranaFigura;
    }
}


