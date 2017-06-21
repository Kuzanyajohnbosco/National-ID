/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nationalid;

import database.DatabaseConn;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 *
 * @author basasagerald
 */
public class FXMLDocumentController implements Initializable {
     private NationalID application;
    @FXML
    private Label label;
    @FXML Text warningAtLogin;
    @FXML
    Button loginBtn;
    @FXML TextField userName;
    @FXML PasswordField password;
    
    @FXML
    private void handleLoginButtonAction(ActionEvent event) throws SQLException {
        Boolean logged = false;
        DatabaseConn data = new DatabaseConn("jdbc:mysql://localhost:3306/nationalid", "root", "");
        if(data.dBConnect()){
            ResultSet results = data.queryDatabase("SELECT * FROM users where username = '"+userName.getText()+"'");
            while(results.next()){
                String v = results.getString("userName");
                String u = results.getString("password");
                if((v.equalsIgnoreCase(userName.getText()) && (u.equals(password.getText())))){
                    application.gotoWelcomeScreen();
                    logged =true;
                    break;
                }
            }if(logged == false){
                warningAtLogin.setText("Invalid Login. Check Username or Password");
            }  
        }else{
            warningAtLogin.setText("Server Currently OFF. Contact System Administrator");
        }
         
    }
    public void setApp(NationalID application){
        this.application = application;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        warningAtLogin.setText("");
    }    
    
}
