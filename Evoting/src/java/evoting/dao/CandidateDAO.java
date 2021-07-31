/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.dao;

import evoting.dbutil.DBConnection;
import evoting.dto.CandidateDTO;
import evoting.dto.CandidateDetails;
import evoting.dto.CandidateInfo;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hp
 */
public class CandidateDAO {

    private static PreparedStatement ps, ps1, ps2, ps3, ps4, ps5, ps6, ps7, ps8;
    private static Statement st,st2;

    static {
        try {

            st = DBConnection.getConnection().createStatement();
            ps = DBConnection.getConnection().prepareStatement("select max(candidate_id) from candidate");
            ps1 = DBConnection.getConnection().prepareStatement("select username from user_details where adhar_no=?");
           // ps1 = DBConnection.getConnection().prepareStatement("select username from user_details where adhar_no=? and adhar_no not in(select user_id from candidate)");
            ps2 = DBConnection.getConnection().prepareStatement("select distinct city from user_details");
            ps3 = DBConnection.getConnection().prepareStatement("Insert into candidate values(?,?,?,?,?)");
            //ps8 = DBConnection.getConnection().prepareStatement("Insert into candidate(candidate_id,party,user_id,city) values(?,?,?,?)");
            ps4 = DBConnection.getConnection().prepareStatement("select * from candidate where candidate_id=?");
            ps5 = DBConnection.getConnection().prepareStatement("select candidate_id,username,party,symbol from candidate,user_details where candidate.user_id=user_details.adhar_no and lower(candidate.city)=(select city from user_details where adhar_no=?)");
            ps6 = DBConnection.getConnection().prepareStatement("update candidate set party=?,city=?,symbol=? where user_id=?");
            ps7 = DBConnection.getConnection().prepareStatement("delete from candidate where candidate_id=?");
            st2 = DBConnection.getConnection().createStatement();
        } catch (SQLException ex) {

            ex.printStackTrace();

        }

    }

    public static String getNewId() throws SQLException {

        ResultSet rs = ps.executeQuery();
       // System.out.println(rs.next());//true
         //System.out.println(rs.next());//false
        if(rs.next()) {
            System.out.println("line 56 in rs.next():- ");
            
            String id=rs.getString(1);
            
            System.out.println("id:- "+id.substring(1));
           
            return "C" + (Integer.parseInt(id.substring(1)) + 1);
        } else {
            return "C" + 101;
        }
    }

    public static String getUserNameById(String uid) throws SQLException {

        ps1.setString(1, uid);
        ResultSet rs = ps1.executeQuery();
        if (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }

    public static ArrayList<String> getCity() throws SQLException {

        ArrayList<String> cityList = new ArrayList();

        ResultSet rs = ps2.executeQuery();

        while (rs.next()) {
            cityList.add(rs.getString(1));
        }
        return cityList;
    }
    public static boolean isCandidatePresent(String party,String city) throws SQLException{
        
        ResultSet rs=st2.executeQuery("select party,city from candidate");
        while(rs.next()){
            if(rs.getString(1).equalsIgnoreCase(party) && rs.getString(2).equalsIgnoreCase(city))
                return true;
            
        }
        return false;
    }
    public static boolean addCandidate(CandidateDTO obj) throws SQLException {
        boolean res=isCandidatePresent(obj.getParty(),obj.getCity());
        if(res==true)
            return false;
        ps3.setString(1, obj.getCandidateId());
        ps3.setString(2, obj.getParty());
        ps3.setString(3, obj.getUserid());
        ps3.setBinaryStream(4, obj.getSymbol());
        ps3.setString(5,obj.getCity());

        return ps3.executeUpdate() != 0;

//        ps8.setString(1, obj.getCandidateId());
//        ps8.setString(2, obj.getParty());
//        ps8.setString(3, obj.getUserid());
//        ps8.setString(4, obj.getCity());
//
//        return ps8.executeUpdate() != 0;
    }

    public static ArrayList<String> getAllCandidateIds() throws SQLException {

        ArrayList<String> candidateIdlist = new ArrayList();
        ResultSet rs = st.executeQuery("select candidate_id from candidate");
        while (rs.next()) {
            candidateIdlist.add(rs.getString(1));
        }
        return candidateIdlist;
    }

    public static CandidateDetails getDetailsById(String cid) throws Exception {

        ps4.setString(1, cid);
        ResultSet rs = ps4.executeQuery();
        CandidateDetails cdetails = new CandidateDetails();
        Blob blob;
        InputStream inputStream;
        byte[] buffer;
        byte[] imageBytes;
        int bytesRead;
        String base64Image;
        ByteArrayOutputStream outputStream;

        if (rs.next()) {
            
            //System.out.println("in getDetailsById In rs.next() , cid is:- "+cid);
            blob = rs.getBlob(4);
            inputStream = blob.getBinaryStream();
            outputStream =new ByteArrayOutputStream();
            buffer=new byte[4096];
            bytesRead=-1;
            while((bytesRead=inputStream.read(buffer))!=-1)
           {
               outputStream.write(buffer,0,bytesRead);
           }
            imageBytes=outputStream.toByteArray();
            Base64.Encoder en=Base64.getEncoder();
            base64Image=en.encodeToString(imageBytes);
            cdetails.setSymbol(base64Image);
            cdetails.setCandidateId(cid);
            //System.out.println("rs.getString(3):"+rs.getString(3));
            cdetails.setCandidateName(getUserNameById(rs.getString(3)));
            cdetails.setParty(rs.getString(2));
            cdetails.setCity(rs.getString(5));
            cdetails.setUserId(rs.getString(3));
        }
        return cdetails;
    }
    
    public static ArrayList<CandidateInfo> viewCandidate(String adhar_id) throws Exception{
        
        ArrayList<CandidateInfo> candidatelist= new ArrayList();
        System.out.println("In viewCandidate adhar_id is:- "+adhar_id);
        ps5.setString(1, adhar_id);
        ResultSet rs = ps5.executeQuery();
        Blob blob;
        InputStream inputStream;
        byte[] buffer;
        byte[] imageBytes;
        int bytesRead;
        String base64Image;
        ByteArrayOutputStream outputStream;
        System.out.println("outside rs.next");
        while(rs.next())
        {
            System.out.println("inside rs.next");
            blob = rs.getBlob(4);
            inputStream = blob.getBinaryStream();
            outputStream =new ByteArrayOutputStream();
            buffer=new byte[4096];
            bytesRead=-1;
            while((bytesRead=inputStream.read(buffer))!=-1)
            {
               outputStream.write(buffer,0,bytesRead);
            }
            imageBytes=outputStream.toByteArray();
            Base64.Encoder en=Base64.getEncoder();
            base64Image=en.encodeToString(imageBytes);
            CandidateInfo cd=new CandidateInfo();
            cd.setSymbol(base64Image);
            cd.setCandidateId(rs.getString(1));
            cd.setCandidateName(rs.getString(2));
            cd.setParty(rs.getString(3));
            candidatelist.add(cd);
        }
        return candidatelist;
        
}
    
    public static boolean updateCandidate(CandidateDTO obj) throws SQLException {
        
        ps6.setString(1, obj.getParty());
        ps6.setString(2,obj.getCity());
        ps6.setBinaryStream(3, obj.getSymbol());
        ps6.setString(4, obj.getUserid());

        return ps6.executeUpdate() != 0;
    } 
    
    public static boolean deleteCandidate(String cid) throws SQLException {
        ps7.setString(1, cid);

        return ps7.executeUpdate() != 0;
    }     
    
}
