/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




function showaddcandidateform()
{
    removecandidateForm();
    var newdiv = document.createElement("div");
    newdiv.setAttribute("id", "candidateform");
    newdiv.setAttribute("float", "left");
    newdiv.setAttribute("padding-left", "12px");
    newdiv.setAttribute("border", "solid 2px red");
    newdiv.innerHTML = "<h3>Add New Candidate</h3>";
    newdiv.innerHTML = newdiv.innerHTML + "<form method='POST' enctype='multipart/form-data' id='fileUploadForm'>\n\
<table><tr><th>Candidate Id:</th><td><input type='text' id='cid'></td></tr>\n\
<tr><th>User Id:</th><td><input type='text' id='uid' onkeypress='return getdetails(event)'></td></tr>\n\
<tr><th>Candidate Name:</th><td><input type='text' id='cname'></td></tr>\n\
<tr><th>City:</th><td><select id='city'></select></td></tr>\n\
<tr><th>Party:</th><td><input type='text' id='party'></td></tr>\n\
<tr><td colspan='2'><input type='file' name='files' value='Select Image'></td></tr>\n\
<tr><th><input type='button' value='Add Candidate' onclick='addcandidate()' id='addcnd'></th>\n\
<th><input type='reset' value='Clear' onclick='clearText()'></th></tr></table></form>";
    newdiv.innerHTML = newdiv.innerHTML + "<br><span id='addresp'></span>";
    var addcand = $("#result")[0];
    addcand.appendChild(newdiv);
    $("#candidateform").hide();
    $("#candidateform").fadeIn(3500);
    data = {id: "getid"};
    $.post("AddCandidateControllerServlet", data,
            function (responseText) {
                $("#cid").val(responseText);
                $('#cid').prop("disabled", true);
            });

}

function addcandidate()
{
    var form = $('#fileUploadForm')[0];
    var data = new FormData(form);
    alert(data);
    var cid = $("#cid").val();
    var cname = $("#cname").val();
    var city = $("#city").val();
    var party = $("#party").val();
    var uid = $("#uid").val();
    data.append("cid", cid);
    data.append("uid", uid);
    data.append("cname", cname);
    data.append("party", party);
    data.append("city", city);
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "AddNewCandidateControllerServlet",
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            str = data + "....";
            swal("Admin!", str, "success").then((value) => {
                showaddcandidateform();
            });
//                $("#addresp").text(str).css("color","green").css("font-weight","bold");
//                window.setTimeout(function(){showaddcandidateform();},5000);
        },
        error: function (e) {
            swal("Admin!", e, "error");
        }
    });
}

function updatecandidate()
{
    
    var form = $('#fileUploadForm')[0];
    var data = new FormData(form);
    alert(data);
    var cid = $("#cid").val();
    var cname = $("#cname").val();
    var city = $("#city").val();
    var party = $("#party").val();
    var uid = $("#uid").val();
    data.append("cid", cid);
    data.append("uid", uid);
    data.append("cname", cname);
    data.append("party", party);
    data.append("city", city);
    
    $.ajax({
        type: "POST",
        enctype: 'multipart/form-data',
        url: "UpdateCandidateControllerServlet",
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        timeout: 600000,
        success: function (data) {
            str = data + "....";
            swal("Admin!", str, "success").then((value) => {
                showupdatecandidateform();
            });
        },
        error: function (e) {
            swal("Admin!", e, "error");
        }
    });
    
}


function clearText()
{
    $("#addresp").html("");
}

function getdetails(e)
{
    if (e.keyCode === 13)
    {
        data = {uid: $("#uid").val()};
        $.post("AddCandidateControllerServlet", data, function (responseText)
        {
            let details = JSON.parse(responseText);
            let city = details.city;
            let uname = details.username;
            if (uname === "wrong")
                swal("Wrong Adhaar No!", "Adhaar no is invalid!", "error");
            else
            {
                $("#cname").val(uname);
                $("#city").empty();
                $("#city").append(city);
                $("#cname").prop("disabled", true);
            }
        });
    }
}

function removecandidateForm()
{
    var contdiv = document.getElementById("result");
    var formdiv = document.getElementById("candidateform");
    if (formdiv !== null)
    {
        $("#candidateform").fadeOut("3500");
        contdiv.removeChild(formdiv);
    }
}

function removeupdatecandidateForm()
{
    var contdiv = document.getElementById("result");
    var formdiv = document.getElementById("candidform");
    if (formdiv !== null)
    {
        $("#candidform").fadeOut("3500");
        contdiv.removeChild(formdiv);
    }
}

function removeuserForm(){
    var contdiv = document.getElementById("result");
    var formdiv = document.getElementById("userform");
    if (formdiv !== null)
    {
        $("#userform").fadeOut("3500");
        contdiv.removeChild(formdiv);
    }
}

function redirectadministratorpage()
{
    swal("Admin!", "Redirecting To Admin Actions Page!", "success").then(value => {
        window.location = "adminactions.jsp";
    });
}
function redirectvotingpage()
{
    swal("Admin!", "Redirecting To Voting Controller Page!", "success").then(value => {
        window.location = "VotingControllerServlet";
    });
}
function manageuser()
{
    swal("Admin!", "Redirecting To User Management Page!", "success").then(value => {
        window.location = "manageuser.jsp";
    });
}
function managecandidate()
{
    swal("Admin!", "Redirecting To Candidate Management Page!", "success").then(value => {
        window.location = "managecandidate.jsp";
    });
}

function showresult()
{
    swal("Admin!", "Redirecting To Show Result Page!", "success").then(value => {
        window.location = "showresult.jsp";
    });
}

function genderwise()
{
    swal("Admin!", "Redirecting To Voting Controller Page!", "success").then(value => {
        window.location = "gender_wise_chart.html";
    });
}



function showcandidatewise(){
    var newdiv = document.createElement("div");
    newdiv.setAttribute("id", "candidateform");
    newdiv.setAttribute("float", "left");
    newdiv.setAttribute("padding-left", "12px");
    newdiv.setAttribute("border", "solid 2px red");
    newdiv.innerHTML = "<h3>Result candidate wisee</h3>";
    newdiv.innerHTML = newdiv.innerHTML + "<br><span id='addresp'></span>";
    var addcand = $("#result")[0];
    addcand.appendChild(newdiv);
    data={data:'candidatewise'};
        $.post("ElectionResultControllerServlet",data,function(responseText) {
            clearText();
            //$("#addresp").append(responseText.trim());
            $("#result").html(responseText.trim());
        });
}

function showpartywise(){
    
    var newdiv = document.createElement("div");
    newdiv.setAttribute("id", "candidateform");
    newdiv.setAttribute("float", "left");
    newdiv.setAttribute("padding-left", "12px");
    newdiv.setAttribute("border", "solid 2px red");
    newdiv.innerHTML = "<h3>Result party wisee</h3>";
    newdiv.innerHTML = newdiv.innerHTML + "<br><span id='addresp'></span>";
    var addcand = $("#result")[0];
    addcand.appendChild(newdiv);
    data={data:'partywise'};
        $.post("ElectionResultControllerServlet",data,function(responseText) {
            clearText();
            //$("#addresp").append(responseText.trim());
            $("#result").html(responseText.trim());
        });
}

function showvotingpercentage()
{
        swal("Admin!", "Redirecting to Gender wise voting percentage", "success").then(value => {
        window.location = "genderwise.html";
    });  
}

function showcandidate() {
    
     removecandidateForm();
    
    var newdiv = document.createElement("div");
    newdiv.setAttribute("id", "candidateform");
    newdiv.setAttribute("float", "left");
    newdiv.setAttribute("padding-left", "12px");
    newdiv.setAttribute("border", "solid 2px red");
    newdiv.innerHTML = "<h3>Show Candidate</h3>";
    newdiv.innerHTML = newdiv.innerHTML + "<div style='color:white;font-weight:bold'>Candidate Id:</div><div><select id='cid'></select></div>";
    newdiv.innerHTML = newdiv.innerHTML + "<br><span id='addresp'></span>";
    var addcand = $("#result")[0];
    addcand.appendChild(newdiv);
    $("#candidateform").hide();
    $("#candidateform").fadeIn(3500);
    data = {data: "cid"};
    $.post("ShowCandidateControllerServlet", data, function (responseText) {
        var candidlist = JSON.parse(responseText);
        $("#cid").append(candidlist.cids);
    });

    $("#cid").change(function () {
        var cid = $("#cid").val();
        if (cid === '')
        {
            swal("No selection!", "Please select an id", "error");
            return;
        }

        data = {data: cid};
        $.post("ShowCandidateControllerServlet", data, function (responseText) {
            clearText();
            var details = JSON.parse(responseText);
            $("#addresp").append(details.subdetails);
        });

    });


}


function showupdatecandidateform() {
    
    removeupdatecandidateForm();
    removecandidateForm();
    var newdiv = document.createElement("div");
    newdiv.setAttribute("id", "candidateform");
    newdiv.setAttribute("float", "left");
    newdiv.setAttribute("padding-left", "12px");
    newdiv.setAttribute("border", "solid 2px red");
    newdiv.innerHTML = "<h3>Update Candidate</h3>";
    newdiv.innerHTML = newdiv.innerHTML + "<div style='color:white;font-weight:bold'>Candidate Id:</div><div><select id='cid'></select></div>";
    newdiv.innerHTML = newdiv.innerHTML + "<br><span id='addresp'></span>";
    var addcand = $("#result")[0];
    addcand.appendChild(newdiv);
    $("#candidateform").hide();
    $("#candidateform").fadeIn(3500);
    data = {data: "cid"};
    $.post("EditCandidateControllerServlet", data, function (responseText) {
        var candidlist = JSON.parse(responseText);
        $("#cid").append(candidlist.cids);
    });

    $("#cid").change(function () {
        var cid = $("#cid").val();
        if (cid ==='Choose Id')
        {
            swal("No selection!", "Please select an id", "error");
            return;
        }

        data = {data: cid};
        $.post("EditCandidateControllerServlet", data, function (responseText) {
            
            removeupdatecandidateForm();
            var newdiv = document.createElement("div");
            newdiv.setAttribute("id", "candidform");
            newdiv.setAttribute("float", "left");
            newdiv.setAttribute("padding-left", "12px");
            newdiv.setAttribute("border", "solid 2px red");
            newdiv.innerHTML = "<h3>Update Candidate</h3>";
            newdiv.innerHTML = newdiv.innerHTML + "<form method='POST' enctype='multipart/form-data' id='fileUploadForm'>\n\
<table>\n\
<tr><th>User Id:</th><td><input type='text' id='uid'></td></tr>\n\
<tr><th>Candidate Name:</th><td><input type='text' id='cname'></td></tr>\n\
<tr><th>City:</th><td><select id='city'></select></td></tr>\n\
<tr><th>Party:</th><td><input type='text' id='party'></td></tr><tr><th>Symbol:</th><td id='symbol'></td></tr>\n\
<tr><td colspan='2'><input type='file' name='files' value='Change Image'></td></tr>\n\
<tr><th><input type='button' value='update Candidate' onclick='updatecandidate()' id='updcnd'></th>\n\
<th><input type='reset' value='Clear' onclick='clearText()'></th></tr></table></form>";
            newdiv.innerHTML = newdiv.innerHTML + "<br><span id='addresp'></span>";
            var addcand = $("#result")[0];
            addcand.appendChild(newdiv);
            $("#candidform").hide();
            $("#candidform").fadeIn(3500);
            clearText();
            var details = JSON.parse(responseText);
            
            $("#uid").val(details.userid);
            $("#uid").prop("disabled",true);
            $("#cname").val(details.candidateName);
            $("#cname").prop("disabled",true);
            $("#city").empty();
            $("#city").append(details.city);
            $("#party").val(details.party);
            $("#symbol").append(details.symbol);
           // $("#cname").val(details.party);
        });

    });


}

function deletecandidate(){
    
    
    removecandidateForm();
    var newdiv = document.createElement("div");
    newdiv.setAttribute("id", "candidateform");
    newdiv.setAttribute("float", "left");
    newdiv.setAttribute("padding-left", "12px");
    newdiv.setAttribute("border", "solid 2px red");
    newdiv.innerHTML = "<h3>Delete Candidate</h3>";
    newdiv.innerHTML = newdiv.innerHTML + "<div style='color:white;font-weight:bold'>Candidate Id:</div><div><select id='cid'></select></div>";
    newdiv.innerHTML = newdiv.innerHTML + "<br><span id='addresp'></span>";
    var addcand = $("#result")[0];
    addcand.appendChild(newdiv);
    $("#candidateform").hide();
    $("#candidateform").fadeIn(3500);
    data = {data: "cid"};
    $.post("DeleteCandidateControllerServlet", data, function (responseText) {
        var candidlist = JSON.parse(responseText);
        $("#cid").append(candidlist.cids);
    });

    $("#cid").change(function () {
        var cid = $("#cid").val();
        if (cid === 'Choose Id')
        {
            swal("No selection!", "Please select an id", "error");
            return;
        }

        data = {data:cid,del:null};
        $.post("DeleteCandidateControllerServlet", data, function (responseText) {
            clearText();
            var details = JSON.parse(responseText);
            $("#addresp").append(details.subdetails);
        });

    });
    
    
}

function delcandidate(){
    var cid = $("#cid").val();
    data = {data:cid,del:"del"};
    alert(data+","+cid);
    $.post("DeleteCandidateControllerServlet", data,processResponse);
}


function processResponse(responseText){
    //Check the response ,how the popup , redirect to votingresponse
    if(responseText.trim()==="success")
    {
        swal("Success","Candidate deleted successfully","success").then((value)=>{
                deletecandidate();
        }); 
    }
    else
    {
        swal("Failure","Candidate cannot deleted","error").then((value)=>{
               deletecandidate();
        });  
        
    }
}


function electionresult() {
    $.post("ElectionResultControllerServlet", null, function (responseText) {
        
        swal("Result fetched!", "Success", "success");

        $("#result").html(responseText.trim());
    });
}

function showusers(){
    
    var newdiv = document.createElement("div");
    newdiv.setAttribute("id", "candidateform");
    newdiv.setAttribute("float", "left");
    newdiv.setAttribute("padding-left", "12px");
    newdiv.setAttribute("border", "solid 2px red");
    newdiv.innerHTML = "<h3>Show users</h3>";
    newdiv.innerHTML = newdiv.innerHTML + "<br><span id='addresp'></span>";
    var addcand = $("#result")[0];
    addcand.appendChild(newdiv);
        $.post("ShowUsersControllerServlet",function (responseText) {
            clearText();
            var details = JSON.parse(responseText);
            $("#addresp").append(details.userdetails);
        });
}


function deleteuser(){
    
    removeuserForm();
    var newdiv = document.createElement("div");
    newdiv.setAttribute("id", "userform");
    newdiv.setAttribute("float", "left");
    newdiv.setAttribute("padding-left", "12px");
    newdiv.setAttribute("border", "solid 2px red");
    newdiv.innerHTML = "<h3>Delete User</h3>";
    newdiv.innerHTML = newdiv.innerHTML + "<div style='color:white;font-weight:bold'>User Id:</div><div><select id='uid'></select></div>";
    newdiv.innerHTML = newdiv.innerHTML + "<br><span id='addresp'></span>";
    var addcand = $("#result")[0];
    addcand.appendChild(newdiv);
    $("#userform").hide();
    $("#userform").fadeIn(3500);
    data = {data: "uid"};
    $.post("DeleteUserControllerServlet", data, function (responseText) {
        var candidlist = JSON.parse(responseText);
        $("#uid").append(candidlist.uids);
    });

    $("#uid").change(function () {
        var uid = $("#uid").val();
        if (uid === 'Choose Id')
        {
            swal("No selection!", "Please select an id", "error");
            return;
        }

        data = {data:uid,del:null};
        $.post("DeleteUserControllerServlet", data, function (responseText) {
            clearText();
            var details = JSON.parse(responseText);
            $("#addresp").append(details.subdetails);
        });

    });
}

function deluser(){
    var uid = $("#uid").val();
    data = {data:uid,del:"del"};
    alert(data+","+uid);
    $.post("DeleteUserControllerServlet", data,processResponse2);
}

function processResponse2(responseText){
    //Check the response ,how the popup , redirect to votingresponse
    if(responseText.trim()==="success")
    {
        swal("Success","User deleted successfully","success").then((value)=>{
                deleteuser();
        }); 
    }
    else
    {
        swal("Failure","User cannot deleted","error").then((value)=>{
               deleteuser();
        });  
        
    }
}