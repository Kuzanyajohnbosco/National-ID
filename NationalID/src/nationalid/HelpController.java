/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nationalid;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 *
 * @author basasagerald
 */
public class HelpController implements Initializable{

    private NationalID application;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        }
     public void setApp(NationalID application){
        this.application = application;
    }
}
