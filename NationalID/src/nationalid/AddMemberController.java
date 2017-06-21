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
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author basasagerald
 */
public class AddMemberController implements Initializable {
@FXML TextField memberID;
@FXML TextField surname;
@FXML TextField firstname;
@FXML TextField nrmno;
@FXML TextField othername;
@FXML TextField nationalID;
@FXML TextField sex;
@FXML TextField district;
@FXML TextField constituency;
@FXML TextField subcounty;
@FXML TextField county;
@FXML TextField dob;
@FXML TextField parish;
@FXML TextField village;
private NationalID application;
DatabaseConn con = new DatabaseConn("jdbc:mysql://localhost:3306/nationalid", "root", "");
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
    
    public void AddMemberz(){
    String memberid=memberID.getText().toString();
    String nrmNo=nrmno.getText().toString();
    String surName=surname.getText().toString();
    String firstName=firstname.getText().toString();
    String otherName=othername.getText().toString();
    String Sex=sex.getText().toString();
    String Dob=dob.getText().toString();
    String nationalid=nationalID.getText().toString();
    String District=district.getText().toString();
    String Constituency=constituency.getText().toString();
     String County=county.getText().toString();
    String subCounty=subcounty.getText().toString();
    String Parish=parish.getText().toString();
    String Village=village.getText().toString();
    
         try {
            if(con.dBConnect()){
            ResultSet results = con.queryDatabase("SELECT memberID, parish, village FROM members");
            while(results.next()){
                String memberz = results.getString("memberID");
                String parishz=results.getString("parish");
                String villagez=results.getString("village");
                
                if(!parishz.equals(Parish)||!villagez.equals(Village)){
                    JOptionPane.showMessageDialog(null, "ENTERED PARISH OR VILLAGE DOESNOT EXIST !!!");
                    break;
                }
                else if (memberz.equals(memberid)){
                    JOptionPane.showMessageDialog(null, "MEMBER ID ALREADY EXISTS !!!");
                    break;
                }
            }
            con.insert_or_Update("INSERT INTO members VALUES('"+memberid+"','"+nrmNo+"','"+surName+"','"+firstName+"','"+otherName+"','"+Sex+"','"+Dob+"','"+nationalid+"','','"+District+"','"+Constituency+"','"+County+"','"+subCounty+"','"+Parish+"','"+Village+"');");
            JOptionPane.showMessageDialog(null, "MEMEBER ADDED SUCCESSFULLY!!!");
        }
        } catch (Exception e) {
        }
    
    }
    
}
