package pis.hue1;
import javax.swing.*;
import java.util.*;

/**
 * Doppelwuerfel-Verschluesselung
 *
 * Doppelwuerfel wird als ein für Spione eher ein optimales Hand-Verfahren zur Verschluesselung beschrieben.
 * Es ist ein Verfahren mit einer zweifach angewendeten Spaltentransposition. Dabei werden die einzelnen Zeichen der Botschaft umsortiert.
 *
 * @author  Neha Kandhari
 * @date    06.12.2020
 */
public class Wuerfel implements Codec{
    /**
     * Das aktuelle Losungswort aus {@link #setzeLosung(String)}, um sie fuer die anderen Methoden
     * innerhalb dieser KLasse sichtbar zu machen.
     */
    private String losungswort;

    /**
     * Konstruktor ohne Parameter.
     */
    public Wuerfel(){}

    /**
     * @param losung Konstruktor mit dem Losungswort als Parameter .
     */
    public Wuerfel(String losung){
        this.setzeLosung(losung);
    }

    /**
     * Methode, um einen bestimmten String-Parameter lsw (Losungswort) nach dem Alphabet zu sortieren,
     * begleitet wird er durch einen Integer-Array, dass sich die Positionen
     * der urspruenglichen String-Parameter-Zeichen merkt. Es verfolgt den BubbleSort-Prinzip.
     *
     * Ausgegeben wird der entsprechende, permutierte Integer-Array index.
     *
     * @param   lsw
     * @return  index
     */
    public static int[] sortby(String lsw){

        char[] charLsw = lsw.toCharArray();
        int[] index = new int[lsw.length()];

        for (int i = 0; i < lsw.length(); i++) {
            index[i] = i;
        }
        for (int i = 0; i < lsw.length(); i++) {
            for (int j = lsw.length()-1; j > i; j--) {
                if (charLsw[j] < charLsw[j-1]){

                    char temp = charLsw[j-1];
                    charLsw[j-1] = charLsw[j];
                    charLsw[j] = temp;

                    int temp2 = index[j-1];
                    index[j-1] = index[j];
                    index[j] = temp2;
                }
            }
        } return index;
    }

    /**
     * Methode, die den Parameter Klartext kodiert.
     * Hierfuer wird der Klartext in Zeilen (Substrings) unterteilt und in eine Liste gespeichert.
     * Ausserdem wird die Methode {@link #sortby(String)} benoetigt,
     * um das Losungswort fuer die Spaltentransposition zu verwenden, die in lswindex vorgenommen wurde.
     * Mittels lswindex (Positionen) werden von den Zeilen des Klartexts nacheinander die einzelnen Zeichen
     * zu einem Geheimtext aufgeschrieben.
     *
     * Ausgegeben wird dementsprechend geheimtextStr.
     *
     * @param   klartext
     * @return  geheimtextStr
     * @see     Codec
     */
    @Override
    public String kodiere(String klartext) {
        int[] lswindex = sortby(losungswort.toUpperCase());

        List<String> zeilen = new ArrayList<>();
        int index = 0;
        while (index < klartext.length()) {
            zeilen.add(klartext.substring(index, Math.min(index + losungswort.length(), klartext.length())));
            index += losungswort.length();
        }

        StringBuilder geheimtext = new StringBuilder();
        String geheimtextStr;
        int i = 0;
        while (i<losungswort.length()){

            for (String s : zeilen){
                if (s.equals(zeilen.get(zeilen.size()-1))){
                    s = String.format("%" + "-" + losungswort.length() +"s", s).replace(' ', '_');
                }
                char cs = s.charAt(lswindex[i]);
                geheimtext.append(cs);
            }
            i++;
        }
        geheimtextStr = geheimtext.toString().replaceAll("_", "");

        return geheimtextStr;
    }

    /**
     * Methode, die den Geheimtext als Parameter braucht und in den Klartext 'zurueck' kodiert.
     * Hierfuer wird der Geheimtext abhaengig von den Positionen aus lswindex spaltenweise veraendert, indem es
     * jeweils um die Laenge des Losungswortes von Position zu Position springt. Dazu
     * wird die Methode {@link #sortby(String)} fuer die jeweiligen Positionen benoetigt.
     *
     * Ausgegeben wird dementsprechend klartxt.
     *
     * @param   geheimtext
     * @return  klartext
     * @see     Codec
     */
    @Override
    public String dekodiere(String geheimtext) {
        int[] lswindex = sortby(losungswort.toUpperCase());

        StringBuilder dekodiert = new StringBuilder(geheimtext);
        int spalte = losungswort.length();
        int zeile = (int) Math.ceil(((double) geheimtext.length() / ((double)losungswort.length())));

        int index;
        int counter = 0;
        for(int i = 0; i < spalte; i++){
            for (int j = 0; j < zeile; j++){
                index = j * spalte + lswindex[i];
                if (index < geheimtext.length()){
                    dekodiert.setCharAt(index, geheimtext.charAt(counter));
                    counter++;
                }
            }
        }
        String klartext = dekodiert.toString();

        return klartext;
    }

    /**
     * Methode gibt Losungswort zurueck.
     * @return  losungswort
     */
    @Override
    public String gibLosung() {
        return losungswort;
    }

    /**
     * Methode benoetigt einen Schluessel als Parameter,
     * der jedoch als Voraussetzung nur aus Buchstaben bestehen soll.
     * Dieser Schluessel wird dann als das Losungswort festgesetzt,
     * das in den anderen Methoden in dieser Klasse verwendet wird.
     *
     * @param   schluessel
     * @throws  IllegalArgumentException
     */
    @Override
    public void setzeLosung(String schluessel) throws IllegalArgumentException {
        String schluesselwort;
        try {
            char[] schluesselwortCharArray = schluessel.toCharArray();
            schluesselwort = String.copyValueOf(schluesselwortCharArray);
            for (char c : schluesselwortCharArray){
                if(!Character.isLetter(c)){
                    throw new IllegalArgumentException();
                }
            }
        } catch(IllegalArgumentException e){
            JFrame frame = new JFrame();
            JOptionPane.showMessageDialog(frame, "Nur Buchstaben erlaubt!", "Losungswort inakzeptabel", JOptionPane.WARNING_MESSAGE);
            return;
        }
        this.losungswort = schluesselwort;
    }
}
