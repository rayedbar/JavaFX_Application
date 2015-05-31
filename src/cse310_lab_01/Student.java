/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse310_lab_01;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Rayed
 */
public class Student {
    
    private SimpleStringProperty name;
    private SimpleIntegerProperty sid; 
    private SimpleStringProperty email;
   
    public void setName(String name){
        this.name = new SimpleStringProperty(name);
    }
    
    public String getName(){
        return name.get();
    }
    
    public void setSid(int sid){
        this.sid = new SimpleIntegerProperty(sid);
    }
    
    public int getSid (){
        return sid.get();
    }
    
    public void setEmail(String email){
        this.email = new SimpleStringProperty(email);
    }
    
    public String getEmail(){
        return email.get();
    }
}
