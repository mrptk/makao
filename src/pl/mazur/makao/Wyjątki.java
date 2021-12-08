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
public class Wyjątki extends Exception {
    public static final int NIEPRAWIDŁOWA_KARTA = 0;
    public static final int BŁĘDNY_WYBÓR = 1;
    public static final int NAZWA_ZAJĘTA = 2;
    public static final int TALIA_PUSTA = 3;
    public static final int BRAK_KART = 4;
    public static final int BŁĘDNA_LICZBA_GRACZY = 5;
    public static final int BŁĄD_NIEZNANY = 6;
    
    private int KodBłędu;
    
    public Wyjątki(int Kod){
        super();        
        KodBłędu = Kod;
        
        if (Kod < NIEPRAWIDŁOWA_KARTA || Kod > BŁĄD_NIEZNANY) KodBłędu = BŁĄD_NIEZNANY;
    }
    
    public int kodBłędu(){ return KodBłędu; }
    
    @Override
    public String toString(){
        String Tekst;
        
        switch (KodBłędu) {
            case 0:
                Tekst = "Nie można zagrać tej karty!";
            break;
            case 1:
                Tekst = "Należy wybrać jedną z podanych pozycji!";
            break;
            case 2:
                Tekst = "Istnieje gracz o takiej nazwie!";
            break;
            case 3:
                Tekst = "Talia jest pusta!";
            break;
            case 4:
                Tekst = "W grze brakuje kart!";
            break;
            case 5:
                Tekst = "Dozwolona liczba graczy to od 2 do 5!";
            break;
            default:
                Tekst = "Błąd nieznany!";
            break;
        }
        
        return Tekst;
    }
}
