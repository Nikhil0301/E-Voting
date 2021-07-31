/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function addVote(){
    //Pick up the selected candidate id and call the AddVoteControllerServlet
    alert("add vote");
    var id=$('input[type=radio][name=flat]:checked').attr('id');
    alert(id);
    data={candidate:id};
    //alert(candidate);
    $.post("AddVoteControllerServlet",data,processResponse);
    
}

function processResponse(responseText){
    //Check the response ,how the popup , redirect to votingresponse
    if(responseText.trim()==="success")
    {
        swal("Success","Voting done","success").then((value)=>{
                window.location="votingresponse.jsp";
        }); 
    }
    else
    {
        swal("Failure","Voting failed","error").then((value)=>{
                window.location="votingresponse.jsp";
        });  
        
    }
}