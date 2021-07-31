<%-- 
    Document   : adminshowusers
    Created on : 26 Jun, 2021, 7:46:55 PM
    Author     : hp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="evoting.dto.UserDetails"%>
<%@page import="org.json.JSONObject" %>
<%@page import="evoting.dto.CandidateDetails" %>
<%@page import="java.util.ArrayList" %>

<%
    String userid = (String) session.getAttribute("userid");
    if (userid == null) {
        // session.invalidate();
        response.sendRedirect("accessdenied.html");
        return;
    }
    String result = (String) request.getAttribute("result");
    StringBuffer displayBlock = new StringBuffer();
    if (result != null && result.equalsIgnoreCase("candidatelist")) {
        ArrayList<UserDetails> candidatelist = (ArrayList<UserDetails>)request.getAttribute("candlist");
        displayBlock.append("<table border='2'>");
        displayBlock.append("<tr><th>User Id</th>");
        displayBlock.append("<th>UserName</th>");
        displayBlock.append("<th>Address</th>");
        displayBlock.append("<th>City</th>");
        displayBlock.append("<th>Email</th>");
        displayBlock.append("<th>Mobile no</th></tr>");
        for (UserDetails user : candidatelist) {
            displayBlock.append("<tr><td>"+user.getUserid()+"</td>");
            displayBlock.append("<td>"+user.getUsername()+"</td>");
            displayBlock.append("<td>"+user.getAddress()+"</td>");
            displayBlock.append("<td>"+user.getCity()+"</td>");
            displayBlock.append("<td>"+user.getEmail()+"</td>");
            displayBlock.append("<td>"+user.getMobile()+"</td></tr>");
        }
        displayBlock.append("</table>");
        JSONObject json = new JSONObject();
        json.put("userdetails", displayBlock.toString());
        out.println(json);
        System.out.println("In adminshowcand");
        System.out.println(displayBlock);

    }
    
%>
