"use strict";
import {byId, toon, setText} from "./util.js";
const klant = JSON.parse(sessionStorage.getItem("klant"));
setText("naam",klant.voornaam +" "+klant.familienaam);
const filmInMandje = JSON.parse(sessionStorage.getItem("filmInMandje"));
const aantalFilms = Array.isArray(filmInMandje) ? filmInMandje.length : 0;
setText("aantalFilms",aantalFilms);
byId("bevestig").onclick = async () => {
    const mandje = mandjeArray()
    const alleIds = mandje.map(items =>items.id);
    const nietGereserveerd = await checkEnMaakReservaties(klant.id,alleIds);
    const filmResultaat = byId("filmResultaat");
    for (const film of filmInMandje) {
        const tr = filmResultaat.insertRow();
        const td = tr.insertCell();
        td.textContent= `${film.titel}`;
        const status = nietGereserveerd.includes(film.id) ? "Uitverkocht" : "OK";
        tr.insertCell().textContent = status
    }
    sessionStorage.removeItem("filmInMandje");
    sessionStorage.removeItem("genres");
    byId("bevestig").disabled = true;
    byId("mandjesLink").hidden = true

}

function mandjeArray() {
    const opgeslagen = sessionStorage.getItem("filmInMandje");
    return opgeslagen ? JSON.parse(opgeslagen) : [];
}
async function checkEnMaakReservaties(id,alleIds) {
    const response = await fetch("films",
        {
            method: "POST",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({
                klantId : id,
                filmIds : alleIds
            })

        });
    if (response.ok){
         return await response.json();
    }
    else {
        const responseBody = await response.json();
        if (response.status === 400) {
          //  const responseBody = await response.json();
            setText("badRequest", responseBody);
            toon("badRequest")
        } else {
           // const responseBody = await response.json();
            setText("storing", response.body);
            toon("storing");
        }
    }
}