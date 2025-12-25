"use strict"
import {byId,verberg,toon} from "./util.js";
// op die manier wordt eerst de html opgebouwd en dan pas js uitgevoerd
window.addEventListener("DOMContentLoaded", async () => {
    const aantalResponse = await fetch(`/mensen`);
    if (aantalResponse.ok) {
        const aantalRecords = await aantalResponse.json();
        byId("maxgrens").textContent = aantalRecords.total_records + 1;
        byId("input").max = aantalRecords.total_records+1;


    }
});


byId("checkButton").onclick = async () => {
    verberg("mededeling","mededeling2","thead","thead2","thead3","databaseButton","probleemDatabase","dubbelPersoonDatabase","naamPersoon");
    toon("mededeling3");
    startTabel.innerHTML="";
    byId("filmsTabel").innerHTML="";
    byId("shipsTabel").innerHTML="";
    const idInput = byId("input");
    if (idInput.checkValidity()) {
        findbyId(idInput.value);
    } else {
        toon("mededeling2");
        verberg("thead","mededeling","mededeling3");
        startTabel.innerHTML="";
        idInput.value = "";
    }
};
byId("databaseButton").onclick = async function () {
    const figuur = JSON.parse(sessionStorage.getItem("figuur"));
    const databaseResponse = await fetch("database",
        {
            method: "POST",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(figuur)
        });

    if (databaseResponse.ok) {
        const id = await databaseResponse.json();
        toon("naamPersoon");
        byId("naamFiguur").textContent = figuur.naam;
        byId("idFiguur").textContent = id.toString();
        verberg("databaseButton");
    } else {
        if (databaseResponse.status === 409) {
            toon("dubbelPersoonDatabase");
            verberg("databaseButton");
        } else {
            toon("probleemDatabase");
            verberg("databaseButton");
        }
    }
}

async function findbyId(id) {
    const response = await fetch(`/mensen/${id}`)
    if (response.ok) {
        verberg("mededeling3");
        toon("thead");
        toon("databaseButton");
        const mensgegevens = await response.json();
        const startTabel = byId("startTabel");
        startTabel.innerHTML = "";
        const rij = startTabel.insertRow();
        rij.insertCell().textContent = mensgegevens.naam;
        rij.insertCell().textContent = mensgegevens.hoogte;
        rij.insertCell().textContent = mensgegevens.gewicht;
        rij.insertCell().textContent = mensgegevens.aangemaaktOp;
        const figuur ={
            naam : mensgegevens.naam,
            hoogte : mensgegevens.hoogte,
            gewicht : mensgegevens.gewicht
        };
        console.log("figuur opgeslagen:", figuur);
        sessionStorage.setItem("figuur", JSON.stringify(figuur));
        toon("thead2")
        const startTabelFilm = byId("filmsTabel");
        startTabelFilm.innerHTML = "";
        let aantalFilms = mensgegevens.films.length;
        for (let i = 0; i < aantalFilms; i++) {
            const rijFilm = startTabelFilm.insertRow();
            rijFilm.insertCell().textContent = mensgegevens.films[i];
        }
        //betreft spaceships
        let aantalShips = mensgegevens.starships.length
        if ( aantalShips  > 0) {
            const startTabelShip = byId("shipsTabel");
            startTabelShip.innerHTML = "";
            toon("thead3");
            for (let i = 0; i < aantalShips; i++) {
              //  const hyperlink = document.createElement("a");
              //  hyperlink.textContent = "";
             //   hyperlink.href = "https://www.google.com/search?q=" + encodeURIComponent(mensgegevens.starships[i].naam);
               //  hyperlink.classList.add("text-info", "text-decoration-none");
               // hyperlink.target = "_blank";
                const rijShip = startTabelShip.insertRow();
                rijShip.insertCell().textContent = mensgegevens.starships[i].naam;
                rijShip.insertCell().textContent = mensgegevens.starships[i].model;
                rijShip.insertCell().textContent = mensgegevens.starships[i].fabrikant;
                rijShip.insertCell().textContent = mensgegevens.starships[i].klasse;
              //  rijShip.appendChild(hyperlink);
            }
        }
    }
    else
    {toon("mededeling");
        verberg("thead","mededeling2","mededeling3");
        startTabel.innerHTML="";
        byId("input").value = "";
    }
}