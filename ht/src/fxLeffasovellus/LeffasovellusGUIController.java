package fxLeffasovellus;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

import Leffasovellus.Elokuva;
import Leffasovellus.Henkilo;
import Leffasovellus.Katsottu;
import Leffasovellus.SailoException;
import Leffasovellus.Sovellus;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
/**
 * @author Maria Hyväkkä & Anniina Häkkinen
 * @version 28.4.2020
 *
 */
public class LeffasovellusGUIController implements Initializable {
    @FXML private ListChooser<Henkilo> chooserHenkilot;
    @FXML private ListChooser<Elokuva> textElokuvaLista;
    @FXML private ComboBoxChooser<String> textHakuehto;
    @FXML private ComboBoxChooser<String> textHenkiloHakuehto;
    @FXML private Label textVirheHenkilo;
    @FXML private Label textVirheElokuva;
    @FXML private Label textVirheTiedot;
    @FXML private ScrollPane panelElokuva;
    @FXML private TextField textHenkiloHaku;
    @FXML private TextField textElokuvaHaku;
    @FXML private TextField textElokuvanNimi;
    @FXML private TextField textOhjaaja;
    @FXML private TextField textGenre;
    @FXML private TextField textIlmestymisvuosi;
    @FXML private TextField textKesto;
    
    
    @FXML void handleLisaaKatsottuihin() {
     
       lisaaKatsottuihin();
    }

    @FXML void handleLopeta() {
        tallenna();
        Platform.exit();
    }
    
    @FXML void handleElokuvaHaku() {
        hae(0);
    }

    @FXML void handleHaku() {
        haeHenk(0);
    }

    @FXML void handlePoistaElokuva() {
        poistaElokuva();
    }

    @FXML void handlePoistaHenkilo() {
        poistaHenkilo();
    }

    @FXML void handleTallenna() {
        tallenna();
    }
    
    @FXML
    void handleOhjeet() {
        ohjeet();
    }

    @FXML void handleUusiElokuva() {
        uusiElokuva();
    }

    @FXML void handleUusiHenkilo() {
       uusiHenkilo();
    }
    
    @FXML void handleMuokkaaElokuvaa() {
        muokkaaElokuvaa();
        haeElokuva();
    }

    @FXML void handleMuokkaaHenkiloa() {
        muokkaaHenkiloa();
    }
    
    @FXML
    void handleNaytaKaikkiElokuvat() {
        haeElokuva();
        haeHenkilo();
        tyhjennaElokuvanTiedot(tiedot);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();      
    }
    
    //============================================================================================================
    
    private Sovellus sovellus;
    private String kerhonnimi;
    private Elokuva elokuvaKohdalla;
    private Henkilo henkiloKohdalla;
    private TextField[] tiedot;
    private static Elokuva apuElokuva = new Elokuva();
    private static Henkilo apuHenkilo = new Henkilo();
    
    
    /**
     * Alustetaan sovellus tyhjentämällä ensin ruudut ja tekemällä muut alustukset
     */
    private void alusta() {
        textHakuehto.clear();
        for (int k = apuElokuva.ekaKentta(); k < apuElokuva.getKenttia(); k++) 
            textHakuehto.add(apuElokuva.getKysymys(k), null); 
        textHakuehto.getSelectionModel().select(0);
        
        textHenkiloHakuehto.clear();
        for (int k = apuHenkilo.ekaKentta(); k < apuHenkilo.getKenttia(); k++) 
            textHenkiloHakuehto.add(apuHenkilo.getKysymys(k), null); 
        textHenkiloHakuehto.getSelectionModel().select(0);
        
        chooserHenkilot.clear();
        chooserHenkilot.addSelectionListener(e -> naytaHenkilo());
        textElokuvaLista.clear();
        textElokuvaLista.addSelectionListener(e -> naytaElokuva());
        
        tiedot = new TextField[] { textElokuvanNimi, textOhjaaja, textGenre, textIlmestymisvuosi, textKesto};
    }
    
    /**
     * tallentaa uudet tiedot 
     */
    private void tallenna() {
        try {
            sovellus.tallenna();
        } catch (SailoException e) {
          Dialogs.showMessageDialog("tallennuksessa tuli virhe" + e.getMessage());
        }
    }
    
    
    /**
     * Alustaa kerhon lukemalla sen valitun nimisestä tiedostosta
     * @param nimi tiedosto josta kerhon tiedot luetaan
     */
    private void lueTiedosto() {
        kerhonnimi = "leffasovellus";
        setTitle("Sovellus - " + kerhonnimi);

        try {
            sovellus.lueTiedostosta(kerhonnimi);
            haeHenkilo();
            haeElokuva();
            haeKatsottu(0);
        } catch (SailoException e) {
          Dialogs.showMessageDialog(e.getMessage());
        }
        }

    private void setTitle(String title) {
        ModalController.getStage(textHenkiloHaku).setTitle(title);
    }
    
    /**
     * näyttää mahdoliset ohjeet näytölle
     */
    private void ohjeet() {
        Dialogs.showMessageDialog("Ei osata vielä näyttää");
    }
    
    /**
     * Näyttää virheen jos henkilöiden haussa tapahtuu joku virhe
     * @param virhe joka näytetään
     */
    @SuppressWarnings("unused")
    private void naytaVirheHenkilo(String virhe) {
        naytaVirhe(virhe, textVirheHenkilo);
    }
    /**
     * Näyttää virheen jos elokuvien haussa tapahtuu joku virhe
     * @param virhe joka näytetään
     */
    @SuppressWarnings("unused")
    private void naytaVirheElokuva(String virhe) {
        naytaVirhe(virhe, textVirheElokuva);
    }
    
    /**
     * Näyttää tulleen virheen näyttöön
     * @param virhe mikä näytetään
     * @param virhekentta mihin virhe näytetään
     */
    private void naytaVirhe(String virhe, Label virhekentta) {
        if ( virhe == null || virhe.isEmpty() ) {
            virhekentta.setText("");
            virhekentta.getStyleClass().removeAll("virhe");
            return;
        }
        virhekentta.setText(virhe);
        virhekentta.getStyleClass().add("virhe");
    }
    
    /**
     * Asetetaan kontrollerin sovellusviite
     * @param sovellus sovellus mistä on kyse
     */
    public void setSovellus(Sovellus sovellus) {
        this.sovellus = sovellus;
    }
    
    
    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkea sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
    
    
    /**
     * Kun sovellus käynnistetään luetaan tiedostosta näytettävät tiedot
     */
    public void avaa() {
        lueTiedosto();
    }
    

    /**
     * Poistaa valitun henkilön, sekä tämän katsotut.
     */
    private void poistaHenkilo() {
        Henkilo henkilo = henkiloKohdalla;
        if ( henkilo == null || sovellus.getHenkiloita() == 0 ) return;
        if ( !Dialogs.showQuestionDialog("Poistetaanko", "Poistetaanko henkilö: " + henkilo.getNimi(), "Kyllä", "Peruuta")) return;
        sovellus.poista(henkilo);
        int index = chooserHenkilot.getSelectedIndex();
        haeHenkilo();
        chooserHenkilot.setSelectedIndex(index);
        
    }

    /**
     * Muokkaa valittua henkilöä.
     */
    private void muokkaaHenkiloa() {
        Henkilo henkilonKohdalla = chooserHenkilot.getSelectedObject();
        try {
        Henkilo uusi = JasenenLisaysController.kysyHenkilo(null, henkilonKohdalla.clone());
        if (uusi == null) return;
        sovellus.korvaaTaiLisaa(uusi);
        haeHenkilo();
        } catch (CloneNotSupportedException e) {
         e.printStackTrace();
        }
     }
    
    /**
     * Näyttää listasta valitun henkilon katsotut elokuvat.
     */
    private void naytaHenkilo() {
        henkiloKohdalla = chooserHenkilot.getSelectedObject();

        if (henkiloKohdalla == null) return;
        textElokuvaLista.clear();
        tyhjennaElokuvanTiedot(tiedot);
        haeKatsottu(henkiloKohdalla.getTunnusNro());
    }
    
    /**
     * Hakee jäsenet listaksi näyttöön.
     */
    private void haeHenkilo() {
        chooserHenkilot.clear();

        //int index = 0;
        for (int i = 0; i < sovellus.getHenkiloita(); i++) {
            Henkilo henkilo = sovellus.annaHenkilo(i);
            // if (henkilo.getTunnusNro() == hnro) index = i;
            chooserHenkilot.add(henkilo.getNimi(), henkilo);
        }
        //chooserHenkilot.setSelectedIndex(index); 
    }
    
    /**
     * Lisää uuden henkilön sovellukseen.
     */
    public void uusiHenkilo() {
        Henkilo uusi = new Henkilo();
        uusi = JasenenLisaysController.kysyHenkilo(null, uusi);
        if (uusi == null) return;
        uusi.rekisteroi();
        sovellus.lisaa(uusi);
        haeHenkilo();
    }
 
    /**
     * @param hnro numero jolla etsitään
     */
    private void haeHenk(int hnro){
        int enumero = hnro;
        if(enumero <= 0) {
            Henkilo hkohdalla = henkiloKohdalla;
            if(hkohdalla != null) enumero = hkohdalla.getTunnusNro();
        }
        int k = textHakuehto.getSelectionModel().getSelectedIndex() + apuHenkilo.ekaKentta();
        String ehto = textHenkiloHaku.getText();
        if(ehto.indexOf('*') < 0) ehto = "*" + ehto + "*";
        
        chooserHenkilot.clear();
        
        int index = 0;
        Collection<Henkilo> henkilot;
        henkilot = sovellus.etsiHenkilo(ehto, k);
        int i = 0;
        for(Henkilo henkilo : henkilot) {
            if(henkilo.getTunnusNro() == enumero) index = i;
            chooserHenkilot.add(henkilo.getNimi(), henkilo);
            i++;
        }
        chooserHenkilot.setSelectedIndex(index);
    }
    
    
    // Elokuva-aliohjelmat
    //===============================================================================
    
    /**
     * Poistaa valitun elokuvan, sekä katsotut-olioista ne, joissa tämä elokuva esiintyy.
    */
    private void poistaElokuva() {
        Elokuva elokuva = elokuvaKohdalla;
        if ( elokuva == null || sovellus.getElokuvia() == 0  ) return;
        if ( !Dialogs.showQuestionDialog("Poisetaanko", " Poistetaanko elokuva: " + elokuva.getNimi(), "Kyllä", "Peruuta")) return;
        sovellus.poista(elokuva);
        int index = textElokuvaLista.getSelectedIndex();
        haeElokuva();
        textElokuvaLista.setSelectedIndex(index);
        
    } 


    /**
     * Tyhjentää elokuvan tietokentät.
     * @param t taulukko tiedoista
     */
    private void tyhjennaElokuvanTiedot(TextField[] t) {
        for(int i = 0; i < t.length; i++) {
            t[i].clear();
        }
        
    }
    
    /**
     * Hakee elokuvista oikeat hakuehdot täyttävät elokuvat listaan 
     * @param enro hakukentän numero millä haetaan
     */
    private void hae(final int enro) {
        int enumero = enro;
        if(enumero <= 0) {
            Elokuva ekohdalla = elokuvaKohdalla;
            if(ekohdalla != null) enumero = ekohdalla.getId();
        }
        int k = textHakuehto.getSelectionModel().getSelectedIndex() + apuElokuva.ekaKentta();
        String ehto = textElokuvaHaku.getText();
        if(ehto.indexOf('*') < 0) ehto = "*" + ehto + "*";
        
        textElokuvaLista.clear();
        
        int index = 0;
        Collection<Elokuva> elokuvat;
        elokuvat = sovellus.etsiElokuva(ehto, k);
        int i = 0;
        for(Elokuva elokuva : elokuvat) {
            if(elokuva.getId() == enumero) index = i;
            textElokuvaLista.add(elokuva.getNimi(), elokuva);
            i++;
        }
        textElokuvaLista.setSelectedIndex(index);
    }
    
    /**
     * Muokkaa valittua elokuvaa.
     */
    private void muokkaaElokuvaa() {
        elokuvaKohdalla = textElokuvaLista.getSelectedObject();
        if (elokuvaKohdalla == null) return;
        try {
           Elokuva elokuva = ElokuvanLisaysController.kysyElokuva(null, elokuvaKohdalla.clone());
           if (elokuva == null) return;
           sovellus.korvaaTaiLisaa(elokuva);
           haeElokuva();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }   
    }
    
    /**
     * Näyttää listan kaikista sovelluksen elokuvista näytölle.
     */
    public void haeElokuva() {
        textElokuvaLista.clear();

        //int index = 0;
        for (int i = 0; i < sovellus.getElokuvia(); i++) {
            Elokuva elokuva = sovellus.annaElokuva(i);
            // if (elokuva.getId() == id) index = i;
            textElokuvaLista.add(elokuva.getNimi(), elokuva);
        }
        //textElokuvaLista.setSelectedIndex(index);
    }
    
    /**
     * Lisää uuden elokuvan.
     */
    public void uusiElokuva() {
        Elokuva uusi = new Elokuva();
        uusi = ElokuvanLisaysController.kysyElokuva(null, uusi);
        if (uusi == null) return;
        uusi.rekisteroi();
        sovellus.lisaa(uusi);
        haeElokuva();
    }
    
    /**
     * Näyttää valitun elokuvan tiedot
     */
    private void naytaElokuva() {
        elokuvaKohdalla = textElokuvaLista.getSelectedObject();

        if (elokuvaKohdalla == null) return;

        ElokuvanLisaysController.naytaElokuva(tiedot, elokuvaKohdalla);
    }
    
    //katsotut elokuvat
    //====================================================================   

    /**
     * Hakee valitun henkilön katsotut elokuvat listaan 
     * @param tunnusNro henkilön tunnusnumero, kenen katsottuja etsitään
     */
    public void haeKatsottu(int tunnusNro) {
        List <Elokuva> henkilonKatsotut = sovellus.annaElokuvat(tunnusNro);
        for (int i = 0; i < henkilonKatsotut.size(); i++) {
            Elokuva katsottuElokuva = henkilonKatsotut.get(i);
            textElokuvaLista.add(katsottuElokuva.getNimi(), katsottuElokuva);
        }
    }
    
     
    /**
     * Luo uuden Katsottu-olion elokuvasta, joka on valittu.
     */
    public void lisaaKatsottuihin() {
        elokuvaKohdalla = textElokuvaLista.getSelectedObject();
        if(elokuvaKohdalla == null) return;
        Henkilo[] t = new Henkilo[sovellus.getHenkiloita()];
        for(int i = 0; i < t.length; i++ ) {
            t[i] = sovellus.annaHenkilo(i);
        }
        Henkilo[] katsoja = LisaakatsottuihinController.kysyKatsottu(null, t);
        Henkilo henkilo = katsoja[0];
        if(henkilo == null) return;
        Katsottu katsottu = new Katsottu();
        katsottu.setElokuvaId(elokuvaKohdalla.getId());
        katsottu.setHenkiloId(henkilo.getTunnusNro());
        katsottu.rekisteroi();
        sovellus.lisaa(katsottu);
    }
    
}