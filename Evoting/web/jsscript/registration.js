let username, password, cpassword, city, address, adhar, email, mobile,gender;

function addUser()
{

    username = $("#username").val();
    password = $("#password").val();
    cpassword = $("#cpassword").val();
    city = $("#city").val();
    address = $("#address").val();
    adhar = $("#adhar").val();
    email = $("#email").val();
    mobile = $("#mobile").val();
    gender = $("input[name='gender']:checked").val();
    alert(gender);
    //xhr.fail() use if xhr.error() not worked;

    if (validateUser())
    {
        if (password != cpassword)
        {

            swal("Error !", "Passwords do not match", "error");
            return;
        } else {

            if (checkemail() === false)
                return;

            let data = {
                username: username,
                password: password,
                city: city,
                address: address,
                userid: adhar,
                email: email,
                mobile: mobile,
                gender:gender
            };

            let xhr = $.post("RegistrationControllerServlet", data, processresponse);
            xhr.error(handleError);
        }
    }
}
function processresponse(responseText, textstatus, xhr)
{
     
    let str = responseText.trim();
    alert(str);
    if (str === "success") {
        swal("Success!", "Registeration done successfully You can now login", "success");
        setTimeout(redirectUser, 3000);
    } else if (str === "uap")
        swal("Error", "sorry! the userid is already present!", "error");
    else
        swal("Error!", "Registeration failed", "error");
}
function handleError(xhr)
{
    swal("Error", "Problem in server communication:" + xhr.statusText, "error");
}
function validateUser()
{
    if (username === "" || password === "" || cpassword === "" || city === "" || address === "" || adhar === "" || email === "" || mobile === "")
    {
        swal("Error !", "All fields are mandotary", "error");
        return false;
    }
    return true;
}

function checkemail()
{
    let attheratepos = email.indexOf('@');
    let dotpos = email.indexOf('.');

    if (attheratepos < 1 || dotpos < attheratepos + 2 || dotpos >= email.length)
    {
        swal("Error !", "Please enter a valid email", "error");
        return false;
    }
   
    return true;
}
function handleError(xhr)
{
    swal("Error", "Problem in server communication: " + xhr.statusText, "error");
}
function redirectUser()
{
    window.location = "login.html";
}