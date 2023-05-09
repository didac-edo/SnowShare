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


const nombreElemento = document.getElementById('nombre');
const nombrecompleto = "Enrique Garcia Ortiz"/* obtener el valor del campo nombre del usuario de la base de datos*/;
// Actualizar el contenido del elemento h1 con los valores obtenidos
nombreElemento.textContent = nombrecompleto;

const nombre = document.getElementById('nombre');
const editar = document.getElementById('editar');

editar.addEventListener('click', function() {
  nombre.contentEditable = true;
  nombre.focus();
  nombre.style.borderBottom = '1px solid black';
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


function cambiarFotoPerfil() {
  var input = document.createElement('input');
  input.type = 'file';
  input.accept = 'image/*';
  input.onchange = function() {
      var file = this.files[0];
      var reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = function() {
          var img = document.querySelector('.clase4');
          img.src = reader.result;
      };
  };
  input.click();
}
