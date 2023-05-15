var mostrarMenu = false;

function toggleMenu() {
  mostrarMenu = !mostrarMenu;
  var menuDesplegable = document.getElementById("menuDesplegable");
  if (mostrarMenu) {
    menuDesplegable.classList.add("menu-desplegado");
  } else {
    menuDesplegable.classList.remove("menu-desplegado");
  }
}

function redirectToArticle(id) {
    window.location.href = "/listado-articulos/" + id;
}

let map;
let geocoder;

function initMap() {
    const defaultCoords = [40.416775, -3.703790]; // Coordenadas por defecto (Madrid)
    map = L.map('map').setView(defaultCoords, 15);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    const codigoPostalElement = document.getElementById('codigoPostal');
    const codigoPostal = codigoPostalElement.textContent || codigoPostalElement.innerText;
    geocodeAddress(codigoPostal);
}


function geocodeAddress(codigoPostal) {
    const geocoder = new L.Control.Geocoder.Nominatim();

    geocoder.geocode(codigoPostal + ', España', (results) => {
        if (results && results.length > 0) {
            const firstResult = results[0];
            const coords = [firstResult.center.lat, firstResult.center.lng];
            L.marker(coords).addTo(map).bindPopup(firstResult.name || firstResult.html || firstResult.label);
            map.setView(coords, 15);
        } else {
            console.error(`La geocodificación no tuvo éxito para el código postal: ${codigoPostal}`);
        }
    });
}



$(document).ready(function() {
    initMap();
});

