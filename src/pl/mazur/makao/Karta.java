
package pl.mazur.makao;

/**
 * Klasa karta, której parametrami są typy wyliczeniowe
 * @author ptkma
 */

public class Karta implements Comparable<Karta>{
    private final Kolor kolor;
    private final Figura figura;
    private final Funkcja funkcja;
    
/**
 * Tworzy kartę o podanej figurze i kolorze. W zależności od figury (i koloru) karty mają różne funkcje.
 * @param Figura
 * @param Kolor 
 */
    public Karta(Figura Figura, Kolor Kolor){
        this.figura = Figura;
        this.kolor = Kolor;
        
        switch (Figura){
            case DWÓJKA:
                this.funkcja = Funkcja.DWIE_KARTY;
            break;
            case TRÓJKA:
                this.funkcja = Funkcja.TRZY_KARTY;
            break;
            case CZWÓRKA:
                this.funkcja = Funkcja.KOLEJKA;
            break;
            case WALET:
                this.funkcja = Funkcja.ŻĄDANIE;
            break;
            case KRÓL:
                if (Kolor == Kolor.KIER || Kolor == Kolor.PIK) this.funkcja = Funkcja.PIĘĆ_KART;
                else this.funkcja = Funkcja.BRAK;
            break;
            case AS:
                this.funkcja = Funkcja.ZMIANA_KOLORU;
            break;
            default:
                this.funkcja = Funkcja.BRAK;
            break;
        }
    } //Koniec konstruktora 
    
    public Kolor kolor(){
        return kolor;
    }
    
    public Figura figura(){
        return figura;
    }
    
    public Funkcja funkcja(){
        return funkcja;
    }
    
    @Override
    public String toString(){
        String Tekst;
        Tekst = "[" + figura + "_" + kolor + "]";
        return Tekst;
    }
/**
 * Przeciążona metoda compareTo() z interfejsu comparable do sortowania kart.
 * @param Inna
 * @return 
 */    
    @Override
    public int compareTo(Karta Inna) {
        int wynik = 0;
        
        if(this.figura.ordinal() < Inna.figura().ordinal()) wynik = 1;
        if(this.figura.ordinal() > Inna.figura().ordinal()) wynik = -1;
        
        return wynik;
    }
    
    @Override
    public boolean equals(Object Inny){
        boolean wynik = false;        
        Karta Inna = (Karta)Inny;
        
        if (Inna == null) return false;
        if(figura.ordinal() == Inna.figura().ordinal() && kolor.ordinal() == Inna.kolor().ordinal()) wynik = true;
        
        return wynik;
    }
}
