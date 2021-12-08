/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mazur.makao;

import java.util.Collections;

/**
 * Klasa Gracz, której reprezentacją są obiekty mogące brać udział w grze.
 * @author ptkma
 */
public class Gracz {
    private String Nazwa;
    private TaliaKart Talia;
    private Gra Gra;
    private int LiczbaKolejekDoOdczekania;

/**
 * Metoda sprawdzająca, ile kolejek musi odczekać dany gracz.
 * @return LiczbaKolejekDoOdczekania 
 */    
    public int ileKolejekMaCzekać() { return LiczbaKolejekDoOdczekania; }
/**
 * Metoda ustawiająca ile kolejek ma czekać dany gracz, po tym jak ktoś wcześniej rzuci czwórkę/czwórki.
 * @param IleKolejek 
 */ 
    public void czekaj(int IleKolejek){ LiczbaKolejekDoOdczekania += IleKolejek; }
    
/**
 * Metoda zmniejszająca kolejki do odczekania o 1.
 */    
    public void zmniejszKolejkiDoOdczekania() { if (LiczbaKolejekDoOdczekania > 0) LiczbaKolejekDoOdczekania--; }
    
/**
 * Konstruktor Gracza
 * @param Nazwa Nazwa gracza
 * @param Gra Gra, w której uczestniczy
 * @param Talia Talia kart, którą posiada gracz (0 kart przed rozdaniem)
 */    
    public Gracz(String Nazwa, Gra Gra){
        this.Nazwa = Nazwa;
        this.Talia = new TaliaKart();
        this.Gra = Gra;
    }
/**
 * getNazwa
 * @return Nazwa Nazwa gracz
 */
    public String jakaNazwa(){
        return Nazwa;
    }
/**
 * getTalia
 * @return Nazwa Nazwa gracz
 */    
    public TaliaKart jakieKarty(){
        return Talia;
    }
    
    @Override
    public String toString(){
        String Tekst;
        
        Tekst = Nazwa + " posiada karty:\n" + Talia;
        
        return Tekst;        
    }
/**
 * Przekazanie karty do talii gracza
 * @param Karta Karta, którą się przekazuje
 */    
    public void dajKartę(Karta Karta){
        Talia.Karty.add(0, Karta);
        Collections.sort(Talia.Karty);
    }
    
/**
 * Metoda pozwalająca graczom wybrać kartę do zagrania ze swojej talii.
 * @return WybranaKarta
 * @throws Wyjątki 
 */    
    public Karta wybierzKartę() throws Wyjątki{
        WprowadzanieDanych Input = new WprowadzanieDanych();
        Karta WybranaKarta = null;
        
        System.out.println(Talia);
        
        int Wybór = Input.numerKarty();
        
        if (Wybór > Talia.Karty.size()) throw new Wyjątki(Wyjątki.BŁĘDNY_WYBÓR);
        else if (Wybór < 0) throw new Wyjątki(Wyjątki.BŁĘDNY_WYBÓR);
        else if (Wybór == 0) {
            System.out.println("Gracz " + this.Nazwa + " pasuje.");
            Gra.zapisz("Gracz " + this.Nazwa + " pasuje.\n");
        }
        else WybranaKarta = Talia.weź(Wybór - 1);
            
        return WybranaKarta;
    }
    
}
            
            
            
        
    
