/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author ManTing
 */

//Class for admin
public class Admin {
    private final String adminID;
    private String pw;

    public Admin(String inPW) {
       adminID ="admin";
       pw = inPW;
    }

    public String getPW() {
        return pw;
    }
    public void setPW(String pw) {
        this.pw = pw;
    }
    
}
