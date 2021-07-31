/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.dao;

import static evoting.dao.CandidateDAO.getUserNameById;
import java.sql.PreparedStatement;
import evoting.dbutil.DBConnection;
import evoting.dto.CandidateDetails;
import evoting.dto.UserDetails;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author hp
 */
public class RegistrationDAO {

    private static PreparedStatement ps, ps1,ps2,ps3,ps4;
    private static Statement st1,st2;

    static {
        try {
            ps = DBConnection.getConnection().prepareStatement("select * from user_details where adhar_no=?");
            ps1 = DBConnection.getConnection().prepareStatement("Insert into user_details values(?,?,?,?,?,?,?,?,?)");
            st1 = DBConnection.getConnection().createStatement();
            st2 = DBConnection.getConnection().createStatement();
            ps3 = DBConnection.getConnection().prepareStatement("delete from user_details where adhar_no=?");
            ps4 = DBConnection.getConnection().prepareStatement("select username,email,mobile_no,address,city from user_details where adhar_no=?");
        } catch (SQLException sqlex) {
            sqlex.printStackTrace();
        }
    }

    public static boolean searchUser(String userid) throws SQLException {

        ps.setString(1, userid);
        
        return ps.executeQuery().next();
    }
    
    public static boolean registerUser(UserDetails user) throws SQLException {

        ps1.setString(1, user.getUserid());
        ps1.setString(2, user.getPassword());
        ps1.setString(3, user.getUsername());
        ps1.setString(4, user.getAddress());
        ps1.setString(5, user.getCity());
        ps1.setString(6, user.getEmail());
        ps1.setString(7, String.valueOf(user.getMobile()));
        ps1.setString(8, "Voter");
        ps1.setString(9, user.getGender());
        System.out.println(" gen:- "+user.getGender());
        return ps1.executeUpdate()==1;
        
    }
    
    public static ArrayList<UserDetails> showUsers() throws SQLException {

        ArrayList<UserDetails> candidateIdlist = new ArrayList();
        
        ResultSet rs = st1.executeQuery("select * from user_details where user_type='Voter'");
        int i=1;
        while (rs.next()){
            UserDetails user=new UserDetails();
            user.setUserid(rs.getString(1));
            user.setUsername(rs.getString(3));
            user.setAddress(rs.getString(4));
            user.setCity(rs.getString(5));
            user.setEmail(rs.getString(6));
            user.setMobile(rs.getLong(7));
            //System.out.println(i+":- "+user);
            candidateIdlist.add(user);
        }
        return candidateIdlist;
    }
    
    public static ArrayList<String> getAllUserIds() throws SQLException {

        ArrayList<String> userIdlist = new ArrayList();
        ResultSet rs = st2.executeQuery("select adhar_no from user_details");
        while (rs.next()) {
            userIdlist.add(rs.getString(1));
        }
        return userIdlist;
    }
    
    public static boolean deleteUser(String adharid) throws SQLException {
        ps3.setString(1,adharid);

        return ps3.executeUpdate() != 0;
    }
    
    public static UserDetails getUserDetailsById(String userid) throws SQLException {
       ps4.setString(1,userid);
       ResultSet rs=ps4.executeQuery();
       UserDetails user=new UserDetails();
       if(rs.next()){
              
              user.setUsername(rs.getString(1));
              user.setEmail(rs.getString(2));
              user.setMobile(rs.getLong(3));
              user.setAddress(rs.getString(4));
              user.setCity(rs.getString(5));
       }
       return user;
    }
    
//    public static void main(String[] args) throws SQLException {
//        ArrayList<UserDetails> candidateIdlist=showUsers();
//        Iterator it=candidateIdlist.iterator();
//        while(it.hasNext()){
//            System.out.println(it.next());
//        }
//    }

    
}
