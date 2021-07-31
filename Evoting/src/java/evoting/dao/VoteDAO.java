/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.dao;

import static evoting.dao.CandidateDAO.getUserNameById;
import evoting.dbutil.DBConnection;
import evoting.dto.CandidateDetails;
import evoting.dto.CandidateInfo;
import evoting.dto.VoteDTO;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author hp
 */
public class VoteDAO {
    
    private static PreparedStatement ps1,ps2,ps3,ps4,ps5,ps6,ps7,ps8;
    private static Statement st,st2;
    static{
        try {
   
            ps1 = DBConnection.getConnection().prepareStatement("select candidate_id from Voting where voter_id=?");
            ps2 = DBConnection.getConnection().prepareStatement("select candidate_id,username,party,symbol from candidate,user_details where candidate.user_id=user_details.adhar_no and candidate.candidate_id=?");
            ps3=  DBConnection.getConnection().prepareStatement("Insert into voting values(?,?)");
            ps4=  DBConnection.getConnection().prepareStatement("select candidate_id,count(*) as votes_obt from voting group by candidate_id order by votes_obt desc");
            ps6=  DBConnection.getConnection().prepareStatement("select party,count(voting.candidate_id) as votes_obt from candidate,voting where candidate.candidate_id=voting.candidate_id group by party order by votes_obt desc");
            ps5=  DBConnection.getConnection().prepareStatement("select candidate_id from voting where voter_id=?");
            ps7=  DBConnection.getConnection().prepareStatement("select symbol from candidate where party=?");
            ps8=  DBConnection.getConnection().prepareStatement("select party from candidate,voting where candidate.candidate_id=voting.candidate_id and party=? group by party order by count(voting.candidate_id) desc");
            st=   DBConnection.getConnection().createStatement();
            st2=   DBConnection.getConnection().createStatement();
        } catch (SQLException ex) {

            ex.printStackTrace();

        }

    }
    
    public static String getCandidateId(String userid)throws Exception
    {
        ps1.setString(1, userid);
        ResultSet rs = ps1.executeQuery();
        if(rs.next())
        {
            return rs.getString(1);
        }
        return null;
    }
    
    public static CandidateInfo getVote(String candidateid) throws Exception
    {
        ps2.setString(1, candidateid);
        ResultSet rs = ps2.executeQuery();
        CandidateInfo cd = new CandidateInfo();
        Blob blob;
        InputStream inputStream;
        byte[] buffer;
        byte[] imageBytes;
        int bytesRead;
        String base64Image;
        ByteArrayOutputStream outputStream;

        if (rs.next()) {
            blob = rs.getBlob(4);
            inputStream = blob.getBinaryStream();
            outputStream =new ByteArrayOutputStream();
            buffer=new byte[4096];
            bytesRead=-1;
            while((bytesRead=inputStream.read(buffer))!=-1)
           {
               outputStream.write(buffer,0,bytesRead); 
            imageBytes=outputStream.toByteArray();
            Base64.Encoder en=Base64.getEncoder();
            base64Image=en.encodeToString(imageBytes);
            cd.setSymbol(base64Image);
            cd.setCandidateId(candidateid);
            cd.setCandidateName(rs.getString(2));
            cd.setParty(rs.getString(3));
            
          }
        return cd;
    }
        return null;
    }
   
    
    public static boolean addVote(VoteDTO obj)throws SQLException
    {  
        System.out.println("ps3.setString(1,obj.getCandidateId())"+obj.getCandidateId());
        System.out.println("ps3.setString(1,obj.getCandidateId())"+obj.getVoterId());
        ps3.setString(1,obj.getCandidateId());
        ps3.setString(2,obj.getVoterId());
        
        return (ps3.executeUpdate()!=0);
    }
    
    public static Map<String,Integer> getResult() throws SQLException
    {
        Map<String,Integer> result = new LinkedHashMap<>();
        ResultSet rs = ps4.executeQuery();
        while(rs.next()){
            result.put(rs.getString(1),rs.getInt(2));
           //getString(1)=> candidate_id , getInt(2)=> votes_obtained          
        }
        return result;
    }
    
    public static Map<String,Integer> getResultPartyWise() throws SQLException
    {
        //select party,count(voting.candidate_id) as votes_obt from candidate,voting where candidate.candidate_id=voting.candidate_id group by party order by votes_obt desc;
        Map<String,Integer> result = new LinkedHashMap<>();
        ResultSet rs = ps6.executeQuery();
        while(rs.next()){
            result.put(rs.getString(1),rs.getInt(2));
           //getString(1)=> candidate_id , getInt(2)=> votes_obtained          
        }
        //System.out.println("result:- "+result);
        return result;
    }
    
    public static int getVoteCount() throws SQLException
    {
        ResultSet rs=st.executeQuery("select count(*) from voting");
        if(rs.next())
            return rs.getInt(1);
        return 0;
            
    }
    
    public static String getSymbolByParty(String party) throws Exception
    {
        ps8.setString(1,party);
        ResultSet rs=ps8.executeQuery();
        if(rs.next())
        {
            ps7.setString(1,rs.getString(1));
            ResultSet rs2=ps7.executeQuery();
            //System.out.println("party:- "+party);
        Blob blob;
        InputStream inputStream;
        byte[] buffer;
        byte[] imageBytes;
        int bytesRead;
        String base64Image=null;
        ByteArrayOutputStream outputStream;
        
        if (rs2.next()) {
            System.out.println("here");
            blob = rs2.getBlob(1);
            inputStream = blob.getBinaryStream();
            outputStream =new ByteArrayOutputStream();
            buffer=new byte[4096];
            bytesRead=-1;
           while((bytesRead=inputStream.read(buffer))!=-1)
           {
            outputStream.write(buffer,0,bytesRead); 
            imageBytes=outputStream.toByteArray();
            Base64.Encoder en=Base64.getEncoder();
            base64Image=en.encodeToString(imageBytes);
           }
           return  base64Image;
        }
        return base64Image;
            
        }
        return null;
            
    }
    
    public static String verifyVote(String userid)throws SQLException
    {
        ps5.setString(1, userid);
        ResultSet rs=ps5.executeQuery();
        if(rs.next())
            return rs.getString(1);
        return null;
    }   
    public static Map<String,Integer> genderwiseVote() throws SQLException
    {   
        ResultSet rs=st2.executeQuery("select gender,count(gender) from user_details,voting where user_details.adhar_no=voting.voter_id group by gender");
        HashMap<String,Integer> result=new HashMap();
        HashMap<String,Double> genderwise=new HashMap();
        int total=0;
        while(rs.next()){
               result.put(rs.getString(1),rs.getInt(2));
        }
        return result;
    }    
    

    public static void main(String[] args) {
        try{
            Map<String,Integer> m=VoteDAO.genderwiseVote();
            System.out.println(m);
        }
        catch(Exception ex){
            ex.printStackTrace();
            System.out.println("Exception is:- "+ex);
        }
    }
}

