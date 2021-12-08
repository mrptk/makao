/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mazur.makao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * Klasa stół, na której obiekcie odbywa się cała gra.
 * @author ptkma
 */ 
public class Stół {
    private Stack<Karta> Karty;
    private TaliaKartDoGry Talia;
    private Kolor Kolor;
    private Figura Figura;
    private Żądanie Żądanie;
    private int PulaKolejek;
    private int PulaKart;
    protected Gra Gra;
    protected ArrayList<Gracz> Gracze;
    protected boolean KrólPik = false;

    /**
     * Konstruktor tworzy nowy stół dla określonej liczby graczy i przeznaczony dla konkretnej gry. Rozdaje też karty. Karty rozdawane są z talii (lista tablicowa)
     * a kładzione są na stosie.
     * @param LiczbaGraczy
     * @param Gra 
     */
    public Stół(int LiczbaGraczy, Gra Gra) {        
        Karty = new Stack<Karta>();        
        Talia = new TaliaKartDoGry();
        Żądanie = null;
        Gracze = new ArrayList<>();
        Gracze.ensureCapacity(LiczbaGraczy);
        
        this.Gra = Gra;
        
        WprowadzanieDanych Input = new WprowadzanieDanych();
        boolean JestBłąd = false;
        
        for (int i = 0; i < LiczbaGraczy; i++){
            do{
                try{
                    JestBłąd = false;
                    System.out.println("Gracz nr. " + (i + 1));
                    Gracz gracz = new Gracz(Input.nowyGracz(Gracze), Gra); 
                    Gracze.add(gracz);                   
                }catch (Wyjątki Wyjątek){
                    System.out.println(Wyjątek);
                    JestBłąd = true;
                }
            } while (JestBłąd);
        }
        
        Talia.rozdaj(5, Gracze);
        
        połóż(Talia.weźOstatniąNiewaleczną());
    }
    
/**
 * Jedna z głównych metod. Sprawdza, czy wybrana karta może w danym momencie zostać położona na stole. Zmienia status stołu w zależności od położonej karty
 * @param Karta Karta do położenia
 * @throws Wyjątki 
 */    
    public void zagraj(Karta Karta) throws Wyjątki{
        WprowadzanieDanych Input = new WprowadzanieDanych();
        
        if (Żądanie != null){
            if (Karta.figura() != Karty.peek().figura()){
                if (Karta.figura() == Żądanie.figura()) this.połóż(Karta); else throw new Wyjątki(Wyjątki.NIEPRAWIDŁOWA_KARTA);
            }
            else {
                this.połóż(Karta);
            }
            
        } // co w wypadku żądania
        else if (PulaKolejek > 0 && Karta.figura() != Figura.CZWÓRKA) throw new Wyjątki(Wyjątki.NIEPRAWIDŁOWA_KARTA); // co w wypadku kolejek do odczekania
        else if (PulaKart > 0){
            if (Karta.figura() == Figura.DWÓJKA || Karta.figura() == Figura.TRÓJKA){
                if (Karta.kolor() == Kolor || Karta.figura() == Figura) this.połóż(Karta);
                else throw new Wyjątki(Wyjątki.NIEPRAWIDŁOWA_KARTA);
            }
            else if (Karta.figura() == Figura.KRÓL){
                if ((Karta.kolor() == Kolor.KIER || Karta.kolor() == Kolor.PIK) && (Karta.kolor() == Kolor || Karta.figura() == Figura)){
                    this.połóż(Karta);
                }
                else throw new Wyjątki(Wyjątki.NIEPRAWIDŁOWA_KARTA);
            }
            else throw new Wyjątki(Wyjątki.NIEPRAWIDŁOWA_KARTA);
        } // co w wypadku kart do ciągnięcia
        else {
            if ((Karta.kolor() != Kolor) && (Karta.figura() != Figura)) throw new Wyjątki(Wyjątki.NIEPRAWIDŁOWA_KARTA); 
            else{
                this.połóż(Karta);
            }
        }//koniec ifów, koniec części weryfikującej kartę
        
        switch (Karta.funkcja()){
            case ZMIANA_KOLORU:
                boolean JestBłąd = false;
                do{
                    try{
                        JestBłąd = false;
                        Kolor = Input.nowyKolor();
                    }catch(Wyjątki Wyjątek){
                        System.out.println(Wyjątek);
                        JestBłąd = true;
                    }
                }while(JestBłąd);    
            break;
            
            case ŻĄDANIE:
                Żądanie = new Żądanie(Gracze.size());
                if (Żądanie.figura() == null) Żądanie = null;
                if (Żądanie != null) {
                    System.out.println("NOWE ŻĄDANIE: " + Żądanie.figura());
                    Gra.zapisz("NOWE ŻĄDANIE: " + Żądanie.figura());
                }
            break;
            
            case KOLEJKA:
                PulaKolejek++;
            break;
            
            case DWIE_KARTY:
                PulaKart += 2;
            break;
            
            case TRZY_KARTY: 
                PulaKart += 3;
            break;
            
            case PIĘĆ_KART:
                PulaKart += 5;
            break;
        } // Koniec części zmieniającej status stołu
    }
    
    /**
     * Druga najważniejsza metoda. Steruje przebiegiem kolejki w zależności od dokonanych wyborów oraz statusu stołu. Zmienia status Gracza oraz Stołu.
     * @param Gracz Gracz, którego jest kolejka
     */
    public void kolejka(Gracz Gracz){        
        boolean JestBłąd = false;
        int KtóraKartaWKolejce = 1; // kiedy ma więcej niż 1 karte takiej samej figury, może je rzucic, dlatego kolejka jest zapętlona
        Karta WybranaKarta = null;

        System.out.println( "\nKOLEJ GRACZA " + Gracz.jakaNazwa() + ":" + this);
        Gra.zapisz("\nKOLEJ GRACZA " + Gracz.jakaNazwa() + ":" + this + "\n");
        
        if (Gracz.ileKolejekMaCzekać() > 0){
            System.out.println("Gracz " + Gracz.jakaNazwa() + " czeka kolejkę.");
            Gra.zapisz("Gracz " + Gracz.jakaNazwa() + " czeka kolejkę.\n");
            Gracz.zmniejszKolejkiDoOdczekania();
            System.out.println("Pozostała liczba kolejek: " + Gracz.ileKolejekMaCzekać() + ".");
            Gra.zapisz("Pozostała liczba kolejek: " + Gracz.ileKolejekMaCzekać() + ".\n"); 
        } //jeśli musi odczekać kolejki
        else{
            do{
                do{
                    JestBłąd = false;
                    try{ 
                        WybranaKarta = Gracz.wybierzKartę();
                       
                        if (WybranaKarta != null){
                            if (KtóraKartaWKolejce > 1 && WybranaKarta.figura() != Karty.peek().figura()) 
                                throw new Wyjątki(Wyjątki.NIEPRAWIDŁOWA_KARTA); // jeśli ma więcej takich samych figur
                            zagraj(WybranaKarta);
                            System.out.println("Gracz " + Gracz.jakaNazwa() + " zagrał " + WybranaKarta + "."); 
                            Gra.zapisz("Gracz " + Gracz.jakaNazwa() + " zagrał " + WybranaKarta + ".\n");
                        } // zagranie karty
                        else if (KtóraKartaWKolejce < 2 && PulaKolejek == 0 && PulaKart == 0){
                            System.out.println("Gracz " + Gracz.jakaNazwa() + " ciągnie kartę.");
                            Gra.zapisz("Gracz " + Gracz.jakaNazwa() + " ciągnie kartę.\n");
                            Gracz.dajKartę(Talia.weźOstatnią()); // jeśli spasuje i nie rzuci żadnej karty
                        }
                        else if (KtóraKartaWKolejce < 2 && PulaKolejek > 0 && PulaKart == 0){
                            Gracz.czekaj(PulaKolejek);
                            System.out.println("Gracz " + Gracz.jakaNazwa() + " stoi " + PulaKolejek + " kolejek.\n"
                                    + "Gracz " + Gracz.jakaNazwa() + " nic nie zagrał i czeka pierwszą kolejkę.");
                            Gra.zapisz("Gracz " + Gracz.jakaNazwa() + " stoi " + PulaKolejek + " kolejek.\n"
                                    + "Gracz " + Gracz.jakaNazwa() + " nic nie zagrał i czeka pierwszą kolejkę.\n");
                            PulaKolejek = 0;
                            Gracz.zmniejszKolejkiDoOdczekania();
                            System.out.println("Pozostała liczba kolejek: " + Gracz.ileKolejekMaCzekać() + ".\n");
                            Gra.zapisz("Pozostała liczba kolejek: " + Gracz.ileKolejekMaCzekać() + ".\n");
                        } // jeśli nie miał czwórki, gdy  puli były kolejki do odczekania
                        else if (KtóraKartaWKolejce < 2 && PulaKolejek == 0 && PulaKart > 0){
                            System.out.println("Gracz " + Gracz.jakaNazwa() + " ciągnie " + PulaKart + " kart(y).");
                            Gra.zapisz("Gracz " + Gracz.jakaNazwa() + " ciągnie " + PulaKart + " kart(y).\n");
                            
                            do{
                                try {
                                    Gracz.dajKartę(Talia.weźOstatnią());
                                    PulaKart--;
                                } catch (Wyjątki Wyjątek){
                                    if (Wyjątek.kodBłędu() == Wyjątki.TALIA_PUSTA){
                                        Talia.Karty = zabierzKartyZeStołu();
                                        if (Talia.Karty.isEmpty()) throw new Wyjątki(Wyjątki.BRAK_KART); // jeśli talia się skończy
                                    }
                                }
                            
                            } while (PulaKart > 0);
                        } // ciągnięcie więcej niż 1 kart
                    }catch(Wyjątki Wyjątek){
                        JestBłąd = true;
                        
                        if (Wyjątek.kodBłędu() == Wyjątki.NIEPRAWIDŁOWA_KARTA){
                            Gracz.dajKartę(WybranaKarta);
                            System.out.println(Wyjątek);
                            Gra.zapisz(Wyjątek.toString());
                        }
                        if (Wyjątek.kodBłędu() == Wyjątki.BRAK_KART){
                            Talia = new TaliaKartDoGry();
                            System.out.println(Wyjątek + " Stworzono nową talię. Powtórz ruch!");
                            Gra.zapisz(Wyjątek.toString() + " Stworzono nową talię. Powtórz ruch!\n");
                        }
                        if (Wyjątek.kodBłędu() == Wyjątki.TALIA_PUSTA){
                            Talia = new TaliaKartDoGry();
                            System.out.println(Wyjątek + " Stworzono nową talię. Powtórz ruch!");
                            Gra.zapisz(Wyjątek.toString() + " Stworzono nową talię. Powtórz ruch!\n");
                        }
                    }
                }while(JestBłąd);

                KtóraKartaWKolejce++;

            }while (WybranaKarta != null && Gracz.jakieKarty().czyJest(WybranaKarta.figura()));
        }
        
        if (Talia.Karty.isEmpty()) { Talia.Karty = zabierzKartyZeStołu(); }
        
        if (Karty.peek().equals(new Karta(Figura.KRÓL, Kolor.PIK))) KrólPik = true;
            
        if (Żądanie != null) {
            Żądanie.zmniejszKolejki(1);
            if (Żądanie.ileKolejek() < 0) Żądanie = null;
        }
        
        if (Gracz.jakieKarty().Karty.size() == 1) {
            System.out.println("\nMAKAO!!! Gracz " + Gracz.jakaNazwa() + " ma tylko jedną kartę!");
            Gra.zapisz("\nMAKAO!!! Gracz " + Gracz.jakaNazwa() + " ma tylko jedną kartę!\n");
        }
    } // Koniec kolejki
    
    public void połóż(Karta Karta){
        Karty.push(Karta);
        Kolor = Karta.kolor();
        Figura = Karta.figura();
    }
    
    public Karta ostatniaKarta(){
        Karta Ostatnia = Karty.peek();
        return Ostatnia;
    }
    
    public ArrayList<Karta> zabierzKartyZeStołu(){
        Karta OstatniaKarta = Karty.pop();
        ArrayList<Karta> KartyZeStołu = new ArrayList<>();
        
        while (!Karty.empty()){
            KartyZeStołu.add(Karty.pop());
        }
        
        Collections.shuffle(KartyZeStołu);
        
        Karty.push(OstatniaKarta);
        
        return KartyZeStołu;
    }
    
    @Override
    public String toString(){
        String Tekst;
        
        if (!Karty.isEmpty()) Tekst = "\nFigura na stole: .............. " + Figura + 
                                      "\nKolor na stole: ............... " + Kolor;
        
        else Tekst = "Stół jest pusty!";
        
        if (Żądanie != null) Tekst +=  "\nŻądanie: ...................... " + Żądanie.figura();
        if (PulaKart != 0) Tekst +=    "\nPula kart do wzięcia: ......... " + PulaKart;
        if (PulaKolejek != 0) Tekst += "\nPula kolejek do odczekania: ... " + PulaKolejek;        
        
        return Tekst;
    }
}


    
    