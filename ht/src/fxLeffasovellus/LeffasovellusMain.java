package fxLeffasovellus;

import Leffasovellus.Sovellus;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


/**
 * @author Maria Hyväkkä & Anniina Häkkinen
 * @version 26.3.2020
 *
 */
public class LeffasovellusMain extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader ldr = new FXMLLoader(getClass().getResource("LeffasovellusGUIView.fxml"));
            final Pane root = ldr.load();
            final LeffasovellusGUIController leffasovellusCtrl = (LeffasovellusGUIController) ldr.getController();
            final Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("leffasovellus.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("Leffasovellus");
            
            Sovellus sovellus = new Sovellus();
            leffasovellusCtrl.setSovellus(sovellus);
            
            primaryStage.setOnCloseRequest((event) -> {
                if(!leffasovellusCtrl.voikoSulkea() ) event.consume();
            });
            
            primaryStage.show();
            leffasovellusCtrl.avaa();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param args Ei käytössä
     */
    public static void main(String[] args) {
        launch(args);
    }
}