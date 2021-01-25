package Leffasovellus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Maria Hyväkkä & Anniina Häkkinen
 * @version 25.2.2020 
 */
public class Sovellus {
    private final Henkilot henkilot = new Henkilot();
    private final Elokuvat elokuvat = new Elokuvat();
    private final Katsotut katsotut = new Katsotut();
    
    /**
     * Palautaa kerhon jäsenmäärän
     * @return jäsenmäärä
     */
    public int getHenkiloita() {
        return henkilot.getLkm();
    }
    
    /**
     * Palautaa elokuvien määrän sovelluksessa
     * @return elokuvien määrä
     */
    public int getElokuvia() {
        return elokuvat.getLkm();
    }
    
    /**
     * Palautaa katsottujen elokuvien määrän sovelluksessa
     * @return katsottujen määrä
     */
    public int getKatsottuja() {
        return katsotut.getLkm();
    } 

    /**
     * @param henkilo joka poistetaan
     * @return palauttaa montako jäsentä poistettiin
     */
    public int poista(Henkilo henkilo) {
        if ( henkilo == null ) return 0;
        int ret = henkilot.poista(henkilo.getTunnusNro());
        katsotut.poistaHenkilonkatsotut(henkilo.getTunnusNro());
        return ret;
    }

    /**
     * @param elokuva poistettava elokuva
     * @return poistettujen määrä
     */
    public int poista(Elokuva elokuva) {
        if ( elokuva == null ) return 0;
        int poistetut = elokuvat.poista(elokuva);
        katsotut.poistaElokuvanKatsojat(elokuva.getId());
        return poistetut;
    }

    
    /**
     * @param henkilo lisättävä henkilö
     * @example
     * <pre name="test">
     * 
     * </pre>
     */
    public void lisaa(Henkilo henkilo) {
        henkilot.lisaa(henkilo);
    }
    
    /**
     * Lisätään elokuva
     * @param elokuva lisättävä elokuva
     */
    public void lisaa(Elokuva elokuva)  {
        elokuvat.lisaa(elokuva);
    }
    
    /**
     * Lisätään katsottu
     * @param katsottu lisättävä katsottu elokuva
     */
    public void lisaa(Katsottu katsottu)  {
        katsotut.lisaa(katsottu);
    }
    
    /**
     * @param elokuva elokuva, jonka tietoja muokataan
     */
    public void korvaaTaiLisaa(Elokuva elokuva) {
        elokuvat.korvaaTaiLisaa(elokuva);
    }
    

    /**
     * @param henkilo jonka tietoja muokataan
     */
    public void korvaaTaiLisaa(Henkilo henkilo) {
        henkilot.korvaaTaiLisaa(henkilo);
        
    }
    
    /**
     * @param i monesko jäsen palautetaan
     * @return viitteen i:nteen jäseneen
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Henkilo annaHenkilo(int i) throws IndexOutOfBoundsException {
        return henkilot.anna(i);
    }
    
    /**
     * @param i monesko elokuva palautetaan
     * @return viitteen i:nteen elokuvaan
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Elokuva annaElokuva(int i) throws IndexOutOfBoundsException {
        return elokuvat.anna(i);
    }
    
    /**
     * @param i monesko katsottu elokuva palautetaan
     * @return viitteen i:nteen katsottuun
     * @throws IndexOutOfBoundsException jos i väärin
     */
    public Katsottu annaKatsottu(int i) throws IndexOutOfBoundsException {
        return katsotut.anna(i);
    }
    /**
     * Luetaan henkilöt ja elokuvat tiedostoista
     * @param nimi sovelluksen nimi, jonka perusteella osataan etsiä oikea tiedosto
     * @throws SailoException jos ei pystytä lukemaan
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        henkilot.lueTiedostosta(nimi);
        elokuvat.lueTiedostosta(nimi);
        katsotut.lueTiedostosta(nimi);
    }
    
    /**
     * @param hakuehto millä etsitään
     * @param k kenttä jonka perusteella etsitään
     * @return palauttaa collectionin henkilöistä
     */
    public Collection<Henkilo> etsiHenkilo(String hakuehto, int k){
        return henkilot.etsi(hakuehto, k);
    }
    
    /**
     * @param hakuehto millä etsitään
     * @param k kenttä minkä perusteella etsitään
     * @return palauttaa listan elokuvista jotka toteuttaa hakuehdon
     */
    public Collection<Elokuva> etsiElokuva(String hakuehto, int k){
        return elokuvat.etsi(hakuehto,k);
    }
    
    /**
     * Tallennetaan henkilöt, elokuvat ja katsotut tiedostoon.
     * @throws SailoException jos ei pystytä tallentamaan
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            henkilot.tallenna();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }

        try {
            elokuvat.tallenna();
        } catch ( SailoException ex ) {
            virhe += ex.getMessage();
        } 
        
        try {
            katsotut.tallenna();
        } catch ( SailoException ex ) {
            virhe = ex.getMessage();
        }
        if ( !"".equals(virhe) ) throw new SailoException(virhe);
    }


    
    /**
     * Antaa listan elokuvista, jota tietty henkilö on katsonut.
     * @param henkiloId henkilön id, jonka perusteella etsitään katsotut
     * @return listan henkilön katsomista elokuvista
     * <pre name="test">
     * #import java.util.*;
     * 
     *  Sovellus sovellus = new Sovellus();
     *  Henkilo anniina = new Henkilo(), maria = new Henkilo(), jonne = new Henkilo();
     *  anniina.rekisteroi(); maria.rekisteroi(); jonne.rekisteroi(); sovellus.lisaa(anniina); sovellus.lisaa(maria); sovellus.lisaa(jonne);
     *  int id1 = anniina.getTunnusNro();
     *  int id2 = maria.getTunnusNro();
     *  Elokuva titanic = new Elokuva(); titanic.rekisteroi(); sovellus.lisaa(titanic);
     *  Elokuva grinch = new Elokuva(); grinch.rekisteroi(); sovellus.lisaa(grinch);
     *  Katsottu katsottu = new Katsottu(); katsottu.rekisteroi(); sovellus.lisaa(katsottu); katsottu.setElokuvaId(1); katsottu.setHenkiloId(1);
     *  Katsottu katsottu2 = new Katsottu(); katsottu2.rekisteroi(); sovellus.lisaa(katsottu2); katsottu2.setElokuvaId(2); katsottu2.setHenkiloId(1);
     *  
     *  List<Elokuva> loytyneet;
     *  loytyneet = sovellus.annaElokuvat(id2);
     *  loytyneet.size() === 0; 
     *  loytyneet = sovellus.annaElokuvat(id1);
     *  loytyneet.size() === 2; 
     *  loytyneet.get(0) == titanic === true;
     *  loytyneet.get(1) == grinch === true;
     * </pre> 
     */
    public List<Elokuva> annaElokuvat (int henkiloId) {
        List <Elokuva> henkilonElokuvat = new ArrayList<Elokuva>();
        for(int i = 0; i < katsotut.getLkm(); i++) {
            Katsottu tutkittava = katsotut.anna(i);
            if ((tutkittava.getHenkiloId()) == henkiloId) {
                for(int j = 0; j < elokuvat.getLkm(); j++) {
                    Elokuva tutkittavaElokuva = elokuvat.anna(j);
                    if((tutkittavaElokuva.getId()) == tutkittava.getElokuvaId()) henkilonElokuvat.add(tutkittavaElokuva); 
                }
            }
        }
        return henkilonElokuvat;
    }
    

    
    /**
     * Testiohjelma kerhosta
     * @param args ei käytössä
     */
    public static void main(String args[]) {
        Sovellus sovellus = new Sovellus();

        Henkilo maria = new Henkilo(), anniina = new Henkilo();

            anniina.rekisteroi();
            anniina.vastaaHenkilo();
            maria.rekisteroi();
            maria.vastaaHenkilo();

            sovellus.lisaa(anniina);
            sovellus.lisaa(maria);

            System.out.println("============= Sovelluksen testi =================");

            for (int i = 0; i < sovellus.getHenkiloita(); i++) {
                Henkilo henkilo = sovellus.annaHenkilo(i);
                System.out.println("Jäsen paikassa: " + i);
                henkilo.tulosta(System.out);
            }

        Elokuva titanic = new Elokuva(), rush = new Elokuva();
        titanic.rekisteroi();
        titanic.vastaaElokuva();
        rush.rekisteroi();
        rush.vastaaElokuva();

        sovellus.lisaa(titanic);
        sovellus.lisaa(rush);

        System.out.println("============= Sovelluksen testi 2 =================");

        for (int i = 0; i < sovellus.getElokuvia(); i++) {
            Elokuva elokuva = sovellus.annaElokuva(i);
            System.out.println("Elokuva paikassa " + i);
            elokuva.tulosta(System.out);
        }


        Katsottu katsottu1 = new Katsottu(), katsottu2 = new Katsottu();
        katsottu1.rekisteroi();
        katsottu1.vastaaKatsottu();
        katsottu2.rekisteroi();
        katsottu2.vastaaKatsottu();

        sovellus.lisaa(katsottu1);
        sovellus.lisaa(katsottu2);

        System.out.println("============= Sovelluksen testi 3 =================");

        for (int i = 0; i < sovellus.getKatsottuja(); i++) {
            Katsottu elokuva = sovellus.annaKatsottu(i);
            System.out.println("Katsottu paikassa " + i);
            elokuva.tulosta(System.out);
        }
        
        System.out.println("============= Sovelluksen testi 4 =================");
        
        int id = anniina.getTunnusNro();
        List <Elokuva> anniinanKatsotut = sovellus.annaElokuvat(id);
        
        for(int i = 0; i < anniinanKatsotut.size(); i++) {
            Elokuva tutkittava = anniinanKatsotut.get(i);
            System.out.println(anniina.getNimi() + " on katsonut elokuvan " + tutkittava.getNimi());
        }

    }

}    
