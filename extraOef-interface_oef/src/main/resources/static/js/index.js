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
    verberg("mededeling","mededeling2","thead","thead2");
    startTabel.innerHTML="";
    byId("filmsTabel").innerHTML="";
    const idInput = byId("input");
    if (idInput.checkValidity()) {
        findbyId(idInput.value);
    } else {
        toon("mededeling2");
        verberg("thead","mededeling");
        startTabel.innerHTML="";
        idInput.value = "";
    }
};
async function findbyId(id) {
    const response = await fetch(`/mensen/${id}`)
    if (response.ok) {
        toon("thead");
        const mensgegevens = await response.json();
        const startTabel = byId("startTabel");
        startTabel.innerHTML = "";
        const rij = startTabel.insertRow();
        rij.insertCell().textContent = mensgegevens.naam;
        rij.insertCell().textContent = mensgegevens.hoogte;
        rij.insertCell().textContent = mensgegevens.gewicht;
        rij.insertCell().textContent = mensgegevens.aangemaaktOp;
        toon("thead2")
        const startTabelFilm = byId("filmsTabel");
        startTabelFilm.innerHTML = "";

        let aantalFilms = mensgegevens.films.length;
        for (let i = 0; i < aantalFilms; i++) {
            const rijFilm = startTabelFilm.insertRow();
            rijFilm.insertCell().textContent = mensgegevens.films[i];
        }
    }
    else
    {toon("mededeling");
        verberg("thead","mededeling2");
        startTabel.innerHTML="";
        byId("input").value = "";
    }
}