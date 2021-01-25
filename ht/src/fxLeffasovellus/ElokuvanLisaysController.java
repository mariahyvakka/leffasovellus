package fxLeffasovellus;

import java.net.URL;
import java.util.ResourceBundle;

import Leffasovellus.Elokuva;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


/**
 * @author Maria Hyväkkä & Anniina Häkkinen
 * @version 28.4.2020
 *
 */
public class ElokuvanLisaysController implements ModalControllerInterface<Elokuva>, Initializable {

    @FXML private TextField textElokuvanNimi;
    @FXML private TextField textOhjaaja;
    @FXML private TextField textGenre;
    @FXML private TextField textIlmestymisvuosi;
    @FXML private TextField textKesto;
    @FXML private BorderPane textLisaaikkuna;
    @FXML private Label labelVirhe;

    @FXML void handleOK() {
        if(elokuvaKohdalla != null && elokuvaKohdalla.getNimi().trim().equals("") ) {
            naytaVirhe("Nimi ei saa olla tyhjä");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }

    @FXML void handlePeruuta() {
        elokuvaKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }

    @Override
    public Elokuva getResult() {
        return elokuvaKohdalla;
    }

    @Override
    public void handleShown() {
        textElokuvanNimi.requestFocus();
    }
    
    @Override
    public void setDefault(Elokuva elokuva) {
        elokuvaKohdalla = elokuva;
        naytaElokuva(elokuvaKohdalla);
    }
    
    

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
        
    }

    //=========================================================================================
    private TextField[] kentat;
    private Elokuva elokuvaKohdalla;
    
    /**
     * Alustaa tarvittavat tiedot kentat taulukkoon 
     */
    private void alusta() {
        kentat = new TextField[] {textElokuvanNimi, textOhjaaja, textGenre, textIlmestymisvuosi, textKesto};
        int i= 0;
        for(TextField kentta : kentat) {
            final int k = ++i;
            kentta.setOnKeyReleased(e -> kasitteleMuutosElokuvaan(k, (TextField)(e.getSource())));
        }
    }
    
    /**
     * Näyttää virheen ikkunan alareunassa.
     * @param virhe viesti mikä näytetään jos tulee virhe jostain
     */
    private void naytaVirhe(String virhe) {
        if ( virhe == null || virhe.isEmpty() ) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().removeAll("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");        
    }
    
    /**
     * Hakee annetun elokuvan tiedot ja tulostaa ne tietokenttiin.
     * @param tiedot taulukko, jossa tietokentät
     * @param elokuva elokuva, jonka tiedot näytetään
     */
    public static void naytaElokuva(TextField[] tiedot, Elokuva elokuva) {
        if(elokuva == null) return;
        tiedot[0].setText(elokuva.getNimi());
        tiedot[1].setText(elokuva.getOhjaaja());
        tiedot[2].setText(elokuva.getGenre());
        tiedot[3].setText(elokuva.getVuosi());
        tiedot[4].setText(elokuva.getKesto());
        
    }
    
    /**
     * Kutsuu toista naytaElokuva-aliohjelmaa, joka tulostaa annetun elokuvan tiedot.
     * @param elokuva jolle haetaan tiedot
     */
    public void naytaElokuva(Elokuva elokuva) {
        naytaElokuva(kentat, elokuva);
        
    }
    
    /**
     * Käsitellään elokuvaan tullut muutos
     * @param kentta muuttunut kenttä
     */
    private void kasitteleMuutosElokuvaan(int kentanNumero, TextField kentta) {
        if (elokuvaKohdalla == null) return;
        String s = kentta.getText();
        String virhe = null;
        switch (kentanNumero) {
           case 1 : virhe = elokuvaKohdalla.setNimi(s); break;
           case 2 : virhe = elokuvaKohdalla.setOhjaaja(s); break;
           case 3 : virhe = elokuvaKohdalla.setGenre(s); break;
           case 4 : virhe = elokuvaKohdalla.setVuosi(s); break;
           case 5 : virhe = elokuvaKohdalla.setKesto(s); break;
           default:
        }
        if (virhe == null) {
            Dialogs.setToolTipText(kentta,"");
            kentta.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(kentta,virhe);
            kentta.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }
    }
    
    /**
     * Luodaan elokuvan kysymisdialogi ja palautetaan elokuva muutettuna, jos painetaan ok.
     * @param modalitystage mille ollaan modaalisia, null = sovellukselle
     * @param oletus elokuva, joka näytetään oletuksena
     * @return null jos painetaan cancel, muussa tapauksessa täytetty elokuva-olio.
     */
    public static Elokuva kysyElokuva(Stage modalitystage, Elokuva oletus) {
        return ModalController.showModal(ElokuvanLisaysController.class.getResource("ElokuvanLisays.fxml"), "Elokuva", modalitystage, oletus, null);
    }

    

    
}
