document.getElementById('botonCrearUsuario').addEventListener('click', function(event) {
    event.preventDefault(); // Detiene la acción predeterminada del botón de envío

    let formulario = document.getElementById('formularioUsuario');
    let nombre = formulario.nombre.value.trim();
    let contrasena = formulario.contrasena.value.trim();
    let correoElectronico = formulario.correoElectronico.value.trim();
    let fotoPerfil = formulario.fotoPerfil.files[0];

    // Validación del nombre (tú puedes ajustar las reglas según tus necesidades)
    if (nombre.length < 3 || nombre.length > 50) {
        alert('El nombre debe tener entre 3 y 50 caracteres.');
        return;
    }

    // Validación de la contraseña (tú puedes ajustar las reglas según tus necesidades)
    if (contrasena.length < 8) {
        alert('La contraseña debe tener al menos 8 caracteres.');
        return;
    }

    // Validación del correo electrónico
    let regexCorreo = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    if (!regexCorreo.test(correoElectronico)) {
        alert('Ingresa un correo electrónico válido.');
        return;
    }

    // Validación del archivo de imagen
    if (!fotoPerfil || !['image/png', 'image/jpeg', 'image/gif'].includes(fotoPerfil.type)) {
        alert('Selecciona una imagen válida (PNG, JPEG o GIF).');
        return;
    }

    // Si todas las validaciones pasan, se envía el formulario
    formulario.submit();
});
