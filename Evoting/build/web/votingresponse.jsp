<%-- 
    Document   : votingresponse
    Created on : 8 Jun, 2021, 7:22:53 PM
    Author     : hp
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="evoting.dto.CandidateInfo"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <!--        <script src="jsscript/vote.js"></script>
                <script src="jsscript/jquery.js"></script>-->
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <link href="stylesheet/backgroundimage.css" rel="stylesheet">
        <link href="stylesheet/pageheader.css" rel="stylesheet">
        <link href="stylesheet/showcandidate.css" rel="stylesheet">
        <title>Voting Details</title>
    </head>
    <body>
        <%
            String userid = (String) session.getAttribute("userid");
            if (userid == null) 
            {
                session.invalidate();
                response.sendRedirect("accessdenied.html");
                return;
            }
            CandidateInfo c=(CandidateInfo)session.getAttribute("candidate");
            StringBuffer displayBlock = new StringBuffer();
            displayBlock.append("<div class='sticky'><div class='candidate'>VOTE FOR CHANGE</div><br>");
            if(c==null)
            {
                displayBlock.append("<div class='subcandidate'>Sorry! Your vote should not be casted</div><br><br>");
                displayBlock.append("<div><h4 id='logout'><a href='LoginControllerServlet?logout=logout'>Logout</a></h4></div>");
                out.println(displayBlock);
            }
            else
            {
              displayBlock.append("<div><h4 id='logout'><a href='LoginControllerServlet?logout=logout'>Logout</a></h4></div>");
               displayBlock.append("<div class='subcandidate'>Thank you for voting!</div><br><br>");
               displayBlock.append("<br><div class='candidateprofile'>Your vote added successfully!</div>");
               displayBlock.append("<br><div class='candidateprofile'><p>CandidateId:"+c.getCandidateId()+"</p><br>");
               displayBlock.append("<strong>You voted for </strong><br><img src='data:image/jpg;base64,"+c.getSymbol()+"' style='width:300px;height:200px;'/></label>");
               displayBlock.append("<br><div class='candidateprofile'><p>CandidateId:"+c.getCandidateId()+"</p><br>");
               displayBlock.append("Candidate Name:"+c.getCandidateName()+"<br>");
               displayBlock.append("Party:"+c.getParty()+"<br></div>");
               out.println(displayBlock);
            }
        %>

    </body>
</html>
