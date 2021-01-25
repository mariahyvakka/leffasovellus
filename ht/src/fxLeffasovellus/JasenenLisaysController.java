package fxLeffasovellus;

import java.net.URL;
import java.util.ResourceBundle;

import Leffasovellus.Henkilo;
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
public class JasenenLisaysController implements ModalControllerInterface<Henkilo>, Initializable {

    @FXML private TextField textAnnaNimi;
    @FXML private BorderPane textLisaaikkuna;
    @FXML private Label labelVirhe;
    
    @FXML void handleOK() {
        if(henkiloKohdalla != null && henkiloKohdalla.getNimi().trim().equals("") ) {
            naytaVirhe("Nimi ei saa olla tyhjä");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }

    @FXML void handlePeruuta() {
        ModalController.closeStage(textLisaaikkuna);
    }

    @Override
    public Henkilo getResult() {
        return henkiloKohdalla;
    }

    @Override
    public void handleShown() {
        textAnnaNimi.requestFocus();
    }

    @Override
    public void setDefault(Henkilo henkilo) {
        henkiloKohdalla = henkilo;
        naytaHenkilo(henkiloKohdalla);
        
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        alusta();
        
    }


    //==================================================================================
    private Henkilo henkiloKohdalla;

    /**
     * Tekee tarvittavat alustukset
     */
    private void alusta() {
        textAnnaNimi.setOnKeyReleased(e -> kasitteleMuutosHenkiloon((TextField)(e.getSource())));        
    }

    /**
     * Käsittelee henkilöön tehdyn muutoksen
     * @param kentta muuttunut kenttä
     */
    private void kasitteleMuutosHenkiloon(TextField kentta) {
      
            if (henkiloKohdalla == null) return;
            String s = textAnnaNimi.getText();
            String virhe = null;
            virhe = henkiloKohdalla.setNimi(s); 
               
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
     * Tulkitsee virheen ja näyttää tarvittaessa tulleen virheen näytölle
     * @param virhe mikä näytetään
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
     * Hakee henkilön tiedot ja tulostaa ne ikkunaan.
     */
    private void naytaHenkilo(Henkilo henkilo) {
        if(henkilo == null) return;
        textAnnaNimi.setText(henkilo.getNimi());
    }

    /**
     * Luodaan henkilön kysymisdialogi ja palautetaan henkilö muutettuna, jos painetaan ok.
     * @param modalitystage mille ollaan modaalisia, null = sovellus
     * @param oletus henkilö, joka näytetään oletuksena
     * @return null jos painetaan cancel, muussa tapauksessa täytetty henkilö-olio.
     */
    public static Henkilo kysyHenkilo(Stage modalitystage, Henkilo oletus) {
        return ModalController.showModal(JasenenLisaysController.class.getResource("JasenenLisays.fxml"), "Elokuva", modalitystage, oletus, null);
    }
        
}
