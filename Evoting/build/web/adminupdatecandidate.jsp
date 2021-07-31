<%-- 
    Document   : adminupdatecandidate
    Created on : 25 Jun, 2021, 6:54:49 PM
    Author     : hp
--%>

<%@page import="evoting.dao.CandidateDAO"%>
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
    StringBuffer displayBlock = new StringBuffer();
    if (result != null && result.equalsIgnoreCase("candidatelist")) {
        System.out.println("in if in adminupdatecandidate");
        ArrayList<String> candidateId = (ArrayList<String>) request.getAttribute("candidateid");
        displayBlock.append("<option>Choose Id</option>");
        for (String c : candidateId) {
            displayBlock.append("<option value='" + c + "'>" + c + "</option>");
        }
        JSONObject json = new JSONObject();
        json.put("cids", displayBlock.toString());
        out.println(json);
        System.out.println("In adminshowcand");
        System.out.println(displayBlock);

    } else if (result != null && result.equals("details")) {
        System.out.println("in else if in adminupdatecandidate");
        CandidateDetails cd = (CandidateDetails) request.getAttribute("candidate");
        String str = "<img src='data:image/jpg;base64," + cd.getSymbol() + "' style='width:300px;height:200px;'/>";
        //String dynamicForm="";
        displayBlock.append("<table>");
        displayBlock.append("<tr><th>UserId:</th><td>"+cd.getUserId()+"</td></tr>");
        displayBlock.append("<tr><th>Candidate Name:- </th><td>"+cd.getCandidateName()+"</td></tr>");
        displayBlock.append("<tr><th>City:</th><td>"+cd.getCity()+"</td></tr>");
        displayBlock.append("<tr><th>Party:</th><td>"+cd.getParty()+"</td></tr>");
        displayBlock.append("<tr><th>Symbol:</th><td>"+str+"</td></tr>");
        displayBlock.append("</table>");
        
                ArrayList<String> city = CandidateDAO.getCity();
                
                StringBuffer sb = new StringBuffer();
                for(String c:city)
                {
                    sb.append("<option value='"+c+"'>"+c+"</option>");
                }
                
        //displayBlock.append("<tr><th><input type='button' value='Add Candidate' onclick='addcandidate()' id='addcnd'></th>\n");
       // displayBlock.append("<th><input type='reset' value='Clear' onclick='clearText()'></th></tr></table></form>\n");
        JSONObject json = new JSONObject();
        json.put("candidateid",cd.getCandidateId());
        json.put("userid",cd.getUserId());
        json.put("candidateName",cd.getCandidateName());
        json.put("city",sb.toString());
        json.put("party",cd.getParty());
        json.put("symbol",str);
    //json.put("subdetails", displayBlock.toString());
    out.println(json);
    System.out.println("In adminshowcand");
    System.out.println(displayBlock);
    }
    
%>