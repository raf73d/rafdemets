"use strict";
import {byId, setText,verberg,toon} from "./util.js";

const filmIdTitel = JSON.parse(sessionStorage.getItem("filmIdTitel"));
setText("titelFilm",filmIdTitel.titel)
const img = document.createElement("img");
img.src = `images/${filmIdTitel.id}.jpg`;
img.alt = `Foto van ${filmIdTitel.titel}`;
byId("foto").appendChild(img);
verberg("mededeling","storing");
const filmResponse = await fetch(`films/${filmIdTitel.id}`);
if (filmResponse.ok) {
    const film = await filmResponse.json();
    setText("prijs", film.prijs);
    setText("voorraad", film.voorraad);
    setText("gereserveerd", film.gereserveerd);
    var beschikbaar = film.voorraad - film.gereserveerd;
    setText("beschikbaar", beschikbaar);
    if (!beschikbaar) {
        byId("inMandje").disabled = true;
    }

    byId("inMandje").onclick = async (e) => {
        const filmIdTitelPrijs = {
            id: filmIdTitel.id,
            titel: filmIdTitel.titel,
            prijs: film.prijs
        }
        const mandje = mandjeArray()
        const alleIds = mandje.map(items =>items.id);
        if (!alleIds.includes(filmIdTitelPrijs.id)) {
            mandje.push(filmIdTitelPrijs);
            sessionStorage.setItem("filmInMandje", JSON.stringify(mandje));
            window.location ="mandje.html";
        } else {
            toon("mededeling");
        }
        //window.location = "mandje.html";
    }
}
function mandjeArray() {
    const opgeslagen = sessionStorage.getItem("filmInMandje");
    return opgeslagen ? JSON.parse(opgeslagen) : [];
}