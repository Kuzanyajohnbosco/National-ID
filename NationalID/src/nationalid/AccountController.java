/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nationalid;

import database.DatabaseConn;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import sun.security.util.Password;

/**
 * FXML Controller class
 *
 * @author basasagerald
 */
public class AccountController implements Initializable {
 private NationalID application;
 DatabaseConn con = new DatabaseConn("jdbc:mysql://localhost:3306/nationalid", "root", "");
   @FXML TextField username;
   @FXML PasswordField pass;
   @FXML PasswordField confirm;
   @FXML Button btn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void setApp(NationalID application){
        this.application = application;
    }
    public void Register(){
    String username1=username.getText().toString();
    String psw=pass.getText().toString();
    String conf=confirm.getText().toString();
    
        try {
            if(con.dBConnect()){
            ResultSet results = con.queryDatabase("SELECT username FROM users");
            while(results.next()){
                String v = results.getString("userName");
                if((v.equalsIgnoreCase(username1))){
                    JOptionPane.showMessageDialog(null, "THIS USERNAME IS ALREDAY REGISTERED !!!");
                    break;
                }
                else if (!psw.equals(conf)){
                    JOptionPane.showMessageDialog(null, "PASSWORD DOESNOT MATCH !!!");
                    break;
                }
            }
            con.insert_or_Update("INSERT INTO users VALUES('"+username1+"','"+psw+"');");
            JOptionPane.showMessageDialog(null, "ACCOUNT CREATED SUCCESSFULLY!!!");
        }
        } catch (Exception e) {
        }
    }
}
