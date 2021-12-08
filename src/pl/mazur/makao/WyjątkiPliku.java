/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mazur.makao;

/**
 *
 * @author ptkma
 */
public class WyjątkiPliku extends Exception {
    
    private int KodBłędu;
    private final String NazwaPliku;
    
    public static final int BRAK_BŁĘDU = 0;
    public static final int BŁĄD_PODCZAS_TWORZENIA_PLIKU = 1;
    public static final int BŁĄD_PODCZAS_ZAPISYWANIA = 2;
    public static final int BŁĄD_PODCZAS_ZAMYKANIA = 3;
    public static final int PLIK_NIEOTWARTY = 4;
    public static final int BŁĄD_NIEZNANY = 5;
    
    public WyjątkiPliku (int Kod, String NazwaPliku){
        super();
        
        KodBłędu = BŁĄD_NIEZNANY;
        this.NazwaPliku = NazwaPliku;
        
        if (Kod >= BRAK_BŁĘDU && Kod < BŁĄD_NIEZNANY ) KodBłędu = Kod;
    }
    
    public int kodBłędu(){ return KodBłędu; }
    
    @Override
    public String toString(){
        
        String Tekst;
        
        Tekst = "Brak wyjątku";
        if(KodBłędu == BŁĄD_PODCZAS_TWORZENIA_PLIKU) Tekst = "Pliku \'" + NazwaPliku + "\' nie udało się utworzyć!";
        if(KodBłędu == BŁĄD_PODCZAS_ZAPISYWANIA) Tekst = "Błąd podczas zapisu do pliku!";
        if(KodBłędu == BŁĄD_PODCZAS_ZAMYKANIA) Tekst = "Błąd podczas zamykania pliku \'" + NazwaPliku + "\'";
        if(KodBłędu == PLIK_NIEOTWARTY) Tekst = "Plik \'" + NazwaPliku + "\' nie jest otwarty!";
        if(KodBłędu == BŁĄD_NIEZNANY) Tekst = "Nieznany wyjątek";
        
        return Tekst;
    }
    
}
