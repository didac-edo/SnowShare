package com.snowshare.SnowShare.controller;

import com.snowshare.SnowShare.dto.ConversacionDTO;
import com.snowshare.SnowShare.models.Articulo;
import com.snowshare.SnowShare.models.Conversacion;
import com.snowshare.SnowShare.models.Usuario;
import com.snowshare.SnowShare.repository.ArticuloRepository;
import com.snowshare.SnowShare.repository.ConversacionRepository;
import com.snowshare.SnowShare.repository.UsuarioRepository;
import com.snowshare.SnowShare.service.ConversacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.messaging.handler.annotation.Header;

@Controller
public class ChatController {

    //ara s'ha de fer que si s'ha creat un nou chat, que es vegi un nou chat creat al propeitari del articulo
    @Autowired
    private ConversacionRepository conversacionRepository;
    @Autowired
    private ArticuloRepository articuloRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ConversacionService conversacionService;

    @MessageMapping("/chat/{idArticulo}/sendMessage")
    @SendTo("/topic/chat/{idArticulo}")
    public ConversacionDTO sendMessage(@DestinationVariable Integer idArticulo, @Payload Conversacion chatMessage, @Header("usuarioId") Integer currentUserId) {

        Articulo articulo = articuloRepository.findById(idArticulo).orElse(null);
        chatMessage.setArticulo(articulo);

        //String correoUsuarioActual = chatMessage.getUsuario().getCorreoElectronico();
        Usuario usuarioActual = usuarioRepository.getById(currentUserId);
        System.out.println("El usuario actual es: " + usuarioActual.getIdUsuario());
        //Usuario usuarioActual = usuarioRepository.findByCorreoElectronico(correoUsuarioActual);
        chatMessage.setUsuario(usuarioActual);

        Usuario propietario = null;
        if (articulo != null) {
            propietario = articulo.getPropietario();
            chatMessage.setPropietario(propietario);
            System.out.println("idPropietario: " + propietario.getIdUsuario());
        } else {
            System.out.println("idPropietario: is null");
        }

        System.out.println("idArticulo: " + idArticulo);
        System.out.println("idUsuarioActual: " + usuarioActual.getIdUsuario());

        System.out.println("chatMessage: " + chatMessage);

        ConversacionDTO chatMessageDto = null;

        try {
            Conversacion savedMessage = conversacionRepository.save(chatMessage);
            System.out.println("Mensaje guardado: " + savedMessage);

            chatMessageDto = conversacionService.convertToDto(savedMessage);
        } catch (Exception e) {
            System.out.println("Error al guardar el mensaje:");
            e.printStackTrace();
        }

        // Devuelve el mensaje al cliente a través del canal de WebSocket
        //Conversacion savedConversacion = conversacionRepository.save(chatMessage);
        return chatMessageDto;
    }

    @GetMapping("/chat/{idArticulo}")
    public String chat(@PathVariable Integer idArticulo, Model model) {
        // Obtén el artículo y la información de los usuarios del chat
        Articulo articulo = articuloRepository.findById(idArticulo).orElse(null);

        if (articulo == null) {
            model.addAttribute("error", "Artículo no encontrado");
            return "error"; // reemplaza "error" con el nombre de tu página de error personalizada
        }

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = userDetails.getUsername();
        Usuario usuarioActual = usuarioRepository.findByCorreoElectronico(userEmail);

        Usuario propietario = articulo.getPropietario();

        // Añade los atributos al modelo que necesitas en la vista
        model.addAttribute("articulo", articulo);
        model.addAttribute("usuario", usuarioActual);
        model.addAttribute("propietario", propietario);

        return "chat";
    }

}
