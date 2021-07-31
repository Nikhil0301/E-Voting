/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evoting.controller;

import evoting.dao.CandidateDAO;
import evoting.dao.RegistrationDAO;
import evoting.dto.CandidateDetails;
import evoting.dto.UserDetails;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
public class DeleteUserControllerServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = null;  
        PrintWriter out = response.getWriter();
        HttpSession sess = request.getSession();
        String userid = (String) sess.getAttribute("userid");

        if (userid == null) {
            sess.invalidate();
            response.sendRedirect("accessdenied.html");
            return;
        }
        String data = request.getParameter("data");
        String del = request.getParameter("del");
        System.out.println("data is:- "+data);
        System.out.println("del is:- "+del);
        try {
            if (data != null && data.equalsIgnoreCase("uid")) {

                ArrayList<String> usersIdList = RegistrationDAO.getAllUserIds();
                request.setAttribute("usersid",usersIdList);
                request.setAttribute("result", "usersIdList");
            }
            else if(del!=null && del.equalsIgnoreCase("del"))
            {
                 boolean ans=RegistrationDAO.deleteUser(data);
                 if(ans)
                   request.setAttribute("result", "deleted");
                 else
                   request.setAttribute("result", "Notdeleted");    
            }
            else{
                UserDetails user = RegistrationDAO.getUserDetailsById(data);
                request.setAttribute("user",user);
                request.setAttribute("result","details");
            }
            rd=request.getRequestDispatcher("admindeleteuser.jsp");
        } catch (Exception ex) {
             ex.printStackTrace();
             rd = request.getRequestDispatcher("showexception.jsp");
             request.setAttribute("Exception", ex);     
           
        }
        finally
        {
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
