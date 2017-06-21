/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

/**
 *
 * @author Miche
 */
import database.DatabaseConn;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import jxl.Cell;
import jxl.CellView;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ReadData {
    WritableWorkbook writableWorkbook;
    WritableCellFormat cf;
    WritableSheet writableSheet;
    WritableFont wf;
    int row = 0;
    int initialmember = 1;
    int mebers = 0;

    DatabaseConn con = new DatabaseConn("jdbc:mysql://localhost:3306/nationalid", "root", "");
    private String inputFile;

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public void read() throws IOException {
        File inputWorkbook = new File(inputFile);
        Workbook w;
        try {
            w = Workbook.getWorkbook(inputWorkbook);
            // Get the first sheet
            Sheet sheet = w.getSheet(0);
            // Loop over first 10 column and lines
            con.dBConnect();
            for (int j = 1; j < sheet.getRows(); j++) {
                Cell adNo = sheet.getCell(0, j);
                Cell sNo = sheet.getCell(1, j);
                Cell Name = sheet.getCell(2, j);
                Cell Stream = sheet.getCell(3, j);
                String a = adNo.getContents();
                String b = sNo.getContents();
                String c = Name.getContents();
                String d = Stream.getContents();
                con.insert_or_Update("INSERT INTO student VALUES('" + a + "','" + b + "','" + c + "',null,null,null,null,null,null,null);");
                con.insert_or_Update("INSERT INTO s1 VALUES('" + a + "','" + c + "','" + d + "');");
                con.insert_or_Update("INSERT INTO s1_sb01 VALUES('" + a + "','" + c + "','" + d + "',null,null);");
            }
        } catch (IOException | BiffException | IndexOutOfBoundsException e) {
            System.out.println(e);
        }
    }

    public void generateTemp(String column) throws Exception {
        String path = "E:\\printed\\VillageForms";
        String Parish = "PARISH", sheet = "SHEET";
        File f = new File(path);
        f.mkdir();
        File exlFile = new File(path + "\\" + column + ".xls");

        wf = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
        cf = new WritableCellFormat(wf);
        cf.setBorder(Border.ALL, BorderLineStyle.THIN);
        CellView hidden = new CellView();
        hidden.setHidden(true);

        writableWorkbook = Workbook.createWorkbook(exlFile);
        writableSheet = writableWorkbook.createSheet(Parish + " " + column + " " + sheet, 0);
        if (con.dBConnect()) {
            ResultSet t = con.queryDatabase("SELECT DISTINCT village from members where parish='" + column + "';");
            while (t.next()) {
                String village = t.getString("village");
                System.out.println(village);
                village(village);
                row = row + 10;
            }
            
            writableWorkbook.write();
            writableWorkbook.close();
            System.out.println("Write complete");
        }
    }
    

    public void village(String village) throws Exception {
        if (con.dBConnect()) {
            try {

                int column = 0;
                ResultSet t = con.queryDatabase("SELECT * from members where village='" + village + "';");
                ResultSet count = con.queryDatabase("SELECT COUNT(memberID) from members where village='" + village + "';");
                while (count.next()) {
                    mebers = count.getInt(1);
                }
//               for (int i = 0; i < 2; i++) {
                if (t.next()) {
                    int x, y = 1, z = 9;

                    Label dis = new Label(column, row, "DISTRICT :");
                    WritableCellFormat c1 = new WritableCellFormat(wf);
                    c1.setBorder(Border.ALL, BorderLineStyle.THIN);
                    c1.setFont(wf);
                    dis.setCellFormat(c1);
                    writableSheet.addCell(dis);

                    Label ct = new Label(column, row + 1, "COSTITUENCY :");
                    WritableCellFormat c = new WritableCellFormat(wf);
                    c.setBorder(Border.ALL, BorderLineStyle.THIN);
                    c.setFont(wf);
                    ct.setCellFormat(c);
                    writableSheet.addCell(ct);

                    Label cty = new Label(column, row + 3, "COUNTY:");
                    WritableCellFormat coty = new WritableCellFormat(wf);
                    coty.setBorder(Border.ALL, BorderLineStyle.THIN);
                    coty.setFont(wf);
                    cty.setCellFormat(coty);
                    writableSheet.addCell(cty);

                    Label SUBC = new Label(column + 3, row, "SUBCOUNTYNAME :");
                    WritableCellFormat cS = new WritableCellFormat(wf);
                    cS.setBorder(Border.ALL, BorderLineStyle.THIN);
                    cS.setFont(wf);
                    SUBC.setCellFormat(cS);
                    writableSheet.addCell(SUBC);

                    Label pur = new Label(column + 3, row + 2, "PARISH :");
                    WritableCellFormat cP = new WritableCellFormat(wf);
                    cP.setBorder(Border.ALL, BorderLineStyle.THIN);
                    cP.setFont(wf);
                    pur.setCellFormat(cP);
                    writableSheet.addCell(pur);

                    Label VILL = new Label(column + 6, row, "VILLAGE :");
                    WritableCellFormat cV = new WritableCellFormat(wf);
                    cV.setBorder(Border.ALL, BorderLineStyle.THIN);
                    cV.setFont(wf);
                    VILL.setCellFormat(cV);
                    writableSheet.addCell(VILL);

                    Label total = new Label(column + 6, row + 3, "TOTAL MEMBERS:");
                    WritableCellFormat TOV = new WritableCellFormat(wf);
                    TOV.setBorder(Border.ALL, BorderLineStyle.THIN);
                    TOV.setFont(wf);
                    total.setCellFormat(TOV);
                    writableSheet.addCell(total);

                    for (int a = 1; a <= z; a++) {
                        Label titleZ = new Label(a - 1, initialmember + 4, "");
                        WritableCellFormat BLACK1 = new WritableCellFormat(wf);
                        BLACK1.setBorder(Border.ALL, BorderLineStyle.THIN);
                        BLACK1.setBackground(Colour.YELLOW2);
                        BLACK1.setFont(wf);
                        titleZ.setCellFormat(BLACK1);
                        writableSheet.addCell(titleZ);
                    }

                    for (int a = 1; a <= z; a++) {
                        Label title = new Label(a - 1, initialmember + 5, t.getMetaData().getColumnName(a));
                        WritableCellFormat headers = new WritableCellFormat(wf);
                        headers.setBorder(Border.ALL, BorderLineStyle.THIN);
                        headers.setBackground(Colour.YELLOW);
                        headers.setFont(wf);
                        title.setCellFormat(headers);
                        writableSheet.addCell(title);
                    }
                    while (t.next()) {
                        Label label1 = new Label(column + 1, row, t.getString("district"));
                        label1.setCellFormat(cf);
                        writableSheet.addCell(label1);
                        Label label2 = new Label(column, row + 2, t.getString("constituency"));
                        label1.setCellFormat(cf);
                        writableSheet.addCell(label2);

                        Label label3 = new Label(column + 3, row + 1, t.getString("subcounty"));
                        label1.setCellFormat(cf);
                        writableSheet.addCell(label3);

                        Label label4 = new Label(column + 6, row + 1, t.getString("village"));
                        label1.setCellFormat(cf);
                        writableSheet.addCell(label4);

                        Label label5 = new Label(column + 3, row + 3, t.getString("parish"));
                        label1.setCellFormat(cf);
                        writableSheet.addCell(label5);

                        Label label6 = new Label(column + 1, row + 3, t.getString("county"));
                        label1.setCellFormat(cf);
                        writableSheet.addCell(label6);

                        for (x = 0; x < z; x++) {
                            Label label = new Label(x, initialmember + 6, t.getString(x + 1));
                            label.setCellFormat(cf);
                            writableSheet.addCell(label);
                        }
                        row = row + 1;
                        initialmember = initialmember + 1;

                    }

//                while (count.next()) {
//                    Label label7 = new Label(7, 3, count.getString(1));
//                    label7.setCellFormat(cf);
//                    writableSheet.addCell(label7);
//                    
//                }
                    row = row + mebers;
                    initialmember = initialmember + mebers;
                }
            } catch (Exception e) {
            }
        }

    }

    public void toBotOlevel(String subject, String sClass) throws SQLException {
        File inputWorkbook = new File(inputFile);
        if (con.dBConnect()) {
            try {
                ResultSet t = con.queryDatabase("SELECT subjectCode from subject where subjectName='" + subject + "';");
                String sub = null;
                if (t.next()) {
                    sub = t.getString("subjectCode");
                }
                Workbook w = Workbook.getWorkbook(inputWorkbook);
                Sheet sheet = w.getSheet(0);
                if (con.dBConnect()) {
                    for (int j = 1; j < sheet.getRows(); j++) {
                        String aNo = sheet.getCell(0, j).getContents();
                        String bot = sheet.getCell(3, j).getContents();
                        con.insert_or_Update("UPDATE " + sClass.replace(".", "") + "_" + sub + " SET BOT=" + bot + " WHERE adNo='" + aNo + "';");
                    }
                }
                System.out.println("Read Complete");
            } catch (IOException | BiffException | IndexOutOfBoundsException ex) {
                System.out.println(ex);
            }
        }
    }

    public void toimportNew() throws SQLException {
        File inputWorkbook = new File(inputFile);
        if (con.dBConnect()) {
            try {
                Workbook w = Workbook.getWorkbook(inputWorkbook);
                Sheet sheet = w.getSheet(0);
                if (con.dBConnect()) {
                    for (int j = 1; j < sheet.getRows(); j++) {
                        String memberid = sheet.getCell(0, j).getContents();
                        String nrmno = sheet.getCell(1, j).getContents();
                        String surname = sheet.getCell(2, j).getContents();
                        String firstname = sheet.getCell(3, j).getContents();
                        String othername = sheet.getCell(4, j).getContents();
                        String sex = sheet.getCell(5, j).getContents();
                        String dob = sheet.getCell(6, j).getContents();
                        String nationalid = sheet.getCell(7, j).getContents();
                        String voted = sheet.getCell(8, j).getContents();
                        String district = sheet.getCell(9, j).getContents();
                        String constituency = sheet.getCell(10, j).getContents();
                        String county = sheet.getCell(11, j).getContents();
                        String subcounty = sheet.getCell(12, j).getContents();
                        String parish = sheet.getCell(13, j).getContents();
                        String village = sheet.getCell(14, j).getContents();

                        con.insert_or_Update("INSERT INTO members VALUES('" + memberid + "','" + nrmno + "','" + surname + "','" + firstname + "','" + othername + "','" + sex + "','" + dob + "','" + nationalid + "','" + voted + "','" + district + "','" + constituency + "','" + county + "','" + subcounty + "','" + parish + "','" + village + "');");
                    }
                }
                System.out.println("Read Complete");
            } catch (IOException | BiffException | IndexOutOfBoundsException ex) {
                System.out.println("Error is :" + ex);
            }
        }
    }
}
