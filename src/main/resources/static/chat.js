const url = 'http://localhost:8080/chat-websocket';
const socket = new SockJS(url);
const stompClient = Stomp.over(socket);

fetchChatMessages();

stompClient.connect({}, function () {
    stompClient.subscribe('/topic/chat/' + idArticulo, function (message) {
        const chatMessageDto = JSON.parse(message.body);
        // Muestra el mensaje en el chat
        displayChatMessage(chatMessageDto);
    });
});

function displayChatMessage(chatMessageDto) {
    const messageElement = document.createElement('div');
    messageElement.classList.add('chat-message');
    messageElement.textContent = chatMessageDto.mensaje; // Asegúrate de utilizar la propiedad correcta según tu modelo de datos

    const messagesContainer = document.getElementById('messages-container');
    messagesContainer.appendChild(messageElement);
}

async function fetchChatMessages() {
    const response = await fetch(`/api/chat/${idArticulo}/messages`);
    const chatMessages = await response.json();

    for (const chatMessage of chatMessages) {
        displayChatMessage(chatMessage);
    }
}

document.getElementById('chat-form').addEventListener('submit', function (event) {
    event.preventDefault();

    const messageInput = document.getElementById('message-input');
    const messageText = messageInput.value.trim();

    if (messageText) {
        const chatMessage = {
            mensaje: messageText,
            fechaHora: new Date().toISOString(),
            // Agrega la información del usuario y el propietario aquí
            id_articulo: idArticulo,
            id_propietario: idPropietario,
            id_usuario: idUsuario,
        };

        stompClient.send('/app/chat/' + idArticulo + '/sendMessage', {usuarioId: idUsuario}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
});
