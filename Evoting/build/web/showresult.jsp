<%-- 
    Document   : showresult
    Created on : 29 Jun, 2021, 3:13:02 PM
    Author     : hp
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="jsscript/adminoptions.js"></script>
        <script src="jsscript/jquery.js"></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js"></script>
        <link href="stylesheet/backgroundimage.css" rel="stylesheet">
        <link href="stylesheet/pageheader.css" rel="stylesheet">
        <link href="stylesheet/admin.css" rel="stylesheet">
        <title>manage candidate</title>
    </head>
    <body>
        <%
            String userid = (String) session.getAttribute("userid");
            if (userid == null) {
                response.sendRedirect("accessdenied.html");
                return;
            }
            StringBuffer displayBlock = new StringBuffer("<canvas id='myChart style='width:100%;max-width:600px'></canvas>");
            displayBlock.append("<div class='sticky'><div class='candidate'>VOTE FOR CHANGE</div><br>"
                    + "<div class='subcandidate'>Admin show result Page</div><br><br>");
            displayBlock.append("<div class='logout'><a href='login.html'>logout</a></div></div>");
            displayBlock.append("<div class='container'>");
            displayBlock.append("<div id='dv1' onclick='showcandidatewise()'>"
                    + "<img src='images/candidate_wise.jpg' height='300px' width='300px'>"
                    + "<br><h3>Show Result candidate wise</h3></div>");
            displayBlock.append("<div id='dv2' onclick='showpartywise()'>"
                    + "<img src='images/partywise.jpeg' height='300px' width='300px'>"
                    + "<br><h3>Show Result party wise</h3></div>");
            
            displayBlock.append("<div id='dv2' onclick='showvotingpercentage()'>"
                    + "<img src='images/gender_wise.png' height='300px' width='300px'>"
                    + "<br><h3>Show gender wise voting ratio</h3></div>");
            
            displayBlock.append("</div>");
            displayBlock.append("<br><br><div align='center' id='result'></div>");
            out.println(displayBlock);
        %>
    </body>
</html>
