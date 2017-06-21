/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nationalid;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author basasagerald
 */
public class EnterMemberModel {
    private String memberid;
    private String nrmno;
    private String surname; 
    private String firstname;
    private String othername;
    private String sex;
    private String dob;
    private String nationalid;
    private String voted;
    
    public EnterMemberModel() {
        this.memberid = null;
        this.nrmno = null;
        this.surname = null;
        this.firstname = null;
        this.othername  = null;
        this.sex  = null;
        this.dob = null;
        this.nationalid = null;
        this.voted = null;
        
    }

    public EnterMemberModel(String memberid1, String nrmno1 ,String surname1,String firstname1,String othername1 ,String sex1,String dob1,String nationalid1,String voted1)
    {
        this.memberid = memberid1;
        this.nrmno = nrmno1;
        this.surname =surname1;
        this.firstname = firstname1;
        this.othername = othername1;
        this.sex = sex1;
        this.dob = dob1;
        
        this.nationalid =nationalid1;
        this.voted = voted1;           
    }

    /**
     * @return the memberid
     */
    public String getMemberid() {
        return memberid;
    }

    /**
     * @return the nrmno
     */
    public String getNrmno() {
        return nrmno;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @return the othername
     */
    public String getOthername() {
        return othername;
    }

    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @return the dob
     */
    public String getDob() {
        return dob;
    }

    /**
     * @return the nationalid
     */
    public String getNationalid() {
        return nationalid;
    }

    /**
     * @return the voted
     */
    public String getVoted() {
        return voted;
    }

    /**
     * @param memberid the memberid to set
     */
    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    /**
     * @param nrmno the nrmno to set
     */
    public void setNrmno(String nrmno) {
        this.nrmno = nrmno;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @param othername the othername to set
     */
    public void setOthername(String othername) {
        this.othername = othername;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @param dob the dob to set
     */
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     * @param nationalid the nationalid to set
     */
    public void setNationalid(String nationalid) {
        this.nationalid = nationalid;
    }

    /**
     * @param voted the voted to set
     */
    public void setVoted(String voted) {
        this.voted = voted;
    }
}

    