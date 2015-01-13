/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kmp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Jagoda Złożoność: Czasowa: Przetwarzanie wstępne: O(m) Wyszukiwanie:
 * O(n) m – długość wzorca, n – długość tekstu Pamięciowa O(m)
 */
public class KMP {

    private final int[] nie_pasujace;
    public static int licz = 0;

    // konstruktor z wywolaniem wyszukania frazy w stringu + inf o położeniu
    public KMP(String text, String fraza, int policz) {

        nie_pasujace = new int[fraza.length()];

        odsiew(fraza);

        int ind = dopasowanie(text, fraza);

        if (ind == -1) {
        } else {
            policz++;
            System.out.println("Fraza odnaleziona w pliku pod indexem " + ind);
        }
        KMP.licz = policz;

    }

    // funkcja wyszukujaca dopasowanie wzorca do podanego stringa

    private int dopasowanie(String text, String fraza) {

        int i = 0, j = 0;

        int lens = text.length();

        int lenp = fraza.length();

        while (i < lens && j < lenp) {

            if (text.charAt(i) == fraza.charAt(j)) {

                i++;

                j++;

            } else if (j == 0) {
                i++;
            } else {
                j = nie_pasujace[j - 1] + 1;
            }

        }

        return ((j == lenp) ? (i - lenp) : -1);

    }

    private void odsiew(String pat) {

        int n = pat.length();

        nie_pasujace[0] = -1;

        for (int j = 1; j < n; j++) {

            int i = nie_pasujace[j - 1];

            while ((pat.charAt(j) != pat.charAt(i + 1)) && i >= 0) {
                i = nie_pasujace[i];
            }

            if (pat.charAt(j) == pat.charAt(i + 1)) {
                nie_pasujace[j] = i + 1;
            } else {
                nie_pasujace[j] = -1;
            }

        }

    }
// funkcja zawiadujaca wyszukiwaniem frazy w pliku 
// zczytanie szciezki i szukanej frazy , pętla po wszystkich liniach pliku
// linia jak ostring przeszukiwany algorytmem KMP

    public static void szukaj() throws IOException {
        Scanner br = new Scanner(System.in);
        System.out.println("Wyszukiwanie algorytmem Knuth Morris Pratt \n");

        System.out.println("Podaj ścieżkę do pliku, który ma zostać przeszukany");
        String text = br.nextLine();

        System.out.println("Podaj frazę do wyszukania, wielkość liter ma znaczenie!");
        String fraza = br.nextLine();

        BufferedReader in = new BufferedReader(new FileReader(new File(text)));
        for (String line = in.readLine(); line != null; line = in.readLine()) {
            KMP kmp = new KMP(line, fraza, licz);
        }
        String line = in.readLine();
        if (licz == 0 && line == null) {
            System.out.println("\n W pliku nie odnaleziono szukanej frazy");
        }
    }

// przykładowe wywołanie wyszukiwania
    public static void main(String[] args) throws IOException {
        szukaj();

    }
}
