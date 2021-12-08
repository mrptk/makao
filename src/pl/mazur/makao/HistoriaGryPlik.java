/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mazur.makao;

import java.io.*;
/**
 * Klasa obsługująca plik historii i zapisywanie w nim gier.
 * @author ptkma
 */
public class HistoriaGryPlik {
    private final String NazwaPliku;
    private OutputStreamWriter Zapisywacz;
    private FileOutputStream StrumieńWyjściowy;
    private final boolean Dopisz;
    
/**
 * Konstruktor pliku z historią
 * @param NazwaPliku Nazwa pliku
 * @param CzyDopisać Dopisywanie do końca
 */    
    public HistoriaGryPlik(String NazwaPliku, boolean CzyDopisać){
        this.NazwaPliku = NazwaPliku;
        Zapisywacz = null;
        Dopisz = CzyDopisać;
    }
    
    public String jakaNazwa() throws WyjątkiPliku { return NazwaPliku; }
    
/**
 * Metoda otwórz() tworzy lub otwiera plik
 * @throws WyjątkiPliku 
 */    
    public void otwórz() throws WyjątkiPliku{
        zamknij();
        try{
            StrumieńWyjściowy = new FileOutputStream(NazwaPliku, Dopisz);
        } catch (FileNotFoundException ex) {
            throw new WyjątkiPliku(WyjątkiPliku.BŁĄD_PODCZAS_TWORZENIA_PLIKU, NazwaPliku);
        }
        
        Zapisywacz = new OutputStreamWriter(StrumieńWyjściowy);
    }

/**
 * Metoda zamknij() zamyka plik
 * @throws WyjątkiPliku 
 */ 
    public void zamknij() throws WyjątkiPliku {
        if (this.Zapisywacz != null){
            try{
                Zapisywacz.close();
            } catch (IOException ex) {
                throw new WyjątkiPliku(WyjątkiPliku.BŁĄD_PODCZAS_ZAMYKANIA, NazwaPliku);
            }
        }
        Zapisywacz = null;
        StrumieńWyjściowy = null;
    }
/**
 * Zapisywanie tekstu do pliku
 * @param Tekst Tekst do zapisania
 * @throws WyjątkiPliku 
 */    
    public void zapisz(String Tekst) throws WyjątkiPliku{        
        if (Zapisywacz == null) throw new WyjątkiPliku(WyjątkiPliku.PLIK_NIEOTWARTY, NazwaPliku);
        
        try {
            Zapisywacz.write(Tekst);
            Zapisywacz.flush();
        } catch (IOException ex) {
            throw new WyjątkiPliku(WyjątkiPliku.BŁĄD_PODCZAS_ZAPISYWANIA, NazwaPliku);
        }
        
    }
}
