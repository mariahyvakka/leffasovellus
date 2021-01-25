package Leffasovellus;

import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Maria Hyväkkä & Anniina Häkkinen
 * @version 25.2.2020
 *
 */
public class Henkilo implements Cloneable {
    private int tunnus;
    private String nimi = "";
    private static int seuraava = 1;
    
    /**
     * Apumetodi, jolla saadaan täytettyä testiarvot henkilölle. 
     */
    public void vastaaHenkilo() {
        nimi = "Anniina";
    }

    
    /**
     * Antaa henkilölle seuraavan rekisterinumeron.
     * @return henkilön uusi tunnusNro
     * @example
     * <pre name="test">
     *   Henkilo henk = new Henkilo();
     *   henk.getTunnusNro() === 0;
     *   henk.rekisteroi();
     *   Henkilo henk2 = new Henkilo();
     *   henk2.rekisteroi();
     *   int n1 = henk.getTunnusNro();
     *   int n2 = henk2.getTunnusNro();
     *   n1 === n2-1;
     * </pre>
     */
    public int rekisteroi() {
        tunnus = seuraava;
        seuraava++;
        return tunnus;
    }
    

    /**
     * @return palauttaa ensimmäisen kentän
     */
    public int ekaKentta() {
        return 1;
    }

    /**
     * @return palauttaa henkilon kenttien määrän
     */
    public int getKenttia() {
        return 2;
    }

    /**
     * Palauttaa k:tta henkilön kenttää vastaavan kysymyksen
     * @param k kuinka monennen kentän kysymys palautetaan (0-alkuinen)
     * @return k:netta kenttää vastaava kysymys
     */
    public String getKysymys(int k) {
        switch ( k ) {
        case 0: return "Tunnus nro";
        case 1: return "nimi";
        default: return "Äääliö";
        }
    }
    
    /**
     * Antaa k:n kentän sisällön merkkijonona
     * @param k monenenko kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     */
    public String anna(int k) {
        switch ( k ) {
        case 0: return "" + tunnus;
        case 1: return "" + nimi;
        default: return "Ei tälläistä ole";
        }
    }
    
    /**
     * Palauttaa henkilon nimen
     * @return henkilön nimen
     * @example
     * <pre name="test">
     * Henkilo henk = new Henkilo();
     * henk.parse("5|Pekka");
     * henk.getNimi() === "Pekka";
     * </pre>
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * @param s nimi joka henkilölle annetaan
     * @return palauttaa onnistuiko nimen laitto vai ei
     */
    public String setNimi(String s) {
        nimi = s;
        return null;
    }

    /**
     * Palauttaa jäsenen tunnusnumeron.
     * @return jäsenen tunnusnumero
     */
    public int getTunnusNro() {
        return tunnus;
    }
    
    /**
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnus = nr;
        if (tunnus >= seuraava) seuraava = tunnus + 1;
    }
    
    /**
     * Tehdään identtinen klooni henkilöstä
     * @return kloonatun henkilön
     * @example
     * <pre name="test">
     * #THROWS CloneNotSupportedException 
     *   Henkilo henk = new Henkilo();
     *   henk.parse("   3  |  Maria");
     *   Henkilo kopio = henk.clone();
     *   kopio.toString() === henk.toString();
     *   henk.parse("   4  |  Anniina");
     *   kopio.toString().equals(henk.toString()) === false;
     * </pre>
     */
    @Override
    public Henkilo clone() throws CloneNotSupportedException { 
        Henkilo kloonattu = (Henkilo) super.clone();
        return kloonattu;
    }
    
    /**
     * Tulostetaan henkilön tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%01d", tunnus, 3) + "  " + nimi);
    }
    
    /**
     * Tulostetaan henkilön tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }

    /**
     * Asettaa henkilön attribuuteille sisällön lukemalla sen rivistä.
     * @param rivi rivi,josta otetaan henkilön tiedot
     * @example
     * <pre name="test">
     * Henkilo henk = new Henkilo();
     * henk.parse("   1   | Anniina   ");
     * henk.getTunnusNro() === 1;
     * 
     * henk.rekisteroi();
     * int n = henk.getTunnusNro();
     * henk.parse(""+(n+4));
     * henk.rekisteroi();
     * henk.getTunnusNro() === n+4+1;
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        nimi = Mjonot.erota(sb, '|', nimi);
    }
    
    /**
     * Palauttaa henkilön tiedot merkkijono joka voidaan tallentaa tiedostoon
     * @return henkilön tiedot merkkijonolla erotettuna
     * @example
     * <pre name="test">
     * Henkilo henk = new Henkilo();
     * henk.parse("2|Maria");
     * henk.toString() === "2|Maria|";
     * </pre>
     */
    @Override
    public String toString() {
         return "" + 
                 getTunnusNro() + "|" +
                 nimi + "|";
     }
    /**
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Henkilo maria = new Henkilo();
        maria.rekisteroi();
        maria.vastaaHenkilo();
        maria.tulosta(System.out);
    }
}
