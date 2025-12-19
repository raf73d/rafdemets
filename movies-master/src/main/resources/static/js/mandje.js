"use strict";
import {toon, setText} from "./util.js";
const films = JSON.parse(sessionStorage.getItem("filmInMandje"));
if (films.length > 0){
    toon("totaal");
    var totalePrijs = 0;
    for (const film of films){
        const tr = mandjeBody.insertRow();
        tr.insertCell().textContent = film.titel;
        tr.insertCell().textContent = film.prijs;
        totalePrijs += film.prijs
    }
    setText("totalePrijs", totalePrijs);
}