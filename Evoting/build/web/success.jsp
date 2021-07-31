<%-- 
    Document   : success
    Created on : 22 May, 2021, 6:36:52 AM
    Author     : hp
--%>

<%
    String userid=(String)session.getAttribute("userid");
    if(userid==null)
    {
        session.invalidate();
        response.sendRedirect("accessdenied.html");
        return ;
    }
    out.println("success");
%>