/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nationalid;

import database.DatabaseConn;
import db.ReadData;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javax.swing.JOptionPane;
/**
 * FXML Controller class
 *
 * @author basasagerald
 */
public class NextsceneFXMLController implements Initializable {
    private NationalID application;
     DatabaseConn con = new DatabaseConn("jdbc:mysql://localhost:3306/nationalid", "root", "");
    @FXML TableView   enterMembersTable;
     @FXML Button browseBtn1;
    @FXML Button imprt;
    @FXML TableColumn MEMBERIDColumn;
    @FXML TableColumn NRMNOColumn;
    @FXML TableColumn SURNAMEColumn;
    @FXML TableColumn FIRSTNAMEColumn;
    
    @FXML TableColumn OTHERNAMEColumn;
//    @FXML TableColumn P1Grd;
    
    @FXML TableColumn SEXColumn;
    @FXML TableColumn DOBColumn;
    
    @FXML TableColumn NATIONALIDColumn;
    @FXML TableColumn VOTEDColumn;
    
    @FXML TextField importfield;
    String printdata;
     @FXML TextField fieldsearch,fieldsearch1;
     @FXML ListView display,display1;
    ResultSet myresult;
    int t=0;
    Stage mem;
    private ObservableList<EnterMemberModel> data= FXCollections.observableArrayList();
    private ObservableList<String> fieltereddata=FXCollections.observableArrayList();;
    private ObservableList<String> fielteredparish=FXCollections.observableArrayList();;
 public void setApp(NationalID application){
        this.application = application;
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        myTable(enterMembersTable , "members"); 
       fetchparish();
       fetchNames();
//        if (display1.getSelectionModel().getSelectedItem()==null) {
////            fieldsearch.setVisible(false);
//            
//        }else{
//        fieldsearch.setVisible(true);
        fieldsearch.textProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldVal,
                    Object newVal) {
                search((String) oldVal, (String) newVal);
            }
        });
        
        
        fieldsearch1.textProperty().addListener(new ChangeListener() {
            public void changed(ObservableValue observable, Object oldVal,
                    Object newVal) {
                searchparish((String) oldVal, (String) newVal);
            }
        });
        
        
        display.setOnMouseClicked((MouseEvent event) -> {
            try {
                getselctedItem();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
        display1.setOnMouseClicked((MouseEvent event) -> {
            try {
                getselctedItemparish();
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });
        
    }
    public void getselctedItem() {
        data.clear();
        String k = display.getSelectionModel().getSelectedItem().toString();
        
        try {
            con.dBConnect();
            String SQL = ("SELECT  * from members where village ='"+k+"';" );
            ResultSet rs = con.queryDatabase(SQL);
            while (rs.next()) {
               data.add(new EnterMemberModel(rs.getString("memberID"),rs.getString("nrmNo"),rs.getString("surname"),rs.getString("firstname"),rs.getString("othername"),rs.getString("sex"),rs.getString("dob"),rs.getString("nationalid"),rs.getString("voted")));   
            }
            enterMembersTable.setItems(data);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
     public void getselctedItemparish() {  
         fieltereddata.clear();
        String parish = display1.getSelectionModel().getSelectedItem().toString();
        printdata=parish;
        try {
            con.dBConnect();
            String SQL = ("SELECT DISTINCT village from members where parish ='"+parish+"';" );
            ResultSet rs = con.queryDatabase(SQL);
            while (rs.next()) {
                fieltereddata.add(rs.getString("village"));
              }
            display.setItems(fieltereddata);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    
     public void search(String oldVal, String newVal) {
        if (oldVal != null && (newVal.length() < oldVal.length())) {
            display.setItems(fieltereddata);
        }
        String value = newVal;
        ObservableList<String> subentries = FXCollections.observableArrayList();
        for (Object entry : display.getItems()) {
            String entryText = (String) entry;
            if (entryText.contains(value)) {

                subentries.add(entryText);
            }
        }
        display.setItems(subentries);   
    }
     public void searchparish(String oldVal, String newVal) {
        if (oldVal != null && (newVal.length() < oldVal.length())) {
            display1.setItems(fielteredparish);
        }
        String value = newVal;
        ObservableList<String> subentries1 = FXCollections.observableArrayList();
        for (Object entry : display1.getItems()) {
            String entryText = (String) entry;
            if (entryText.contains(value)) {

                subentries1.add(entryText);
            }
        }
        display1.setItems(subentries1);
    }
    public void myTable(TableView enterMembersTable ,String table){
        try {
            enterMembersTable.setEditable(false);
            enterMembersTable.getSelectionModel (). setCellSelectionEnabled(false);
            enterMembersTable.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
                if (event.getCode() == KeyCode.ENTER) { return;}
                
                if (enterMembersTable.getEditingCell() == null)
                {
                    if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
                        TablePosition focusedCellPosition = enterMembersTable.getFocusModel().getFocusedCell();
                        enterMembersTable.edit(focusedCellPosition.getRow(),focusedCellPosition.getTableColumn());
                        
                    }
                }
            });
            
            
            enterMembersTable.addEventFilter(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
                if (event.getCode() == KeyCode.ENTER) {
                    enterMembersTable.getSelectionModel().selectBelowCell();
                }
            });
             
            buildTable(table);
        } catch (SQLException ex) {
            Logger.getLogger(NextsceneFXMLController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ENTER MEMBERS TABLE ERROR");
        }
    }


        public void buildTable(String table) throws SQLException{

        Callback<TableColumn, TableCell> cellFactory
                = new Callback<TableColumn, TableCell>() {
            public TableCell call(TableColumn p) {
                return new EditingCell();
            }
        };

        MEMBERIDColumn.setCellValueFactory(
                new PropertyValueFactory<EnterMemberModel, String>("memberid"));
        MEMBERIDColumn.setCellFactory(cellFactory);
        MEMBERIDColumn.setOnEditCommit(
                    new EventHandler<CellEditEvent<EnterMemberModel, String>>() {
                        @Override
                        public void handle(CellEditEvent<EnterMemberModel, String> t) {
                            EnterMemberModel stn = (EnterMemberModel) enterMembersTable.getSelectionModel().getSelectedItem();
                            if(con.dBConnect()){
                                con.insert_or_Update("UPDATE members set memberID='"+t.getNewValue()+"' WHERE memberID='"+stn.getMemberid()+"';");
                            }
                        }
                    }
            );

        NRMNOColumn.setCellValueFactory(
                new PropertyValueFactory<EnterMemberModel, String>("nrmno"));
        NRMNOColumn.setCellFactory(cellFactory); 
         NRMNOColumn.setOnEditCommit(
                    new EventHandler<CellEditEvent<EnterMemberModel, String>>() {
                        @Override
                        public void handle(CellEditEvent<EnterMemberModel, String> t) {
                            EnterMemberModel stn = (EnterMemberModel) enterMembersTable.getSelectionModel().getSelectedItem();
                            if(con.dBConnect()){
                                con.insert_or_Update("UPDATE members set nrmNo='"+t.getNewValue()+"' WHERE memberID='"+stn.getMemberid()+"';");
                            }
                        }
                    }
            );

        SURNAMEColumn.setCellValueFactory(
                new PropertyValueFactory<EnterMemberModel, String>("surname"));
        SURNAMEColumn.setCellFactory(cellFactory);
         SURNAMEColumn.setOnEditCommit(
                    new EventHandler<CellEditEvent<EnterMemberModel, String>>() {
                        @Override
                        public void handle(CellEditEvent<EnterMemberModel, String> t) {
                            EnterMemberModel stn = (EnterMemberModel) enterMembersTable.getSelectionModel().getSelectedItem();
                            if(con.dBConnect()){
                                con.insert_or_Update("UPDATE members set surname='"+t.getNewValue()+"' WHERE memberID='"+stn.getMemberid()+"';");
                            }
                        }
                    }
            );

        FIRSTNAMEColumn.setCellValueFactory(
                new PropertyValueFactory<EnterMemberModel, String>("firstname"));
         FIRSTNAMEColumn.setCellFactory(cellFactory);
         FIRSTNAMEColumn.setOnEditCommit(
                    new EventHandler<CellEditEvent<EnterMemberModel, String>>() {
                        @Override
                        public void handle(CellEditEvent<EnterMemberModel, String> t) {
                            EnterMemberModel stn = (EnterMemberModel) enterMembersTable.getSelectionModel().getSelectedItem();
                            if(con.dBConnect()){
                                con.insert_or_Update("UPDATE members set firstname='"+t.getNewValue()+"' WHERE memberID='"+stn.getMemberid()+"';");
                            }
                        }
                    }
            );
         
        OTHERNAMEColumn.setCellValueFactory(
                new PropertyValueFactory<EnterMemberModel, String>("othername"));
        OTHERNAMEColumn.setCellFactory(cellFactory);
        OTHERNAMEColumn.setOnEditCommit(
                    new EventHandler<CellEditEvent<EnterMemberModel, String>>() {
                        @Override
                        public void handle(CellEditEvent<EnterMemberModel, String> t) {
                            EnterMemberModel stn = (EnterMemberModel) enterMembersTable.getSelectionModel().getSelectedItem();
                            if(con.dBConnect()){
                                con.insert_or_Update("UPDATE members set othername='"+t.getNewValue()+"' WHERE memberID='"+stn.getMemberid()+"';");
                            }
                        }
                    }
            );
        SEXColumn.setCellValueFactory(
                new PropertyValueFactory<EnterMemberModel, String>("sex"));
        SEXColumn.setCellFactory(cellFactory);
         SEXColumn.setOnEditCommit(
                    new EventHandler<CellEditEvent<EnterMemberModel, String>>() {
                        @Override
                        public void handle(CellEditEvent<EnterMemberModel, String> t) {
                            EnterMemberModel stn = (EnterMemberModel) enterMembersTable.getSelectionModel().getSelectedItem();
                            if(con.dBConnect()){
                                con.insert_or_Update("UPDATE members set sex='"+t.getNewValue()+"' WHERE memberID='"+stn.getMemberid()+"';");
                            }
                        }
                    }
            );
        DOBColumn.setCellValueFactory(
                new PropertyValueFactory<EnterMemberModel, String>("dob"));
        DOBColumn.setCellFactory(cellFactory);
        DOBColumn.setOnEditCommit(
                    new EventHandler<CellEditEvent<EnterMemberModel, String>>() {
                        @Override
                        public void handle(CellEditEvent<EnterMemberModel, String> t) {
                            EnterMemberModel stn = (EnterMemberModel) enterMembersTable.getSelectionModel().getSelectedItem();
                            if(con.dBConnect()){
                                con.insert_or_Update("UPDATE members set dob='"+t.getNewValue()+"' WHERE memberID='"+stn.getMemberid()+"';");
                            }
                        }
                    }
            );
        NATIONALIDColumn.setCellValueFactory(
                new PropertyValueFactory<EnterMemberModel, String>("nationalid"));
       NATIONALIDColumn.setCellFactory(cellFactory);
       NATIONALIDColumn.setOnEditCommit(
                    new EventHandler<CellEditEvent<EnterMemberModel, String>>() {
                        @Override
                        public void handle(CellEditEvent<EnterMemberModel, String> t) {
                            EnterMemberModel stn = (EnterMemberModel) enterMembersTable.getSelectionModel().getSelectedItem();
                            if(con.dBConnect()){
                                con.insert_or_Update("UPDATE members set nationalid='"+t.getNewValue()+"' WHERE memberID='"+stn.getMemberid()+"';");
                            }
                        }
                    }
            );
//        VOTEDColumn.setCellValueFactory(
//                new PropertyValueFactory<EnterMemberModel, String>("voted"));
//           VOTEDColumn.setCellFactory(cellFactory);

        con.dBConnect();
        String SQL = "SELECT * from " + table + ";";
        ResultSet rs = con.queryDatabase(SQL);
        while (rs.next()) {
            //modification needed right from the DB to much this Constructor
            data.add(new EnterMemberModel(rs.getString("memberID"), rs.getString("nrmNo"), rs.getString("surname"), rs.getString("firstname"), rs.getString("othername"), rs.getString("sex"), rs.getString("dob"), rs.getString("nationalid"), rs.getString("voted")));

        }

        enterMembersTable.setItems(data);

    }

    @FXML
    public void handleBrowseBtn1() throws IOException {

        final FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String mypath = file.getCanonicalPath();
//                openFile(file);
            importfield.setText(mypath);
            System.out.println(mypath);
        }

    }

    public void deleteMember() {
        if (enterMembersTable.getSelectionModel().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "SELECT ITEM TO DROP !!!");
            return;
        }
        else{
        
           EnterMemberModel stn = (EnterMemberModel) enterMembersTable.getSelectionModel().getSelectedItem();
                            if(con.dBConnect()){
                                con.insert_or_Update("DELETE from members where memberID='"+stn.getMemberid()+"';");
                                enterMembersTable.getItems().remove(enterMembersTable.getSelectionModel().getSelectedItem());
                                   System.out.println("the selcted is:" + stn.getMemberid());
                            }
        }
        //int id = getselctedItem().getId();
        
        

    }

    public void AddMember() {

    }
    public void fetchparish(){
     try {
            con.dBConnect();
            String SQL = ("SELECT DISTINCT parish FROM members where memberID like '%" + fieldsearch1.getText() + "%' OR village like '%" + fieldsearch1.getText() + "%' order by surname");
            ResultSet rs = con.queryDatabase(SQL);

            while (rs.next()) {
                fielteredparish.add(rs.getString("parish"));
            }
            display1.setItems(fielteredparish);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void fetchNames() {
        try {
            con.dBConnect();
            String SQL = ("SELECT DISTINCT village FROM members where memberID like '%" + fieldsearch.getText() + "%' OR village like '%" + fieldsearch.getText() + "%' order by surname");
            ResultSet rs = con.queryDatabase(SQL);

            while (rs.next()) {
                fieltereddata.add(rs.getString("village"));
            }
            display.setItems(fieltereddata);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

       

    @FXML
    public void handlePrintButton() {
        if (display1.getSelectionModel().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "SELECT THE PARISH TO PRINT !!!");
            return;
        }
        ReadData prt = new ReadData();
        try {
            prt.generateTemp(printdata);
            t = 0;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (t == 1) {
            File file = new File("E:\\prited\\VillageForms\\printfile.xls");
            try {
                //Open the file using Desktop class
                Desktop.getDesktop().open(file);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

    }

    private static void configureFileChooser(FileChooser fileChooser) {

        fileChooser.getExtensionFilters().addAll(
                //new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter(".xls Excel 2003 to 2013", "*.xls*")
        );

    }

    @FXML
    public void onImport() throws SQLException {
        ReadData rd = new ReadData();
        if (importfield.getText().length() != 0) {
            rd.setInputFile(importfield.getText());
            rd.toimportNew();

            JOptionPane.showMessageDialog(null, "Load Complete");
        } else {
            JOptionPane.showMessageDialog(null, "No file Specified");
        }

    }

    @FXML
    public void addmembers() {
        try {
            Stage hlp = new Stage();
            mem = hlp;
            hlp.setTitle("ADD MEMBER");
            hlp.initOwner(application.stage);
            hlp.initModality(Modality.APPLICATION_MODAL);
            Parent root = FXMLLoader.load(getClass().getResource("AddMember.fxml"));
            Scene cene = new Scene(root);
            hlp.setScene(cene);

            hlp.showAndWait();
        } catch (Exception ex) {
            Logger.getLogger(NationalID.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void logout() {
        application.gotoLogin();
    }
 @FXML
    public void update() {
       enterMembersTable.setEditable(true);
       enterMembersTable.getSelectionModel (). setCellSelectionEnabled(true); 
    }

    @FXML
    public void help() {
        try {
            Stage hlp = new Stage();
            mem = hlp;
            hlp.setTitle("HELP");
            hlp.initOwner(application.stage);
            hlp.initModality(Modality.APPLICATION_MODAL);
            Parent root = FXMLLoader.load(getClass().getResource("help.fxml"));
            Scene cene = new Scene(root);
            hlp.setScene(cene);

            hlp.showAndWait();
        } catch (Exception ex) {
            Logger.getLogger(NationalID.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void accounts() {
        try {
            Stage hlp = new Stage();
            mem = hlp;
            hlp.setTitle("CREATE ACCOUNT");
            hlp.initOwner(application.stage);
            hlp.initModality(Modality.APPLICATION_MODAL);
            Parent root = FXMLLoader.load(getClass().getResource("Account.fxml"));
            Scene cene = new Scene(root);
            hlp.setScene(cene);

            hlp.showAndWait();
        } catch (Exception ex) {
            Logger.getLogger(NationalID.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
