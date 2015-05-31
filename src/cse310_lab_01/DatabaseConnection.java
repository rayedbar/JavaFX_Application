/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cse310_lab_01;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Rayed
 */
public class DatabaseConnection {

    public static int user_id;
    public static int slot_id;
    
    public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost/demo";
            String user = "root";
            String pass = "";
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("Connection Successful");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    } 
    
    public static ResultSet getSlotSeats(int slotID) throws SQLException{
        slot_id = slotID;
        Connection connection = getConnection();
        ResultSet result = null;
        try {
            String insert = "SELECT seats FROM slots where slots.id = ?";
            
            PreparedStatement ps = connection.prepareStatement(insert);
            ps.setInt(1, slot_id);
            
            result = ps.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public static ResultSet getUserCredentials() {
        Connection connection = getConnection();
        ResultSet result = null;
        try {
            Statement query = connection.createStatement();
            String select = "SELECT id, email, password FROM users";
            result = query.executeQuery(select);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static boolean insertData(Users user) throws SQLException {
        String name = user.getName();
        int sid = user.getSid();
        String email = user.getEmail();
        String password = user.getPassword();
        
        Connection con = getConnection();
        try {
            //Statement query = con.createStatement();
            String insert = "INSERT INTO users (name, sid, email, password) " +
                    "VALUES (?, ?, ?, ?)";
            
            PreparedStatement ps = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setInt(2, sid);
            ps.setString(3, email);
            ps.setString(4, password);
            
            int result = ps.executeUpdate();
            if (result == 1){
                System.out.println("New User Created Successfully");
                ResultSet generatedKey = ps.getGeneratedKeys();
                if (generatedKey.next()){
                    user_id = generatedKey.getInt(1);
                    System.out.println(user_id);
                }
                ps.close();
                return true;
            } else {
                System.err.println("Failed to Create New User");
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            con.close();
        }
    }
    
    public static boolean registerSlot() throws SQLException{
        try (Connection con = getConnection()) {
            //Statement query = con.createStatement();
            String insert = "UPDATE users SET slot_id = ? WHERE id = ?"; 
            
            PreparedStatement ps = con.prepareStatement(insert);
            ps.setInt(1, slot_id);
            ps.setInt(2, user_id);
            
            int result = ps.executeUpdate();
            if (result == 1){
                System.out.println("New User Successfully Registered");
                updateSeats();
                ps.close();
                return true;
            } else {
                System.err.println("Failed to Register New User");
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private static void updateSeats() throws SQLException {
        Connection con = getConnection();
        Statement stm = con.createStatement();
        String insert = "UPDATE slots SET seats = seats - 1 WHERE id = " + slot_id;
        int r = stm.executeUpdate(insert);
    }

    public static ObservableList<Student> getEnrolledStudents(int sectionNumber) {
        ObservableList<Student> list = FXCollections.observableArrayList(); 
        try {
            Connection con = getConnection();
            Statement stm = con.createStatement();
            String sql = "SELECT name, sid, email FROM users WHERE slot_id = " + sectionNumber + "  ORDER BY sid ASC";
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()){
                Student student = new Student();
                
                student.setName(rs.getString("name"));
                student.setSid(rs.getInt("sid"));
                student.setEmail(rs.getString("email"));
                
                list.add(student);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}