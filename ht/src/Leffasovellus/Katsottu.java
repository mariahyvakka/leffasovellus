package Leffasovellus;

import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Maria Hyväkkä & Anniina Häkkinen
 * @version 15.3.2020
 *
 */
public class Katsottu {
        private int henkiloId;
        private int elokuvaId;
        private static int seuraava = 1;
        private int tunnus;
        
        /**
         * Apumetodi, jolla saadaan täytettyä testiarvot katsotulle. 
         */
        public void vastaaKatsottu() {
            henkiloId = 1;
            elokuvaId = 1;
        }

        
        /**
         * Antaa katsotulle seuraavan rekisterinumeron.
         * @return katsotun uusi tunnusNro
         * @example
         * <pre name="test">
         *   Katsottu kat = new Katsottu();
         *   kat.getTunnusNro() === 0;
         *   kat.rekisteroi();
         *   Katsottu kat2 = new Katsottu();
         *   kat2.rekisteroi();
         *   int n1 = kat.getTunnusNro();
         *   int n2 = kat2.getTunnusNro();
         *   n1 === (n2-1);
         * </pre>
         */
        public int rekisteroi() {
            tunnus = seuraava;
            seuraava++;
            return tunnus;
        }
        
        /**
         * @return katsotun henkilöId:n
         */
        public int getHenkiloId() {
            return henkiloId;
        }

        /**
         * @return katsotun elokuvaId:n
         * @example
         * <pre name="test">
         * Katsottu kat = new Katsottu();
         * kat.parse("1|3|4");
         * kat.getElokuvaId() === 4;
         * </pre>
         */
        public int getElokuvaId() {
            return elokuvaId;
        }
        
        /**
         * Palauttaa katsotun tunnusnumeron.
         * @return katsotun tunnusnumero
         */
        public int getTunnusNro() {
            return tunnus;
        }
        
        /**
         * Asettaa tunnusnumeron ja samalla varmistaa että
         * seuraava numero on aina suurempi kuin tähän mennessä suurin.
         * @param nr asetettava tunnusnumero
         */
        private void setTunnus(int nr) {
            tunnus = nr;
            if (tunnus >= seuraava) seuraava = tunnus + 1;
        }
        
        /**
         * @param id elokuva id
         */
        public void setElokuvaId(int id) {
            elokuvaId = id;
        }
        
        /**
         * @param id elokuva id
         */
        public void setHenkiloId(int id) {
           henkiloId = id;
        }
        /**
         * Tulostetaan katsotun tiedot
         * @param out tietovirta johon tulostetaan
         */
        public void tulosta(PrintStream out) {
            out.println(String.format("%01d", tunnus, 3) + "  " + henkiloId + "  " + elokuvaId);
        }
        
        /**
         * Tulostetaan katsotun tiedot
         * @param os tietovirta johon tulostetaan
         */
        public void tulosta(OutputStream os) {
            tulosta(new PrintStream(os));
        }
        
        /**
         * Asettaa katsotun attribuuteille sisällön lukemalla sen rivistä.
         * @param rivi rivi,josta otetaan henkilön tiedot
         * @example
         * <pre name="test">
         * Katsottu kat = new Katsottu();
         * kat.parse(" 1 | 4 |6");
         * kat.getTunnusNro() === 1; 
         * kat.rekisteroi();
         * int n = kat.getTunnusNro();
         * kat.parse(""+(n+20));
         * kat.rekisteroi();
         * kat.getTunnusNro() === n+20+1;
         * kat.toString()     === "" + (n+20+1) + "|4|6|";
         * </pre>
         */
        public void parse(String rivi) {
            StringBuilder sb = new StringBuilder(rivi);
            setTunnus(Mjonot.erota(sb, '|', getTunnusNro()));
            henkiloId = Mjonot.erota(sb, '|', henkiloId);
            elokuvaId = Mjonot.erota(sb, '|', elokuvaId);
        }
        
        /**
         * Palauttaa katsotun elokuvan tiedot merkkijonona joka voidaan tallentaa tiedostoon
         * @return katsotun elokuvan | -merkillä erotettuna
         * @example
         * <pre name="test">
         * Katsottu kat = new Katsottu();
         * kat.parse("   5 | 3   | 8");
         * kat.toString() === "5|3|8|";
         * </pre>
         */
        @Override
        public String toString() {
             return "" + 
                     getTunnusNro() + "|" +
                     henkiloId + "|" +
                     elokuvaId + "|";
         }

        /**
         * @param args ei käytössä
         */
        public static void main(String args[]) {
            Katsottu kElokuva = new Katsottu();
            kElokuva.rekisteroi();
            kElokuva.vastaaKatsottu();
            kElokuva.tulosta(System.out);
        }
    }


