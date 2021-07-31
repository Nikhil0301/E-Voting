/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.controller;

import evoting.dao.CandidateDAO;
import evoting.dao.VoteDAO;
import evoting.dto.CandidateDetails;
import evoting.dto.PartyDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hp
 */
public class ElectionResultControllerServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
            RequestDispatcher rd=null;
            HttpSession session = request.getSession();
            String userid = (String)session.getAttribute("userid");
            if(userid==null)
            {
               session.invalidate();
               response.sendRedirect("accessdenied.html");
               return;
            }
            
            try
            {
                String data = request.getParameter("data");
               // String data = request.getParameter("candidatewise");
               
              if(data!=null && data.equalsIgnoreCase("candidatewise")){
                   System.out.println("data:--- "+data);
                Map<String,Integer> result=VoteDAO.getResult();
                //System.out.println("result is:- "+result);
                Set s=result.entrySet();
               // System.out.println("set is:- "+s);
                Iterator it=s.iterator();
                LinkedHashMap<CandidateDetails,Integer> resultdetails=new LinkedHashMap<>();
                //System.out.println("outside while");
                while(it.hasNext()){
                   // System.out.println("inside while");
                    Map.Entry<String,Integer> e=(Map.Entry<String,Integer>)it.next();
                    CandidateDetails cd=CandidateDAO.getDetailsById(e.getKey());
                    resultdetails.put(cd,e.getValue());
                }
               // System.out.println("while end");
                request.setAttribute("result",resultdetails);
                //System.out.println("VoteDAO.getVoteCount():- "+VoteDAO.getVoteCount());
                request.setAttribute("votecount",VoteDAO.getVoteCount());
                request.setAttribute("res","candidatewise");
                rd=request.getRequestDispatcher("electionresult.jsp");
                System.out.println("attr:- "+request.getAttribute("res"));
              }
              else if(data!=null && data.equalsIgnoreCase("partywise")){
                System.out.println("data:--- "+data);
                Map<String,Integer> result=VoteDAO.getResultPartyWise();
                System.out.println("result is:- "+result);
                Set s=result.entrySet();
                System.out.println("set is:- "+s);
                Iterator it=s.iterator();
                LinkedHashMap<PartyDTO,Integer> resultdetails=new LinkedHashMap<>();
         
                while(it.hasNext()){
                    System.out.println("inside while");
                    Map.Entry<String,Integer> e=(Map.Entry<String,Integer>)it.next();
                    String symbol=VoteDAO.getSymbolByParty(e.getKey());
                    PartyDTO p=new PartyDTO(e.getKey(),symbol);
                    resultdetails.put(p,e.getValue());
                }
                System.out.println("while end");
                request.setAttribute("result",resultdetails);
                System.out.println("VoteDAO.getVoteCount():- "+VoteDAO.getVoteCount());
                request.setAttribute("res","partywise");
                request.setAttribute("votecount",VoteDAO.getVoteCount());
                System.out.println("attr:- "+request.getAttribute("res"));
                rd=request.getRequestDispatcher("electionresult.jsp");
              }
              else{
                  System.out.println("data:--- "+data);
                  Map<String,Integer> result=VoteDAO.genderwiseVote();
                  request.setAttribute("result",result);
                  request.setAttribute("res","genderwise");
                  
                  rd=request.getRequestDispatcher("electionresult.jsp");
                  
              }
            } 
            catch(Exception ex)
            {
                ex.printStackTrace();
                request.setAttribute("Exception",ex);
                rd=request.getRequestDispatcher("showexception.jsp");
            }
            finally
            {
                System.out.println("rd:- "+rd);
                rd.forward(request, response);
            }
            
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
