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
public enum Kolor {
    KIER {public String toString() { return String.valueOf("\u2665"); }},
    KARO {public String toString() { return String.valueOf("\u2666"); }},
    PIK {public String toString() { return String.valueOf("\u2660"); }},
    TREFL {public String toString() { return String.valueOf("\u2663"); }}
}
