const nombreElemento = document.getElementById('nombre');
const nombrecompleto = nombreElemento.textContent.trim();

console.log("El nombre Elemento: " + nombreElemento);
console.log("El nombre completo: " + nombrecompleto);

// Asignar el valor obtenido del campo de texto del nombre de usuario de la base de datos al elemento h1.
nombreElemento.textContent = nombrecompleto;

const nombre = document.getElementById('nombre');
console.log(nombre);
const editar = document.getElementById('editar');
const guardar = document.getElementById('guardar');

editar.addEventListener('click', function() {
  nombre.contentEditable = true;
  nombre.focus();
  nombre.style.borderBottom = '1px solid black';

  // Mostrar el botón "Guardar".
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

  // Obtener el nuevo valor del campo de texto del nombre de usuario.
  var nuevoNombre = nombre.innerText.trim();

  const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');

  // Enviar ese valor al servidor usando una solicitud POST a través de la URL "/actualizarNombre".
  fetch("/actualizarNombre", {
    method: "POST",
    body: JSON.stringify({ nombre: nuevoNombre }),
    headers: {
      "Content-Type": "application/json",
      "X-CSRF-Token": csrfToken
    }
  }).then(function(response) {
    if (response.ok) {
      // Actualizar la página después de recibir la respuesta del servidor.
      window.location.reload();
    } else {
      console.error("Error al actualizar el nombre de usuario.");
    }
  });

  // Ocultar el botón "Guardar".
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
    }).then(response => {
        if (response.ok) {
            return response.blob();
        }

        console.log('Error:', response.status, response.statusText);
        throw new Error('Error al actualizar la foto de perfil');
    }).then(imageBlob => {
        let reader = new FileReader();
        reader.onload = function() {
            let base64Image = reader.result;
            document.querySelector('.imagen-seleccionada').src = base64Image;
        }
        reader.readAsDataURL(imageBlob);
    }).catch(error => {
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

// Obtenemos los elementos de los contenidos que queremos mostrar u ocultar
var contenido1 = document.getElementsByClassName("clase16")[0];
var contenido2 = document.getElementsByClassName("clase17")[0];

// Agregamos un evento click a cada opción
opcion1.addEventListener("click", function() {
  // Mostramos el contenido de la opcion1 y ocultamos el contenido de la opcion2
  contenido1.style.display = "flex";
  contenido2.style.display = "none";
});

opcion2.addEventListener("click", function() {
  // Mostramos el contenido de la opcion2 y ocultamos el contenido de la opcion1
  contenido2.style.display = "flex";
  contenido1.style.display = "none";
});