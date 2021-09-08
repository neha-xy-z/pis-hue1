package pis.hue1;
import javax.swing.*;

/**
 * Caesar-Verschluesselung
 *
 * Caesarverfahren wird als ein für Spione eher ein unsicheres Hand-Verfahren zur Verschluesselung
 * beschrieben. Bei der Caesar-Verschluesselung wird jeder Buchstabe der Nachricht um eine bestimmte Zahl
 * im Alphabet weitergeschoben. Diese Zahl ist der geheime Schluessel.
 *
 * @author Neha K.
 * @date 06.12.2020
 */
public class Caesar implements Codec{
    /**
     * Das aktuelle Losungswort aus {@link #setzeLosung(String)}, um sie fuer die anderen Methoden
     * innerhalb dieser KLasse sichtbar zu machen.
     */
    private String losungswort;

    /**
     * Methode, die den Klartext als Parameter uebergeben bekommt, um ihn in den Geheimtext umzuwandeln.
     * Hierfuer werden die einzelnen Buchstaben, je nachdem ob sie gross oder klein sind, um die Laenge des Losunswortes
     * im Alphabet nach vorne (Richtung Z/z) verschoben und dann mit dem jeweiligem Buchstaben ersetzt werden.
     * Dazu werden die Buchstaben mit dem ASCII-Code bearbeitet.
     *
     * @param klartext
     * @return geheimtextString
     */
    @Override
    public String kodiere(String klartext){
        int lswLength  = losungswort.length();
        char [] ch = klartext.toCharArray();

        for(int j = 0; j <ch.length; j++) {

            if (ch[j] >= 65 && ch[j] <= 90) {
                if (ch[j] + lswLength > 90) {
                    ch[j] = (char) (ch[j] + lswLength - 26);
                } else {
                    ch[j] = (char) (ch[j] + lswLength);
                }
            } else if (ch[j] >= 97 && ch[j] <= 122) {
                if (ch[j] + lswLength > 122) {
                    ch[j] = (char) (ch[j] + lswLength - 26);
                } else {
                    ch[j] = (char) (ch[j] + lswLength);
                }
            }
        }
        String geheimtextString = String.copyValueOf(ch);
        return geheimtextString;
    }

    /**
     * Methode, die den Geheimtext als Parameter uebergeben bekommt, um ihn in den Klartext umzuwandeln.
     * Hierfuer werden die einzelnen Buchstaben, je nachdem ob sie gross oder klein sind, um die Laenge des Losunswortes
     * im Alphabet nach hinten (Richtung A/a) verschoben und dann mit dem jeweiligem Buchstaben ersetzt werden.
     * Dazu werden die Buchstaben mit dem ASCII-Code bearbeitet.
     *
     * @param geheimtext
     * @return klartextSTring
     */
    @Override
    public String dekodiere(String geheimtext) {

        int lswLength  = losungswort.length();
        char [] ch = geheimtext.toCharArray();

        for(int j = 0; j <ch.length; j++) {

            if (ch[j] >= 65 && ch[j] <= 90) {
                if (ch[j] - lswLength < 65) {
                    ch[j] = (char) (ch[j] - lswLength + 26);
                } else {
                    ch[j] = (char) (ch[j] - lswLength);
                }
            } else if (ch[j] >= 97 && ch[j] <= 122) {
                if (ch[j] - lswLength < 97) {
                    ch[j] = (char) (ch[j] - lswLength + 26);
                } else {
                    ch[j] = (char) (ch[j] - lswLength);
                }
            }
        }
        String klartextString = String.copyValueOf(ch);
        return klartextString;
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