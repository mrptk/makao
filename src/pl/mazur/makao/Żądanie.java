/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mazur.makao;

/**
 * Klasa żądanie, który może być statusem stołu, gdy ktoś rzuci waleta i obowiązuje przez tyle kolejek, ilu jest graczy.
 * @author ptkma
 */
public class Żądanie{
    private Figura Figura;
    private int Kolejki;
    
    public Żądanie(int LiczbaGraczy){
        boolean JestBłąd = false;
        WprowadzanieDanych Input = new WprowadzanieDanych();
        
        Kolejki = LiczbaGraczy;
        
        do{
            try{
                JestBłąd = false;
                this.Figura = Input.jakaFigura();
            }catch(Wyjątki Wyjątek){
                System.out.println(Wyjątek);
                JestBłąd = true;
            }
        }while(JestBłąd);
    }    
    
    public Figura figura() { return Figura; }
    
    public int ileKolejek() { return Kolejki; }
    
    public void zmniejszKolejki(int OIle) { Kolejki -= OIle; }
}
