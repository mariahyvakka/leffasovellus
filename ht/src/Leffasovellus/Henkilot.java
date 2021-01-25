package Leffasovellus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

import fi.jyu.mit.ohj2.WildChars;

/**
 * @author Maria Hyväkkä & Anniina Häkkinen
 * @version 25.2.2020
 *
 */
public class Henkilot implements Iterable<Henkilo> {
    private static final int MAX_JASENIA   = 10;
    private int              lkm           = 0;
    private String           tiedostonNimi = "";
    private Henkilo          alkiot[]      = new Henkilo[MAX_JASENIA];
    private boolean          muutettu      = false;
    
    /**
     * muodostaja
     */
    public Henkilot() {
        // Attribuuttien oma alustus riittää
    }
    
    /**
     * Lisätään uusi henkilö taulukkoon. Jos taulukko on täysi, kloonataan se isompana.
     * @param henkilo lisättävän jäsenen viite
     */
    public void lisaa(Henkilo henkilo)  {
        if (lkm >= alkiot.length) {
            alkiot = kloonaaTaulukko(alkiot);
        }
        alkiot[lkm] = henkilo;
        lkm++;
        muutettu = true;
    }
    
    /**
     * Kloonataan Henkilöt-taulukko isompana.
     * @param t taulukko, joka kloonataan
     * @return uuden isomman taulukon
     */
    public Henkilo[] kloonaaTaulukko(Henkilo[] t) {
        Henkilo[] uusiTaulukko = new Henkilo[t.length + 2]; 
        for(int i = 0; i < t.length; i++) {
            uusiTaulukko[i] = t[i];
        }
        return uusiTaulukko;
    }
    
    
    /**
     * @param id poistettavan henkilön id numero
     * @return palauttaa 1 jos poistettiin ja 0 jos ei löydy
     * @example
     * <pre name="test">
     * Henkilot henkilot = new Henkilot();
     * Henkilo henk = new Henkilo(), henk2 = new Henkilo(), henk3 = new Henkilo();
     * henk.rekisteroi(); henk2.rekisteroi(); henk3.rekisteroi();
     * int id = henk.getTunnusNro();
     * henkilot.lisaa(henk); henkilot.lisaa(henk2); henkilot.lisaa(henk3);
     * henkilot.poista(id+1) === 1;
     * henkilot.getLkm() === 2;
     * henkilot.poista(id) === 1;
     * henkilot.getLkm() === 1;
     * henkilot.poista(id+5) === 0;
     * henkilot.getLkm() === 1;
     * </pre>
     */
    public int poista(int id) { 
        int ind = etsiId(id); 
        if (ind < 0) return 0; 
        lkm--; 
        for (int i = ind; i < lkm; i++) 
            alkiot[i] = alkiot[i + 1]; 
        alkiot[lkm] = null; 
        muutettu = true; 
        return 1; 
    }
    
    /**
     * @param id numero, jonka mukaan etsitään
     * @return palauttaa henkilön indeksin jos löytyy tai -1 jos ei löydy
     */
    public int etsiId(int id) { 
        for (int i = 0; i < lkm; i++) 
            if (id == alkiot[i].getTunnusNro()) return i; 
        return -1; 
    }

    /**
     * @param id jolla poistetaan
     
    public void poistaHenkilonElokuvat(int id) {
        
    }*/
    
    /**
     * @param henkilo henkilö, johon tehdään muutoksia
     */
    public void korvaaTaiLisaa(Henkilo henkilo) {
        int id = henkilo.getTunnusNro();
        for(int i = 0; i < lkm; i++) {
            if(alkiot[i].getTunnusNro() == id) {
                alkiot[i] = henkilo;
                muutettu = true;
                return;
            }
        }
            lisaa(henkilo);
    }
    
    
    /**
     * Palauttaa viitteen i:teen jäseneen.
     * @param i monennenko jäsenen viite halutaan
     * @return viite jäseneen, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Henkilo anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    /**
     * Lukee jäsenistön tiedostosta.
     * @param nimi sovelluksen nimi, jonka perusteella etsitään oikea hakemisto, josta löytyy tiedosto, mistä luetaan
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        tiedostonNimi = nimi + "/henkilot.dat";
        File file = new File(tiedostonNimi);
        try ( Scanner sc = new Scanner(new FileInputStream(file.getCanonicalPath()))) {
            while (sc.hasNext()) {
                String rivi = sc.nextLine();
                rivi = rivi.trim();
                if("".equals(rivi) || rivi.charAt(0) == ';') continue;
                Henkilo henkilo = new Henkilo();
                henkilo.parse(rivi);
                lisaa(henkilo);
             }
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + file.getName() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }


    /**
     * Tallentaa jäsenistön tiedostoon.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
        if ( !muutettu ) return;

        File ftied = new File("leffasovellus/henkilot.dat");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath()) ) ){

            for (int i = 0; i < getLkm(); i++) {
                Henkilo henkilo = anna(i);
                fo.println(henkilo.toString());
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }

        muutettu = false;
    
    }
    
    /**
     * Palauttaa sovelluksen henkilöiden lukumäärän
     * @return henkilöiden lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    /**
     * @param hakuehto millä etsitään
     * @param k kenttä jonka perusteella etsitään
     * @return palauttaa listan henkiloista
     */
    public Collection<Henkilo> etsi(String hakuehto, int k) {
        String ehto = "*";
        if(hakuehto != null && hakuehto.length() > 0) ehto = hakuehto;
        int hk = k;
        if(hk < 0) hk = 1;
        Collection<Henkilo> sopivat = new ArrayList<Henkilo>();
        for(Henkilo henkilo : this) {
            if (WildChars.onkoSamat(henkilo.anna(hk), ehto)) sopivat.add(henkilo);
        }
        return sopivat;
    }
    
    
    /**
     * Luokka henkilöiden iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Henkilot henkilot = new Henkilot();
     * Henkilo henk = new Henkilo(), henk2 = new Henkilo();
     * henk.rekisteroi(); henk2.rekisteroi();
     *
     * henkilot.lisaa(henk); 
     * henkilot.lisaa(henk2); 
     * henkilot.lisaa(henk); 
     * 
     * StringBuffer ids = new StringBuffer(30);
     * for (Henkilo henkilo : henkilot)
     *   ids.append(" "+henkilo.getTunnusNro());           
     * 
     * String tulos = " " + henk.getTunnusNro() + " " + henk2.getTunnusNro() + " " + henk.getTunnusNro();
     *                                                  
     * ids.toString() === tulos; 
     * 
     * ids = new StringBuffer(30);
     * for (Iterator<Henkilo>  i=henkilot.iterator(); i.hasNext(); ) {
     *   Henkilo henkilo = i.next();
     *   ids.append(" "+henkilo.getTunnusNro());           
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Henkilo>  i=henkilot.iterator();
     * i.next() == henk  === true;
     * i.next() == henk2  === true;
     * i.next() == henk  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */
    public class HenkilotIterator implements Iterator<Henkilo> {
        private int kohdalla = 0;


        /**
         * Onko olemassa vielä seuraava henkilö
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä henkilöitä
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava henkilö
         * @return seuraava henkilö
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Henkilo next() throws NoSuchElementException {
            if ( !hasNext() ) throw new NoSuchElementException("Ei oo");
            return anna(kohdalla++);
        }


        /**
         * Tuhoamista ei ole toteutettu
         * @throws UnsupportedOperationException aina
         * @see java.util.Iterator#remove()
         */
        @Override
        public void remove() throws UnsupportedOperationException {
            throw new UnsupportedOperationException("Me ei poisteta");
        }
    }


    /**
     * Palautetaan iteraattori henkilöistään.
     * @return henkilö iteraattori
     */
    @Override
    public Iterator<Henkilo> iterator() {
        return new HenkilotIterator();
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Henkilot henkilot = new Henkilot();

        Henkilo maria = new Henkilo(), anniina = new Henkilo();
        maria.rekisteroi();
        maria.vastaaHenkilo();
        anniina.rekisteroi();
        anniina.vastaaHenkilo();

            henkilot.lisaa(maria);
            henkilot.lisaa(anniina);

            System.out.println("============= Jäsenet testi =================");

            for (int i = 0; i < henkilot.getLkm(); i++) {
                Henkilo henkilo = henkilot.anna(i);
                System.out.println("Jäsen nro: " + i);
                henkilo.tulosta(System.out);
            }

    }
}
