/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.mazur.makao;

/**
 * Typ wyliczeniowy zawierający figury.
 * @author ptkma
 */
public enum Figura {
    DWÓJKA {public String toString() { return "2"; }},
    TRÓJKA  {public String toString() { return "3"; }}, 
    CZWÓRKA {public String toString() { return "4"; }}, 
    PIĄTKA {public String toString() { return "5"; }},
    SZÓSTKA {public String toString() { return "6"; }},
    SIÓDEMKA  {public String toString() { return "7"; }},
    ÓSEMKA {public String toString() { return "8"; }},
    DZIEWIĄTKA {public String toString() { return "9"; }}, 
    DZIESIĄTKA {public String toString() { return "10"; }},
    WALET {public String toString() { return "W"; }},
    DAMA {public String toString() { return "D"; }},
    KRÓL {public String toString() { return "K"; }},
    AS {public String toString() { return "A"; }}  
}
