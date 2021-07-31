<%-- 
    Document   : failure
    Created on : 22 May, 2021, 6:37:13 AM
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
    out.println("failed");
%>
