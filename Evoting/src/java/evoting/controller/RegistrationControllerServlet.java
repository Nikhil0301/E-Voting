/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */  
package evoting.controller;

import evoting.dao.RegistrationDAO;
import evoting.dto.UserDetails;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author hp
 */
public class RegistrationControllerServlet extends HttpServlet {

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
        
        System.out.println("reg 1");
        RequestDispatcher rd = null;
        UserDetails user = new UserDetails();
        
        user.setUserid(request.getParameter("userid"));

        user.setPassword(request.getParameter("password"));
        user.setAddress(request.getParameter("address"));
        user.setCity(request.getParameter("city"));
        user.setEmail(request.getParameter("email"));
        
        user.setMobile(Long.parseLong(request.getParameter("mobile")));
        
        user.setUsername(request.getParameter("username"));
         System.out.println("RegistrationControllerServlet:- "+request.getParameter("gender"));
        user.setGender(request.getParameter("gender"));
        System.out.println("reg 2");
        try{
            
            boolean result=false,userfound=false;
            System.out.println("reg 3");
        if(!RegistrationDAO.searchUser(user.getUserid()))
        {
           
            result = RegistrationDAO.registerUser(user);
        }
        else
            userfound=true;
        request.setAttribute("result",result);
        request.setAttribute("userfound",userfound);
        request.setAttribute("username",user.getUsername());
        rd=request.getRequestDispatcher("registrationresponse.jsp");
        
        } catch (Exception ex) {
           ex.printStackTrace();
           rd=request.getRequestDispatcher("showexception.jsp");
           
        }
        
        finally{
            if(rd!=null)
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
