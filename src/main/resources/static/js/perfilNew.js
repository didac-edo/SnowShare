const nombreElemento = document.getElementById('nombre');
const nombrecompleto = nombreElemento.textContent.trim();

console.log("El nombre Elemento: " + nombreElemento);
console.log("El nombre completo: " + nombrecompleto);

nombreElemento.textContent = nombrecompleto;

const nombre = document.getElementById('nombre');
console.log(nombre);
const editar = document.getElementById('editar');
const guardar = document.getElementById('guardar');

editar.addEventListener('click', function() {
  nombre.contentEditable = true;
  nombre.focus();
  nombre.style.borderBottom = '1px solid black';

  guardar.style.display = 'inline-block';
});

nombre.addEventListener('blur', function() {
  nombre.contentEditable = false;
  nombre.style.borderBottom = 'none';
});

nombre.addEventListener('keydown', function(e) {
  if (e.keyCode === 13) {
    nombre.contentEditable = false;
    nombre.style.borderBottom = 'none';
  }
});

document.querySelector(".clase5").addEventListener("submit", function(event) {
  event.preventDefault();

  var nuevoNombre = nombre.innerText.trim();

  const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');

  fetch("/actualizarNombre", {
    method: "POST",
    body: JSON.stringify({ nombre: nuevoNombre }),
    headers: {
      "Content-Type": "application/json",
      "X-CSRF-Token": csrfToken
    }
  }).then(function(response) {
    if (response.ok) {
      window.location.reload();
    } else {
      console.error("Error al actualizar el nombre de usuario.");
    }
  });

  guardar.style.display = 'none';
});

function seleccionarImagen() {
    document.getElementById('selector-imagenes').click();
}

function cargarImagen(event) {
    let inputFile = event.target;
    let formData = new FormData();
    formData.append('fotoPerfil', inputFile.files[0]);

    const csrfToken1 = document.querySelector('meta[name="csrf-token"]').getAttribute('content');

    fetch('/cambiarFotoPerfil', {
        method: 'POST',
        body: formData,
        headers: {
            "X-CSRF-Token": csrfToken1
        }
    })
    .then(response => {
        if (response.ok) {
            return response.blob();
        } else {
            throw new Error(response.status + ' ' + response.statusText);
        }
    })
    .then(imageBlob => {
        let reader = new FileReader();
        reader.onload = function() {
            let base64Image = reader.result;
            document.querySelector('.imagen-seleccionada').src = base64Image;
        };
        reader.readAsDataURL(imageBlob);
    })
    .catch(error => {
        console.error('Error al cargar la imagen:', error);
    });
}

// Obtener los elementos del DOM que necesitamos
const opcion1 = document.getElementById('opcion1');
const opcion2 = document.getElementById('opcion2');

// Agregar un event listener para cuando se haga clic en opcion1
opcion1.addEventListener('click', () => {
    opcion2.classList.remove('clase10');
    opcion2.classList.add('clase13');
    opcion1.classList.remove('clase13');
    opcion1.classList.add('clase10');

});


// Agregar un event listener para cuando se haga clic en opcion2
opcion2.addEventListener('click', () => {
    opcion1.classList.remove('clase10');
    opcion1.classList.add('clase13');
    opcion2.classList.remove('clase13');
    opcion2.classList.add('clase10');

});

const opcion3 = document.getElementById('opcion3');
const opcion4 = document.getElementById('opcion4');

// Agregar un event listener para cuando se haga clic en opcion1
opcion3.addEventListener('click', () => {
opcion4.classList.remove('clase12');
opcion4.classList.add('clase14');
opcion3.classList.remove('clase14');
opcion3.classList.add('clase12');

});

opcion4.addEventListener('click', () => {
opcion3.classList.remove('clase12');
opcion3.classList.add('clase14');
opcion4.classList.remove('clase14');
opcion4.classList.add('clase12');
});

var contenido1 = document.getElementsByClassName("clase16");
var contenido2 = document.getElementsByClassName("clase17");

opcion1.addEventListener("click", function() {
  for (var i = 0; i < contenido1.length; i++) {
    contenido1[i].style.display = "flex";
  }
  for (var i = 0; i < contenido2.length; i++) {
    contenido2[i].style.display = "none";
  }
});

opcion2.addEventListener("click", function() {
  for (var i = 0; i < contenido2.length; i++) {
    contenido2[i].style.display = "flex";
  }
  for (var i = 0; i < contenido1.length; i++) {
    contenido1[i].style.display = "none";
  }
});