package Leffasovellus;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;



/**
 * @author Maria Hyväkkä & Anniina Häkkinen
 * @version 15.3.2020
 *
 */
public class Katsotut {
    private int lkm = 0;
    private String tiedostonNimi = "";
    private final ArrayList<Katsottu> alkiot = new ArrayList<Katsottu>();
    private boolean muutettu = false;
    
    /**
     * muodostaja
     */
    public Katsotut() {
        // Attribuuttien oma alustus riittää
    }
    
    /**
     * @param katsottu lisättävän katsotun viite
     * @example
     * <pre name="test">
     * Katsotut katsotut = new Katsotut();
     * Katsottu kat = new Katsottu(), kat2 = new Katsottu(), kat3 = new Katsottu();
     * katsotut.getLkm() === 0;
     * katsotut.lisaa(kat); katsotut.getLkm() === 1;
     * katsotut.lisaa(kat2); katsotut.lisaa(kat3);
     * katsotut.getLkm() === 3;
     * </pre>
     */
    public void lisaa(Katsottu katsottu) {
        alkiot.add(katsottu);
        lkm++;
        muutettu = true;
    }
    


    /**
     * @param tunnusNro henkilön id, jonka katsotut poistetaan
     * @return palauttaa
     */
    public int poistaHenkilonkatsotut(int tunnusNro) {
        int n = 0;
        for (int i = 0;  i < alkiot.size(); i++) {
            Katsottu elo = alkiot.get(i);
            if ( elo.getHenkiloId() == tunnusNro ) {
                alkiot.remove(elo);
                n++;
            }
        }
        if (n > 0) muutettu = true;
        lkm = lkm - n;
        return n;
        
    }

    /**
     * @param id elokuvan id, jonka mukaan katsottuja poistetaan
     * @return poistettujen määrä
     */
    public int poistaElokuvanKatsojat(int id) {
        int n = 0;
        for (int i = 0;  i < alkiot.size(); i++) {
            Katsottu elo = alkiot.get(i);
            if ( elo.getElokuvaId() == id ) {
                alkiot.remove(elo);
                n++;
            }
        }
        if (n > 0) muutettu = true;
        lkm = lkm - n;
        return n;
    }    
    
    /**
     * Palauttaa viitteen i:teen katsottuun.
     * @param i monennenko katsotun viite halutaan
     * @return viite katsottuun, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella  
     */
    public Katsottu anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm < i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot.get(i);
    }
    
    /**
     * Etsii kaikki elokuvaId:t, jotka ovat samalla henkilöllä.
     * @param numero henkilonumero
     * @return listan elokuvaidstä
     * @example
     * <pre name="test">
     * #import java.util.*;
     * 
     * </pre>
     */
    public List<Integer> annaElokuvat(int numero) {
        List<Integer> loydetyt = new ArrayList<Integer>();
        for(int i = 0; i < alkiot.size(); i++) {
            Katsottu tutkittava = anna(i);
            if ( (tutkittava.getHenkiloId()) == numero) loydetyt.add(tutkittava.getElokuvaId());
        }
        return loydetyt;
    }
    

    /**
     * Lukee katsotut tiedostosta.
     * @param nimi nimi sovelluksen nimi, jonka perusteella etsitään oikea hakemisto, jossa tiedosto mistä luetaan
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        tiedostonNimi = nimi + "/katsotut.dat";
        File file = new File(tiedostonNimi);
        try ( Scanner sc = new Scanner(new FileInputStream(file.getCanonicalPath()))) {
            while (sc.hasNext()) {
                String rivi = sc.nextLine();
                rivi = rivi.trim();
                if("".equals(rivi) || rivi.charAt(0) == ';') continue;
                Katsottu katsottu = new Katsottu();
                katsottu.parse(rivi);
                lisaa(katsottu);
             }
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + file.getName() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }
    }

    /**
     * Tallentaa katsotut tiedostoon.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void tallenna() throws SailoException {
         if ( !muutettu ) return;

         File ftied = new File("leffasovellus/katsotut.dat");

         try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath()) ) ){

             for (int i = 0; i < getLkm(); i++) {
                 Katsottu katsottu = anna(i);
                 fo.println(katsottu.toString());
             }
         } catch ( FileNotFoundException ex ) {
             throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
         } catch ( IOException ex ) {
             throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
         }

         muutettu = false;
     }
    
    /**
     * Palauttaa sovelluksen katsottujen lukumäärän
     * @return katsottujen lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    /**
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Katsotut elokuvat = new Katsotut();

        Katsottu katsottu1 = new Katsottu(), katsottu2 = new Katsottu();
        katsottu1.rekisteroi(); 
        katsottu1.vastaaKatsottu();
        katsottu2.rekisteroi();
        katsottu2.vastaaKatsottu();

        elokuvat.lisaa(katsottu1);
        elokuvat.lisaa(katsottu2);

        System.out.println("============= Katsotut testi =================");

        for (int i = 0; i < elokuvat.getLkm(); i++) {
            Katsottu elokuva = elokuvat.anna(i);
            System.out.println("Katsotun nro listassa: " + i);
            elokuva.tulosta(System.out);
        }
        
        List <Integer>anniinanElokuvat = elokuvat.annaElokuvat(1);
        
        System.out.println(anniinanElokuvat.toString());


    }


}
