<%-- 
    Document   : loginresponse.jsp
    Created on : 5 May, 2021, 9:54:13 AM
    Author     : hp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String userid  = (String)request.getAttribute("userid");
    String result= (String)request.getAttribute("result");
    //String url="";
    if(userid!=null && result!=null)
    {
        HttpSession sess = request.getSession();
        sess.setAttribute("userid",userid);
        String url="";
        if(result.equalsIgnoreCase("Admin"))
        {
             url = "AdminControllerServlet;jsessionid="+sess.getId();
        }
        else{
             url = "VotingControllerServlet;jsessionid="+sess.getId();
        }
        out.println(url);
            
    }
    else
       out.println("error");
       
%>
