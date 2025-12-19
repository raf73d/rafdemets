"use strict";
import {byId, toon, verberg} from "./util.js";

byId("zoek").onclick = async () => {
    verberg("woordFout", "storing", "conflict", "badRequest", "mededeling");
    const woordInput = byId("woord");
    if (!woordInput.checkValidity()) {
        toon("woordFout");
    }
    const aantalFouten = document.querySelectorAll(".fout:not([hidden])").length;
    if (aantalFouten === 0) {
        zoek(woordInput.value);
    }
}

async function zoek(woord) {
    const klantResponse = await fetch(`klanten?woord=${woord}`);
    if (klantResponse.ok) {
        const klantenBody = byId("klantenBody");
        klantenBody.innerHTML = "";
        const klanten = await klantResponse.json();
        if (klanten.length > 0) {
            verberg("mededeling");
            toon("tabelHoofd");
            for (const klant of klanten) {
                const tr = klantenBody.insertRow();
                const td = tr.insertCell();
                const hyperlink = document.createElement("a");
                hyperlink.href = "bevestigpagina.html"
                hyperlink.textContent = klant.familienaam + " " + klant.voornaam;
                hyperlink.onclick = () => {
                    const klantInformatie = {
                        id: klant.id,
                        voornaam: klant.voornaam,
                        familienaam: klant.familienaam
                    }
                    sessionStorage.setItem("klant", JSON.stringify(klantInformatie));
                }
                td.appendChild(hyperlink);
                tr.insertCell().textContent = klant.straatNummer;
                tr.insertCell().textContent = klant.postcode;
                tr.insertCell().textContent = klant.gemeente;
            }
        } else {
            verberg("tabelHoofd");
            toon("mededeling")
        }
    }
}
