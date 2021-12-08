/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mazur.makao;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Klasa talia kart do gry, rozszerzająca klasę talia kart o określoną ilość elementów (ensureCapacity) oraz kilka innych metod.
 * @author ptkma
 */
public class TaliaKartDoGry extends TaliaKart {
/**
 * Konstruktor talii kart do gry, po 13 figur dla kazdego koloru.
 */    
    public TaliaKartDoGry(){
        super();        
        Karty.ensureCapacity(52);
        
        for (int i = 0; i < 1; i++){
            for (Kolor K : Kolor.values()){
                for (Figura F : Figura.values()){
                    Karta PojedynczaKarta = new Karta(F, K);
                    Karty.add(PojedynczaKarta);
                }
            }
        }
        Collections.shuffle(Karty); //tasowanie
    }    
    
/**
 * Metoda weźOstatnią() zwraca ostatnią kartę z talii.
 * @return Ostatnia karta z talii
 * @throws Wyjątki Jeśli talia jest pusta
 */
    public Karta weźOstatnią() throws Wyjątki{
        Karta OstatniaKarta;
        
        if (Karty.size() < 1) throw new Wyjątki(Wyjątki.TALIA_PUSTA);
        
        OstatniaKarta = Karty.get(Karty.size() - 1);
        
        Karty.remove(Karty.size() - 1);
        
        return OstatniaKarta;
    }
    
/**
 * Metoda weźOstatnią() zwraca ostatnią niewaleczną kartę z talii (trzeba ją połozyć jako pierwszą na stosie na stole).
 * @return Ostatnia niewaleczna karta z talii
 */
    public Karta weźOstatniąNiewaleczną(){
        boolean CzyWalecznaKarta;
        Karta OstatniaKartaZTalii = null; 
        
        do{
            CzyWalecznaKarta = false;
            try{
                OstatniaKartaZTalii = this.weźOstatnią();
            }catch (Exception ex){
                System.out.println(ex);
            }
            if (OstatniaKartaZTalii.funkcja() != Funkcja.BRAK){
                CzyWalecznaKarta = true;
                this.Karty.add(0, OstatniaKartaZTalii);
            }
        }while(CzyWalecznaKarta);
        
        return OstatniaKartaZTalii;
    }
    
/** 
 * Metoda rozdająca graczom z listy określoną liczbę kart z talii.
 * @param IleKart Po ile kart do rozdania
 * @param Gracze Lista graczy
 */    
    public void rozdaj(int IleKart, ArrayList<Gracz> Gracze){
        for (int i = 0; i < IleKart; i++){
            for (Gracz gracz : Gracze){
                try{
                    gracz.dajKartę(weźOstatnią());
                }catch (Exception ex){
                    System.out.println(ex);
                }
            }
        }
    }
}
