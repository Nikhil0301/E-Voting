<%-- 
    Document   : showexception
    Created on : 1 May, 2021, 7:19:46 PM
    Author     : hp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    Exception ex = (Exception)request.getAttribute("Exception");
    System.out.println("Exception is:- "+ex);
    out.println("Some exception occurred:- "+ex.getMessage());
    
%>
