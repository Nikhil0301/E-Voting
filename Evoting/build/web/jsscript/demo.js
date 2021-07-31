/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function addcandidate()
{
    var form=$('fileUploadForm')[0];
    var data = new FormData(form);
    alert(data);
    var cid=$("#cid").val();
    var cname=$("#cname").val();
    var city=$("#city").val();
    var party = $("#party").val();
    var uid=$("#uid").val();
    data.append("cid",cid);
    data.append("cname",cname);
    data.append("party",city);
    data.append("uid",uid);
    
    $.ajax()
    
}
function getdetails(e)
{
    if(e.keyCode===13)
    {
        data={uid:$("#uid").val()};
        $.post("AddControllerServlet",data,function(responseText)
        {
            let details = JSON.parse(responseText);
            let city = details.city;
            let uname = details.username;
            if(uname==="wrong")
                swal("Wrong Adhaar No!","Adhar no. is invalid!","error");
            else
            {
                $("#cname").val(uname);
                $("#city").empty();
                $("#city").append(city);
                $("#cname").prop("disabled",true);
            }
        });
    }
}

function removecandidateForm()
{
    var contdiv = document.getElementById("result");
    var formdiv = document.getElementById("candidateform");
    if(formdiv!=null)
    {
        $('#candidateform').fadeOut("3500");
        contdiv.removeChild(formdiv);
    }
}

function redirectadministratorpage()
{
    swal("Admin!","Redirecting To Admin Actions Page!","success").then(value=>{
        window.location="adminactions.jsp";
    });
}
function redirectvotingpage()
{
    swal("Admin!","Redirecting To Voting Controller Page!","success").then(value=>{
        window.location="VotingControllerServlet";
    });
}
function manageuser()
{
    swal("Admin!","Redirecting To User Management Page!","success").then(value=>{
        window.location="manageuser.jsp";
    });
}
function managecandidate()
{
    swal("Admin!","Redirecting To Candidate Management Page!","success").then(value=>{
        window.location="managecandidate.jsp";
    });
}


