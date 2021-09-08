package pis.hue1;
/**
 * Schnittstelle Codec fuer Codec-Implementierungen und CodecGUI (Benutzeroberflaeche)
 *
 * @date 06.12.2020
 */
public interface Codec {
    /**
     * Methode soll den Klartext kodieren.
     *
     * Caesar: Klartext und Losungswort werden benoetigt, um Caesarverschluesselung durchzufuehren.
     * Hierbei werden die Buchstaben des Klartexts selbst veraendert.
     *
     * Wuerfel: Klartext und Losungswort werden benoetigt, um den Doppelwuerfel auszufuehren.
     * Hierbei werden die Buchstaben des Klartexts selbst nicht veraendert.
     * Es wird lediglich eine Spaltentransposition durchgefuehrt.
     *
     * @param   klartext
     * @return  geheimtext
     */
    public String kodiere(String klartext);

    /**
     * Methode soll den Geheimtext dekodieren.
     *
     * Caesar: Geheimtext und Losungswort werden benoetigt, um die Buchstabenvertauschung mit demselben
     * Losungswort zurueck zu vertauschen.
     *
     * Wuerfel: Geheimtext und Losungswort werden benoetigt, um die Spaltenvertauschung mit demselben Losungswort
     * zum Klartext zurueckzufuehren.
     *
     * @param   geheimtext
     * @return
     */
    public String dekodiere(String geheimtext);

    /**
     * Methode soll Losungswort zurueckgeben.
     * @return  losungswort
     */
    public String gibLosung();

    /**
     * Methode soll Losungswort festlegen und eine Exception werfen, wenn diese ungueltig ist.
     * @param   schluessel
     * @throws  IllegalArgumentException
     */
    public void setzeLosung(String schluessel)throws
            IllegalArgumentException;
}
