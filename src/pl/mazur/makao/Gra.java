package pl.mazur.makao;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

/**
 * Główna klasa realizująca cały program. Tytułowa gra to makao, której zasady  opierają się na tym, że na stole można położyć tylko kartę
 * o takim samym kolorze lub figurze. Oprócz tego istnieją karty walczne (np. 4 - czekanie kolejki dla następnego gracza) oraz inne
 * zasady, które mają na celu skomplikowanie gry.
 * @author ptkma
 * @version 1.0
 * 
 */

public class Gra {
    private Stół Stół;
    private HistoriaGryPlik Historia;
    private String NazwaPliku;
    
    
/**
 * Kontruktor klasy Gra
 * @param LiczbaGraczy Liczba graczy, którzy uczestniczą w grze
 */
    public Gra(int LiczbaGraczy){
        Stół = new Stół(LiczbaGraczy, this);
        NazwaPliku = String.format("%tF %<tH%<tM%<tS.txt", new Date());
        Historia = new HistoriaGryPlik(NazwaPliku, true);
        
    } // Koniec konstruktora gry
    
/**
 * Metoda tura(), która zapętla metodę kolejka() dla każdego z graczy. Jeśli gracz rzuci Króla Pik, kolejka idzie o jeden do tyłu.
 */
    public void tura(){
        for (int i = 0; i < Stół.Gracze.size(); i++){
            Stół.kolejka(Stół.Gracze.get(i));
        
            if (Stół.KrólPik && Stół.Gracze.size() > 2){
                i -= 2;
                if (i == -2) i = Stół.Gracze.size() - 2;
                System.out.println("Król pik! Kolejka do tyłu!");
                zapisz("Król pik! Kolejka do tyłu!");
                Stół.KrólPik = false;
            }
        }
        zapisz("KONIEC TURY!\n");
        System.out.println("KONIEC TURY!\n" + this);
    } 
/**
 * Metodda czyKoniec() sprawdza, czy gra powinna być kontynuowana, czy wszyscy gracze mają >0 kart.
 * @return Koniec Zmienna logiczna przechowująca informację o tym, czy są jacyś gracze bez kart.
 */    
    public boolean czyKoniec(){
        boolean Koniec = false;
        ArrayList<Gracz> GraczeZwycięzcy = new ArrayList<>();
        String Komunikat = "!!! I PO MAKALE !!! KONIEC GRY !!!\nZwycięzca/y:\n";;
        
        for (Gracz Gracz : Stół.Gracze){
            if (Gracz.jakieKarty().Karty.size() == 0){
                Koniec = true;
                GraczeZwycięzcy.add(Gracz);
            }
        }
        
        for (int i = 0; i < GraczeZwycięzcy.size(); i++) Komunikat += (i + 1) + ". " + GraczeZwycięzcy.get(i).jakaNazwa() + ".\n";

        if (Koniec) {
            System.out.println(Komunikat);
            zapisz("\n" + Komunikat);
        }
        
        return Koniec;
    }
    
    @Override
    public String toString(){
        String Tekst;
        
        Tekst = "\nW grze bierze udział " + Stół.Gracze.size() + " graczy.\n";
        
        for (int i = 0; i < Stół.Gracze.size(); i++){
            Tekst += "Gracz " + (i + 1) + ". " + Stół.Gracze.get(i).jakaNazwa() + ": " + Stół.Gracze.get(i).jakieKarty().Karty.size() + " kart.\n";
        }
        
        Tekst += Stół + "\n"; 
        
        zapisz(Tekst);
        
        return Tekst;
    }    // Koniec przeciążonej metody toString
/**
 * Metoda zapisująca tekst do pliku historii.
 * @param Tekst Tekst do zapisania
 */    
    public void zapisz(String Tekst){
        try{
            Historia.otwórz();
        } catch (WyjątkiPliku WyjątekPliku){
            System.out.println(WyjątekPliku);
        }
        try {
            Historia.zapisz(Tekst);
        } catch (WyjątkiPliku WyjątekPliku) {
            System.out.println(WyjątekPliku + "\nTeksty: \"" + Tekst + "\"Nie zapisano do historii!");
        }
        try{
            Historia.zamknij();
        } catch (WyjątkiPliku WyjątekPliku){
            System.out.println(WyjątekPliku);
        }        
    }
/**
 * Metoda main(), to tutaj użytkownik ustawia liczbę graczy i to tutaj zapętlana jest metoda tura().
 * @param args 
 */
    public static void main(String[] args) {
        // TODO code application logic here
        
        WprowadzanieDanych Input = new WprowadzanieDanych();
        int IluGraczy = 0;
        
        do{
            try{
                IluGraczy = Input.iluGraczy();
            } catch (Wyjątki Wyjątek){
                System.out.println(Wyjątek + "\nJeszcze raz!");
            }
        } while (IluGraczy < 2 || IluGraczy > 5);
        
        Gra makao = new Gra(IluGraczy);
        boolean Koniec = false;
        
        
        System.out.println(makao);
        
        do{
            makao.tura(); // Zapętlenie metody tura
            Koniec = makao.czyKoniec(); // Po każdej turze sprawdzam, czy ktoś ma 0 kart.
        }while(!Koniec);
        
    }    
}


