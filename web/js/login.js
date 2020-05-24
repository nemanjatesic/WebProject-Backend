function login(e) {
    if(e.preventDefault()) e.preventDefault();
    let formData = new FormData(e.target)

    let username = formData.get("username");
    let password = formData.get("password");

    $.ajax({
        url: "/rest/users/login",
        type:"GET",
        data: {
            username: username,
            password: password
        },
        contentType:"application/json",
        dataType:"json",
        success: function(data){
            console.log("Odgovor servera:");
            console.log(data);
            console.log("Token:");
            console.log(data.JWTToken);
            window.localStorage.setItem('jwt', data.JWTToken);
            // redirekcija
            window.location.replace("/index.jsp");
        },
        error: function(data){
            console.log(data);
        }
        // complete: function(data, xhr) {
        //     status
        //     console.log(xhr)
        //     console.log("Odgovor servera: " + data.responseText);
        //     console.log("Token: " + JSON.parse(data.responseText).JWTToken);
        //     window.localStorage.setItem('jwt', JSON.parse(data.responseText).JWTToken);
        //     // redirekcija
        //     // window.location.replace("/index.jsp");
        // }
    });

    return false;
}
var form = document.getElementById('login-form');
form.addEventListener("submit", login);