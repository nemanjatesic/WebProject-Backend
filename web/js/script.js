'use strict';
/*
    Use strict samo oznacava da se JS kod izvrsava u 'strict' modu.
    Odnosno da se sve promenljive moraju deklarisati pre koriscenja.
 */
function loadUsers() {

    let xhttp = new XMLHttpRequest();

    /*
    onreadystatechange.readyState
        0: request not initialized
        1: server connection established
        2: request received
        3: processing request
        4: request finished and response is ready
     */

    xhttp.onreadystatechange = function() {

        console.log(this.response);
        if (this.readyState === 4 && this.status === 200) {
            let result = JSON.parse(this.response);

            // Dohvati tabelu iz DOM-a po id-u
            let table = document.getElementById("users-tbl");

            // Dohvati prvi tbody
            let oldTBody = table.tBodies[0];
            let newTBody = document.createElement("tBody");

            // console.log("i = " + i)

            for (let i = 0; i < result.length; i++) {

                let bRow = document.createElement("tr"); // Kreiraj novi red

                // Postavi vrednosti za taj red iz rezultata sa servera
                let tdName = document.createElement("td");
                tdName.innerHTML = result[i].username;
                let tdColor = document.createElement("td");
                tdColor.innerHTML = result[i].password;

                bRow.appendChild(tdName);
                bRow.appendChild(tdColor);

                newTBody.appendChild(bRow)
            }

            // Zameni postojeci sa novim tBody-jem.
            // Na taj nacin ce uvek da se ispisuju svezi elementi a stari da nestanu.
            table.replaceChild(newTBody, oldTBody)
        }
    };

    xhttp.open("GET", "/rest/users", true);
    xhttp.setRequestHeader("Authorization","Bearer " + window.localStorage.getItem('jwt'));
    xhttp.send();
}

function createUser(firstName, lastName) {
    // Iako se klasa zove "XMLHttpRequest", moze da sluzi i za slanje JSON objekta
    // Samo je potrebno poslati odgovarajuci header da bi backend mogao pravilno da procita objekat
    let xhttp = new XMLHttpRequest();
    /* AJAX salje zahtev asinhrono, sto znaci da nam rezultat nece biti dostupan
        odmah, vec ce se izvrsavati u pozadini.
        Kada stigne onreadystatechange event, sa njim stize i rezultat.
    */
    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            // ovako mozemo pristupiti objektu koji smo dobili
            // console.log(this.response);
            loadUsers();
        }
    };

    /* Najcesce greske mogu da budu:
        - pogresna metoda GET/ POST...
        - pogresan endpoint
        - pogresan content type
    */
    xhttp.open("POST", "/rest/users", true);
    xhttp.setRequestHeader("Content-Type", "application/json");

    // Saljemo objekat u telu zahteva sa parametrima pretvoren u JSON
    xhttp.send(JSON.stringify({firstName: firstName, lastName:lastName}));
    // ovo nece prikazati novog korisnika zato sto asinhroni poziv nije zavrsen
    // loadUsers();
}

window.onload = function () { loadUsers(); };

// Zakaci event listener na klik dugmeta
document.getElementById("load-users-btn").addEventListener("click", loadUsers);

/*
    Ovde ce se nalaziti logika koja se pozvati prilikom submit-a forme.
 */
function processForm(e) {
    if (e.preventDefault) e.preventDefault();
    let formData = new FormData(e.target);

    let firstName = formData.get("firstName");
    let lastName = formData.get("lastName");
    createUser(firstName, lastName);

    // Obavezno vratiti false da bi se pregazilo default-no ponasanje prilikom submit-a.
    return false;
}

var form = document.getElementById('add-user-form');
if (form.attachEvent) {
    // IE support
    form.attachEvent("submit", processForm);
} else {
    form.addEventListener("submit", processForm);
}

// Dohvatanje query parametara iz url-a
var urlParams = new URLSearchParams(window.location.search);
var myParam = urlParams.get('myParam');
// console.log(myParam);