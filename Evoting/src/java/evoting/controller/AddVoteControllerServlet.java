/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.controller;

import evoting.dao.VoteDAO;
import evoting.dto.CandidateInfo;
import evoting.dto.VoteDTO;
import java.io.IOException;
import java.io.PrintWriter;
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
public class AddVoteControllerServlet extends HttpServlet {

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
        HttpSession session=request.getSession();
        String userid = (String)session.getAttribute("userid");
        if(userid==null){
            session.invalidate();
            response.sendRedirect("accessdenied.html");
            return;
        }
        try
        {
            String  candidateId = request.getParameter("candidate");
            System.out.println("candidateId:- "+candidateId);
            System.out.println("1");
            VoteDTO vote = new VoteDTO(candidateId,userid);
            System.out.println("2");
            boolean result = VoteDAO.addVote(vote);
            System.out.println("3");
            CandidateInfo candidate = VoteDAO.getVote(candidateId);
            System.out.println("4");
            if(result==true){
                System.out.println("5");
                    session.setAttribute("candidate",candidate);
            }
           // session.setAttribute("result",result);
            request.setAttribute("result",result);
            System.out.println("6");
            rd=request.getRequestDispatcher("verifyvote.jsp");
            
        }
        catch(Exception ex){
            ex.printStackTrace();
            System.out.println("Exception in AddVoteCandidateControllerServlet");
            request.setAttribute("Exception",ex);
            rd=request.getRequestDispatcher("showexception.jsp");
        }
        finally{
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
