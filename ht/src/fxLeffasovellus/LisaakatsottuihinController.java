package fxLeffasovellus;

import java.net.URL;
import java.util.ResourceBundle;

import Leffasovellus.Henkilo;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author Maria Hyväkkä & Anniina Häkkinen
 * @version 28.4.2020
 *
 */
public class LisaakatsottuihinController implements ModalControllerInterface<Henkilo[]>, Initializable{
    
    @FXML private ListChooser<Henkilo> textNimiLista;
    @FXML private TextField textHenkiloHaku;
    @FXML private AnchorPane textLisaaikkuna;
    
    @FXML void handlePeruuta() {
        taulu[0] = null;
        ModalController.closeStage(textLisaaikkuna);
    }
    
    @FXML
    void handleOK() {
        ModalController.closeStage(textLisaaikkuna);
        
    }
    
    @Override
    public Henkilo[] getResult() {
        Henkilo[] t1 = new Henkilo[1];
        t1[0] = textNimiLista.getSelectedObject();
        taulu = t1;
        return taulu;
    }
    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
        
    }
    @Override
    public void setDefault(Henkilo[] t) {
       taulu = t;
       for(int i = 0; i < taulu.length; i++) {
           textNimiLista.add(taulu[i].getNimi(), taulu[i]);
       }
    }

  
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
    alusta();
    }
    
    

    //==============================================================================
    
     private Henkilo[] taulu;

     /**
      * Tekee tarvittavat alustukset
      */
     private void alusta() {
         textNimiLista.clear();
     }


    /**
     * @param modalitystage mille ollaan modaalisia, null = sovellukselle
     * @param t taulukko henkilöistä, jotka näytetään oletuksena
     * @return palauttaa listan, jossa henkilö, joka valitaan
     */
    public static Henkilo[] kysyKatsottu(Stage modalitystage, Henkilo[] t) {
         return ModalController.showModal(LisaakatsottuihinController.class.getResource("Lisaakatsottuihin.fxml"), "Katsottu", modalitystage, t, null);
    }

}
