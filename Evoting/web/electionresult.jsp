

<%@page import="org.json.JSONObject"%>
<%@page import="java.util.HashMap"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="evoting.dto.CandidateDetails,java.util.LinkedHashMap,java.util.Iterator,evoting.dto.PartyDTO,java.util.Map" %>
<%
  String userid=(String)session.getAttribute("userid");
  if(userid==null){
      response.sendRedirect("accessdenied.html");
      return;
  }
  String s= (String)request.getAttribute("res");
  if(s.equalsIgnoreCase("partywise"))
  {
       //System.out.println("partywise : partyresult");
       LinkedHashMap<PartyDTO,Integer> resultDetails=(LinkedHashMap)request.getAttribute("result");
       int votecount = (int)request.getAttribute("votecount");
       Iterator itr=resultDetails.entrySet().iterator();
       StringBuffer displayBlock=new StringBuffer(s+"<table border='2'>");
 
  displayBlock.append("<tr><th>Party name</th><th>Symbol</th><th>Vote count</th><th>Vote %</th></tr>");
  while(itr.hasNext()){
      Map.Entry<PartyDTO,Integer> e=(Map.Entry)itr.next();
      PartyDTO party=e.getKey();
      float voteper=(e.getValue()*100.0f)/votecount;
      displayBlock.append("<tr><td>"+party.getParty()+"</td>"
                         + "<td><img src='data:image/jpg;base64,"+party.getSymbol()+"'style='width:300px;height:200px;'/></td>"
                         + "<td>"+e.getValue()+"</td>"
                         + "<td>"+voteper+"</td></tr>");
  }
  displayBlock.append("</table>");
  out.println(displayBlock);
  
  }
  else if(s.equalsIgnoreCase("candidatewise")){
     
     // System.out.println("candidatewise : candidate");
  LinkedHashMap<CandidateDetails,Integer> resultDetails=(LinkedHashMap)request.getAttribute("result");
  int votecount = (int)request.getAttribute("votecount");
  Iterator itr=resultDetails.entrySet().iterator();
  StringBuffer displayBlock=new StringBuffer(s+"<table border='2'>");
 
  displayBlock.append("<tr><th>Candidate Id</th><th>Candidate Name</th><th>Party</th><th>Symbol</th><th>City</th><th>Vote count</th><th>Vote %</th></tr>");
  while(itr.hasNext()){
      Map.Entry<CandidateDetails,Integer> e=(Map.Entry)itr.next();
      CandidateDetails cd=e.getKey();
      float voteper=(e.getValue()*100.0f)/votecount;
      displayBlock.append("<tr><td>"+cd.getCandidateId()+"</td>"
                         + "<td>"+cd.getCandidateName()+"</td>"
                         + "<td>"+cd.getParty()+"</td>"
                         + "<td><img src='data:image/jpg;base64,"+cd.getSymbol()+"'style='width:300px;height:200px;'/></td>"
                         + "<td>"+cd.getCity()+"</td>"
                         + "<td>"+e.getValue()+"</td>"
                         + "<td>"+voteper+"</td></tr>");
  }
  displayBlock.append("</table>");
  out.println(displayBlock);
  }
  else{
      Map<String,Integer> resultDetails=(HashMap)request.getAttribute("result");
      int male=resultDetails.get("male");
      int female=resultDetails.get("female");
      int total_votecount=male+female;
      float malevoteper=(male * 100.0f)/total_votecount;
      float femalevoteper=(female * 100.0f)/total_votecount;
      JSONObject json = new JSONObject();
      json.put("male",malevoteper);
      json.put("female",femalevoteper);
      json.put("div","<div id='result'><div>");
      out.println(json);
  }
  
%>
