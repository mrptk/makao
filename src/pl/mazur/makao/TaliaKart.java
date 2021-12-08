/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mazur.makao;


import java.util.ArrayList;
/**
 * Klasa Talia, posiada chronione pole listę tablicową.
 * @author ptkma
 */
public class TaliaKart {
    
    protected ArrayList<Karta> Karty;    
/**
 * Konstruktor talii, lista nie ma określonej wielkości, gdyż ilość kart u gracza jest zmienna.
 */ 
    public TaliaKart(){
        Karty = new ArrayList<>();             
    }
/**
 * Metoda zabierająca kartę z talii
 * @param Wybór Indeks wybranej karty
 * @return Zabrana karta z talii
 * @throws Wyjątek, gdy podany wybór jest nie do zrealizowania 
 */
    public Karta weź(int Wybór) throws Wyjątki{
        Karta ZabranaKarta;
        
        if (Wybór < 0 || Wybór >= Karty.size()) throw new Wyjątki(Wyjątki.BŁĘDNY_WYBÓR);
        
        ZabranaKarta = Karty.get(Wybór);
        
        Karty.remove(Wybór);
        
        return ZabranaKarta;
    }
/**
 * Metoda sprawdzająca obecność danej karty w talii
 * @param Figura Karta, której obecność jest sprawdzana
 * @return Wartość logiczna, czy ejst czy nie ma karty
 */
    public final boolean czyJest(Figura Figura){
        boolean CzyJest = false;
        
        for(int i = 0; i < Karty.size() && !CzyJest; i++){
            if (Karty.get(i).figura() == Figura) CzyJest = true;
        }
        
        return CzyJest;
    }
    
    @Override
    public String toString(){
        String Tekst = "";
        
        for (int i = 0; i < Karty.size(); i++){
            Tekst += (i + 1) + ". " + Karty.get(i) + " ||| ";
        }
        
        return Tekst;
    }
}
