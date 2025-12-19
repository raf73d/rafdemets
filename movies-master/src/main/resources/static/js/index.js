"use strict";
import {byId, toon, setText, verberg} from "./util.js";
//als het niet in sessionStorage zit, haal het uit de database
//genres worden gedelete na een reservatiebevestiging
async function laadgenres() {
    const opgeslagenGenres = sessionStorage.getItem("genres");
    let genres;
    if (opgeslagenGenres) {
        genres = JSON.parse(opgeslagenGenres);
    }
    if (!genres) {
        const genreResponse = await fetch("genres");
        if (genreResponse.ok) {
            genres = await genreResponse.json();
            sessionStorage.setItem("genres", JSON.stringify(genres));
        } else {
            toon("storing");
        }
    }
    return genres;
}
if (document.getElementById("genreFilmBody").innerHTML =="") {
    toon("mededeling");

}
let genres;
genres = await laadgenres();
const genreBody = byId("genreBody");
genreBody.innerHTML = "";
const tr = genreBody.insertRow();
for (const genre of genres) {
    const td = tr.insertCell();
    const hyperlink = document.createElement("a");
    hyperlink.href = "#";
    hyperlink.textContent = genre.naam;
    hyperlink.onclick = () => {
        toon("genreTitel");
        setText("genreTitel", genre.naam);
        maakGenreGalerij(genre.id);
        verberg("mededeling");
    }
    td.appendChild(hyperlink);

}

//filmfoto's uitstallen in rij van tabel
async function maakGenreGalerij(genreId) {
    const filmsZelfdeGenreResponse = await fetch(`films?genreId=${genreId}`);
    if (filmsZelfdeGenreResponse.ok) {
        const films = await filmsZelfdeGenreResponse.json();
        const genreFilmBody = byId("genreFilmBody");
        genreFilmBody.innerHTML = "";
        const tr = genreFilmBody.insertRow();
        for (const film of films) {
            const td = tr.insertCell();
            const hyperlink = document.createElement("a");
            hyperlink.href = "filmpagina.html";
            const img = document.createElement("img");
            img.src = `images/${film.id}.jpg`;
            img.alt = `${film.titel}`;
            img.style.width = "150px";
            img.style.height = "auto";

            hyperlink.onclick = () => {
                sessionStorage.setItem("filmIdTitel", JSON.stringify(film));
            }
            hyperlink.appendChild(img);
            td.appendChild(hyperlink);
        }
    } else {
        if (filmsZelfdeGenreResponse.status === 404) {
            const responseBody = await filmsZelfdeGenreResponse.json();
            setText("nietGevonden", responseBody);
            toon("nietGevonden")
        } else
            toon("storing");

    }
}