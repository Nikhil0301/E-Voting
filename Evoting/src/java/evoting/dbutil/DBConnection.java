/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.dbutil;


import evoting.dao.RegistrationDAO;
import evoting.dao.UserDAO;
import evoting.dto.UserDTO;
import evoting.dto.UserDetails;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hp
 */
public class DBConnection {
    
    private static Connection conn=null;
    
    static{
        try {
             Class.forName("oracle.jdbc.OracleDriver");
             conn = DriverManager.getConnection("jdbc:oracle:thin:@//Nikhil-PC:1521/xe","evoting","evoting");
            // System.out.println("Driver loaded and connection opened");
            
        } catch (ClassNotFoundException cnf) {
            
            System.out.println("In ClassNotFoundException");
            cnf.printStackTrace();
            
        }
        catch(SQLException sqlex){
            System.out.println("In SQLException");
            sqlex.printStackTrace();
            
        }
        
    }
    
    public static Connection getConnection(){
        return conn;
    }
    
    public static void closeConnection(){
        try{
            if(conn!=null)
                conn.close();
        } 
        catch(SQLException sqlex){
            
            sqlex.printStackTrace();
            
        }
    }
    public static void main(String[] args) throws SQLException {
        
         UserDetails user = new UserDetails();
        
         user.setUserid("RM-123");

        user.setPassword("raman");
        user.setAddress("patel nagar");
        user.setCity("Bhopal");
        user.setEmail("ramansahu0301@gmail.com");
        
        user.setMobile(Long.parseLong("9876787654"));
        
        user.setUsername("ramanK");
         
         System.out.println(UserDAO.validateUser(new UserDTO("SA-123","mentor")));
         //System.out.println(RegistrationDAO.searchUser("RN-123"));
         //System.out.println(RegistrationDAO.registerUser(user));
    }
}
