package Leffasovellus;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import fi.jyu.mit.ohj2.WildChars;



/**
 * @author Maria Hyväkkä & Anniina Häkkinen
 * @version 12.3.2020
 *
 */
public class Elokuvat implements Iterable<Elokuva>{
    private int lkm = 0;
    private String tiedostonNimi = "";
    private ArrayList<Elokuva> alkiot = new ArrayList<Elokuva>();
    private boolean muutettu = false;
    
    /**
     * muodostaja
     */
    public Elokuvat() {
        // Attribuuttien oma alustus riittää
    }
    
    /**
     * @param elokuva lisättävän elokuvan viite
     * @example
     * <pre name="test">
     * #import java.util.Iterator;
     * Elokuvat elokuvat = new Elokuvat();
     * Elokuva elo = new Elokuva(), elo2 = new Elokuva();
     * elokuvat.getLkm() === 0;
     * elokuvat.lisaa(elo);
     * elokuvat.getLkm() === 1;
     * elokuvat.lisaa(elo2);
     * elokuvat.getLkm() === 2;
     * Iterator<Elokuva> it = elokuvat.iterator();
     * it.next() === elo;
     * it.next() === elo2;
     * elokuvat.lisaa(elo);
     * elokuvat.getLkm() === 3;
     * </pre>
     */
    public void lisaa(Elokuva elokuva) {
        alkiot.add(elokuva);
        lkm++;
        muutettu = true;
    }
    
    /**
     * Palauttaa sovelluksen elokuvien lukumäärän
     * @return elokuvien lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    /**
     * @param elokuva poistettava elokuva
     * @return 1
     * @example
     * <pre name="test">
     * Elokuvat elokuvat = new Elokuvat();
     * Elokuva elo = new Elokuva(), elo2 = new Elokuva(), elo3 = new Elokuva();
     * elo.rekisteroi(); elo2.rekisteroi(); elo3.rekisteroi();
     * elokuvat.lisaa(elo);
     * elokuvat.lisaa(elo2);
     * elokuvat.lisaa(elo3);
     * elokuvat.getLkm() === 3;
     * elokuvat.poista(elo) === 1;
     * elokuvat.getLkm() === 2;
     * elokuvat.poista(elo3) === 1;
     * elokuvat.getLkm() === 1;
     * </pre>
     */
    public int poista(Elokuva elokuva) {
        alkiot.remove(elokuva);
        lkm--;
        return 1;
    }
    
    /**
     * Korvaa elokuvan tietorakenteessa. Ottaa elokuvan omistukseensa. Etsitään samalla tunnusnumerolla oleva elokuva
     * jos ei löydy, lisätään uutena elokuvana.
     * @param elokuva jota muutetaan 
     */
    public void korvaaTaiLisaa(Elokuva elokuva) {
        int id = elokuva.getId();
        for(int i = 0; i < lkm; i++) {
            if(alkiot.get(i).getId() == id) {
                alkiot.set(i, elokuva);
                muutettu = true;
                return;
            }
        }
        lisaa(elokuva);
    }
    
    /**
     * Palauttaa viitteen i:teen elokuvaan.
     * @param i monennenko elokuvan viite halutaan
     * @return viite elokuvaan, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Elokuva anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot.get(i);
    }
    
    /**
     * Lukee elokuvat tiedostosta.
     * @param nimi nimi sovelluksen nimi, jonka perusteella etsitään oikea hakemisto, jossa tiedosto mistä luetaan
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        tiedostonNimi =  nimi +"/elokuvat.dat";
        File file = new File(tiedostonNimi);
        try ( Scanner sc = new Scanner(new FileInputStream(file.getCanonicalPath()))) {
            while (sc.hasNext()) {
                String rivi = sc.nextLine();
                rivi = rivi.trim();
                if("".equals(rivi) || rivi.charAt(0) == ';') continue;
                Elokuva elokuva = new Elokuva();
                elokuva.parse(rivi);
                lisaa(elokuva);
             }
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + file.getName() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
           }

    /**
     * Tallentaa elokuvat tiedostoon.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
         if ( !muutettu ) return;
         File ftied = new File("leffasovellus/elokuvat.dat");
         try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath()) ) ){

             for (int i = 0; i < getLkm(); i++) {
                 Elokuva elokuva = anna(i);
                 fo.println(elokuva.toString());
             }
         } catch ( FileNotFoundException ex ) {
             throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
         } catch ( IOException ex ) {
             throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
         }

         muutettu = false;
     
     }
    
    /**
     * @param hakuehto millä etsitään
     * @param k kenttä minkä perusteella etsitään
     * @return palauttaa listan elokuvista, jotka toteuttaa ehdon
     */
    public Collection<Elokuva> etsi(String hakuehto, int k) {
        String ehto = "*";
        if(hakuehto != null && hakuehto.length() > 0) ehto = hakuehto;
        int hk = k;
        if(hk < 0) hk = 1;
        Collection<Elokuva> sopivat = new ArrayList<Elokuva>();
        for(Elokuva elokuva : this) {
            if (WildChars.onkoSamat(elokuva.anna(hk), ehto)) sopivat.add(elokuva);
        }
        return sopivat;
    }

    /**
     * Luokka elokuvien iteroimiseksi.
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * #PACKAGEIMPORT
     * #import java.util.*;
     * 
     * Elokuvat elokuvat = new Elokuvat();
     * Elokuva elo = new Elokuva(), elo2 = new Elokuva();
     * elo.rekisteroi(); elo2.rekisteroi();
     *
     * elokuvat.lisaa(elo); 
     * elokuvat.lisaa(elo2); 
     * elokuvat.lisaa(elo); 
     * 
     * StringBuffer ids = new StringBuffer(30);
     * for (Elokuva elokuva : elokuvat)
     *   ids.append(" "+elokuva.getId());           
     * 
     * String tulos = " " + elo.getId() + " " + elo2.getId() + " " + elo.getId();
     *                                                  
     * ids.toString() === tulos; 
     * 
     * ids = new StringBuffer(30);
     * for (Iterator<Elokuva>  i=elokuvat.iterator(); i.hasNext(); ) {
     *   Elokuva elokuva = i.next();
     *   ids.append(" "+elokuva.getId());           
     * }
     * 
     * ids.toString() === tulos;
     * 
     * Iterator<Elokuva>  i=elokuvat.iterator();
     * i.next() == elo  === true;
     * i.next() == elo2  === true;
     * i.next() == elo  === true;
     * 
     * i.next();  #THROWS NoSuchElementException
     *  
     * </pre>
     */
    public class ElokuvatIterator implements Iterator<Elokuva> {
        private int kohdalla = 0;


        /**
         * Onko olemassa vielä seuraava elokuva
         * @see java.util.Iterator#hasNext()
         * @return true jos on vielä elokuvia
         */
        @Override
        public boolean hasNext() {
            return kohdalla < getLkm();
        }


        /**
         * Annetaan seuraava elokuva
         * @return seuraava elokuva
         * @throws NoSuchElementException jos seuraava alkiota ei enää ole
         * @see java.util.Iterator#next()
         */
        @Override
        public Elokuva next() throws NoSuchElementException {
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
     * Palautetaan iteraattori elokuvistaan
     * @return elokuva iteraattori
     */
    @Override
    public Iterator<Elokuva> iterator() {
        return new ElokuvatIterator();
    }
    
    /**
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Elokuvat elokuvat = new Elokuvat();

        Elokuva titanic = new Elokuva(), rush = new Elokuva();
        titanic.rekisteroi();
        titanic.vastaaElokuva();
        rush.rekisteroi();
        rush.vastaaElokuva();

        elokuvat.lisaa(titanic);
        elokuvat.lisaa(rush);

        System.out.println("============= Elokuvat testi =================");

        for (int i = 0; i < elokuvat.getLkm(); i++) {
            Elokuva elokuva = elokuvat.anna(i);
            System.out.println("Elokuva nro: " + i);
            elokuva.tulosta(System.out);
        }


    }

}
