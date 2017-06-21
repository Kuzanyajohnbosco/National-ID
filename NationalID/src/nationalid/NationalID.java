/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nationalid;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author basasagerald
 */
public class NationalID extends Application {
     public Stage stage;
    private final double MINIMUM_WINDOW_WIDTH = 1300.0;
    private final double MINIMUM_WINDOW_HEIGHT = 700.0;
    public NationalID() {
        
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            stage = primaryStage;
            primaryStage.centerOnScreen();
            stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
           
            stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
            stage.setTitle("NATIONAL ID FORM");
            gotoLogin();
           
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(NationalID.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     void gotoWelcomeScreen(){
          try {
           
            NextsceneFXMLController welcome;
            welcome = (NextsceneFXMLController) replaceSceneContent("nextsceneFXML.fxml");
            welcome.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(NationalID.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        }
     
     
  void gotoLogin() {
        try {
            stage.setFullScreen(false);
            FXMLDocumentController login;
            login = (FXMLDocumentController) replaceSceneContent("FXMLDocument.fxml");
            login.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(NationalID.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * @param args the command line arguments
     */
   private Initializable replaceSceneContent(String fxml) throws Exception {
             
        FXMLLoader loader = new FXMLLoader();
        InputStream in = NationalID.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(NationalID.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        } 
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }
        
    public static void main(String[] args) {
       Application.launch(NationalID.class, (java.lang.String[])null);
    }
    
}
