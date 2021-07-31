<%-- 
    Document   : admindeleteuser
    Created on : 27 Jun, 2021, 6:48:51 PM
    Author     : hp
--%>

<%@page import="evoting.dto.UserDetails"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
    if(result.equalsIgnoreCase("deleted")){
        out.println("success");
    }
    else if(result.equalsIgnoreCase("Notdeleted")){
        out.println("failed");
    }
    StringBuffer displayBlock = new StringBuffer();
    if (result != null && result.equalsIgnoreCase("usersIdList")) {
        ArrayList<String> userId = (ArrayList<String>) request.getAttribute("usersid");
        displayBlock.append("<option value='Choose Id' >Choose Id</option>");
        for (String c : userId) {
            displayBlock.append("<option value='" + c + "'>" + c + "</option>");
        }
        JSONObject json = new JSONObject();
        json.put("uids", displayBlock.toString());
        out.println(json);
        System.out.println("In adminshowuserid");
        System.out.println(displayBlock);

    } else if (result != null && result.equals("details")) {
        UserDetails user = (UserDetails) request.getAttribute("user");
        displayBlock.append("<table>");
        displayBlock.append("<tr><th>Username: </th><td>"+user.getUsername()+"</td></tr>");
        displayBlock.append("<tr><th>Email: </th><td>"+user.getEmail()+"</td></tr>");
        displayBlock.append("<tr><th>Mobile No: </th><td>"+user.getMobile()+"</td></tr>");
        displayBlock.append("<tr><th>Address: </th><td>"+user.getAddress()+"</td></tr>");
        displayBlock.append("<tr><th>City:</th><td>"+user.getCity()+"</td></tr>");
        displayBlock.append("<tr><th>confirm:- </th><td><input type='button' value='Delete User' onclick='deluser()' id='deluser'></td></tr>");
        displayBlock.append("</table>");
        //displayBlock.append("<tr><th><input type='button' value='Add Candidate' onclick='addcandidate()' id='addcnd'></th>\n");
       // displayBlock.append("<th><input type='reset' value='Clear' onclick='clearText()'></th></tr></table></form>\n");
        JSONObject json = new JSONObject();
        json.put("subdetails", displayBlock.toString());
        out.println(json);
        System.out.println("In admindeleteuser");
        System.out.println(displayBlock);
    }
    
%>

