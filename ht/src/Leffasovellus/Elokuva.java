package Leffasovellus;

import java.io.*;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Maria Hyväkkä & Anniina Häkkinen
 * @version 12.3.2020
 *
 */
public class Elokuva implements Cloneable {
        private int id;
        private String nimi = "";
        private String ohjaaja = "";
        private int vuosi;
        private String genre = "";
        private int kesto;
        private static int seuraava = 1;
        
        /**
         * Apumetodi, jolla saadaan täytettyä testiarvot elokuvalle. 
         */
        public void vastaaElokuva() {
            nimi = "Titanic";
            ohjaaja = "Cameron";
            vuosi = 1997;
            genre = "draama";
            kesto = 194;
        }

        
        /**
         * rekisteroi elokuvan ja antaa sille id:n
         * @return elokuvan tunnusnumeron
         * @example
         * <pre name="test">
         * Elokuva elokuva = new Elokuva();
         * elokuva.getId() === 0;
         * elokuva.rekisteroi();
         * Elokuva elokuva2 = new Elokuva();
         * elokuva2.rekisteroi();
         * int n1 = elokuva.getId();
         * int n2 = elokuva2.getId();
         * n1 === n2-1;
         * </pre>
         */
        public int rekisteroi() {
            id = seuraava;
            seuraava++;
            return id;
        }
        
        /**
         * Palauttaa elokuvan kenttien lukumäärän
         * @return kenttien lukumäärä
         */
        public int getKenttia() {
            return 6;
        }
        
        /**
         * Palauttaa k:tta elokuvan kenttää vastaavan kysymyksen
         * @param k kuinka monennen kentän kysymys palautetaan (0-alkuinen)
         * @return k:netta kenttää vastaava kysymys
         */
        public String getKysymys(int k) {
            switch ( k ) {
            case 0: return "Tunnus nro";
            case 1: return "nimi";
            case 2: return "ohjaaja";
            case 3: return "genre";
            case 4: return "Ilmestymisvuosi";
            case 5: return "Kesto";
            default: return "Äääliö";
            }
        }


        /**
         * Eka kenttä joka on mielekäs kysyttäväksi
         * @return ekan kentän indeksi
         */
        public int ekaKentta() {
            return 1;
        }
        
        /**
         * @return elokuvan nimen
         * @example
         * <pre name="test">
         * Elokuva elokuva = new Elokuva();
         * elokuva.parse("4|Avatar|James Cameron|2009|fantasia|161 ");
         * elokuva.getNimi() === "Avatar";
         * </pre>
         */
        public String getNimi() {
            return nimi;
        }
        
        /**
         * @return palauttaa ohjaajan nimen
         */
        public String getOhjaaja() {
            return ohjaaja;
        }

        /**
         * @return palauttaa elokuvan genren
         */
        public String getGenre() {
            return genre;
        }

        /**
         * @return palauttaa elokuvan ilmestymisvuoden
         */
        public String getVuosi() {
            return "" + vuosi;
        }

        /**
         * @return palauttaa elokuvan keston
         */
        public String getKesto() {
            return "" + kesto;
        }

        /**
         * Palauttaa elokuvan tunnusnumeron.
         * @return elokuvan tunnusnumero
         */
        public int getId() {
            return id;
        }
        
        /**
         * Asettaa tunnusnumeron ja samalla varmistaa että
         * seuraava numero on aina suurempi kuin tähän mennessä suurin.
         * @param nr asetettava tunnusnumero
         */
        private void setId(int nr) {
            id = nr;
            if (id >= seuraava) seuraava = id + 1;
        }
        
        /**
         * Asettaa elokuvan attribuuteille sisällön lukemalla sen rivistä.
         * @param rivi rivi,josta otetaan henkilön tiedot
         * @example
         * <pre name="test">
         * Elokuva elokuva = new Elokuva();
         * elokuva.parse("4|Avatar|James Cameron|2009|fantasia|161 ");
         * elokuva.getKesto() === "161";
         * elokuva.getId() === 4;
         * </pre>
         */
        public void parse(String rivi) {
            StringBuilder sb = new StringBuilder(rivi);
            setId(Mjonot.erota(sb, '|', getId()));
            nimi = Mjonot.erota(sb, '|', nimi);
            ohjaaja = Mjonot.erota(sb, '|', ohjaaja);
            vuosi = Mjonot.erota(sb, '|', vuosi);
            genre = Mjonot.erota(sb, '|', genre);
            kesto = Mjonot.erota(sb, '|', kesto);
        }
        /**
         * Tulostetaan elokuvan tiedot
         * @param out tietovirta johon tulostetaan
         */
        public void tulosta(PrintStream out) {
            out.println(String.format("%01d", id, 3) + "  " + nimi + "  " + ohjaaja + "  " + vuosi + "  " + genre + "  " + kesto + " minuuttia");
        }
        
        /**
         * Tulostetaan elokuvan tiedot
         * @param os tietovirta johon tulostetaan
         */
        public void tulosta(OutputStream os) {
            tulosta(new PrintStream(os));
        }

        /**
         * Palauttaa elokuvan tiedot merkkijonona jonka voi tallentaa tiedostoon
         * @return elokuvan tiedot tolpilla erotettuna
         * @example
         * <pre name="test">
         * Elokuva elokuva = new Elokuva();
         * elokuva.parse("4|Avatar|James Cameron|2009|fantasia|161");
         * elokuva.toString() === "4|Avatar|James Cameron|2009|fantasia|161|";
         * </pre>
         */
        @Override
        public String toString() {
             return "" + 
                     getId() + "|" +
                     nimi + "|" +
                     ohjaaja + "|" +
                     vuosi + "|" +
                     genre + "|" +
                     kesto + "|" ;
        }
        
        
        /**
         * Tehdään identtinen klooni elokuvasta
         * @return palauttaa kloonatun elokuvan
         * @example
         * <pre name="test">
         * #THROWS CloneNotSupportedException 
         * Elokuva elokuva = new Elokuva();
         * elokuva.parse("6|Titanic|James Cameron|1997|draama|127");
         * Elokuva kopio = elokuva.clone();
         * kopio.toString() === elokuva.toString();
         * elokuva.parse("6|Titanic|James Cameron|1997|fantasia|127");
         * kopio.toString().equals(elokuva.toString()) === false;
         * </pre>
         */
        @Override
        public Elokuva clone() throws CloneNotSupportedException { 
            Elokuva kloonattu = (Elokuva) super.clone();
            return kloonattu;
        }
        

        /**
         * @param nimi elokuvan nimi
         * @return null
         */
        public String setNimi(String nimi) {
            this.nimi = nimi;
            return null;
        }

        /**
         * @param ohjaaja elokuvan ohjaaja
         * @return null
         */
        public String setOhjaaja(String ohjaaja) {
            this.ohjaaja = ohjaaja;
            return null;
        }

        /**
         * @param genre elokuvan tyyli
         * @return null
         */
        public String setGenre(String genre) {
            this.genre = genre;
            return null;
        }

        /**
         * 
         * @param vuosi elokuvan ilmestymisvuosi
         * @return virhe ilmoituksen jos yrittää kirjoittaa vääriä merkkejä vuosilukuun muuten null
         */
        public String setVuosi(String vuosi) {
            if(!vuosi.matches("[0-9]*") ) return "Vuosiluvussa voi olla vain numeroita!";
            if(vuosi.length() != 4) return "Vuosiluvussa on oltava neljä numeroa!";
            int v = Integer.parseInt(vuosi);
            this.vuosi = v;
            return null;
        }

        /**
         * Asettaa elokuvalle sen keston ja tarkistaa, että kestoon saa kirjoittaa vain numeroita
         * @param kesto elokuvan kesto
         * @return null jos ei virheitä, muuten virheilmoituksen jos kestossa laittomia merkkejä
         */
        public String setKesto(String kesto) {
            if(!kesto.matches("[0-9]*") ) return "Kestossa voi olla vain numeroita!";
            int k = Integer.parseInt(kesto);
            this.kesto = k;
            return null;
        }

        /**
         * Antaa k:n kentän sisällön merkkijonona
         * @param k monenenko kentän sisältö palautetaan
         * @return kentän sisältö merkkijonona
         * @example
         * <pre name="test">
         * Elokuva elo = new Elokuva();
         * elo.parse("5|Avatar|James Cameron| fantasia| 2009|161");
         * elo.anna(0) === "5";
         * elo.anna(2) === "James Cameron";
         * elo.anna(5) === "161";
         * </pre>
         */
        public String anna(int k) {
            switch ( k ) {
            case 0: return "" + id;
            case 1: return "" + nimi;
            case 2: return "" + ohjaaja;
            case 3: return "" + genre;
            case 4: return "" + vuosi;
            case 5: return "" + kesto;
            default: return "Ei tälläistä ole";
            }
        }

        
        /**
         * @param args ei käytössä
         */
        public static void main(String args[]) {
            Elokuva Titanic = new Elokuva();
            Titanic.rekisteroi();
            Titanic.vastaaElokuva();
            Titanic.tulosta(System.out);
        }

    }


