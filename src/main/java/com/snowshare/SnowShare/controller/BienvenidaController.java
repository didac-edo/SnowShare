package com.snowshare.SnowShare.controller;

import com.snowshare.SnowShare.config.IntPair;
import com.snowshare.SnowShare.models.Articulo;
import com.snowshare.SnowShare.models.ChatInfo;
import com.snowshare.SnowShare.models.Conversacion;
import com.snowshare.SnowShare.models.Usuario;
import com.snowshare.SnowShare.repository.ConversacionRepository;
import com.snowshare.SnowShare.repository.UsuarioRepository;
import com.snowshare.SnowShare.service.ConversacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@Controller
public class BienvenidaController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ConversacionService conversacionService;

    @GetMapping("/bienvenida")
    public String mostrarBienvenida(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correoElectronico = auth.getName();

        Usuario usuario = usuarioRepository.findByCorreoElectronico(correoElectronico);
        String nombreUsuario = usuario.getNombre();
        byte[] imagenBytes = usuario.getFotoPerfil();
        String imagenBase64 = Base64.getEncoder().encodeToString(imagenBytes);


        List<ChatInfo> chatsPropietario = conversacionService.findAllChatsByPropietarioId(usuario.getIdUsuario());
        List<ChatInfo> chatsIniciadosPorUsuario = conversacionService.findAllChatsIniciadosPorUsuarioId(usuario.getIdUsuario());

        Set<ChatInfo> chatsUnicos = new HashSet<>();
        chatsUnicos.addAll(chatsPropietario);
        chatsUnicos.addAll(chatsIniciadosPorUsuario);

        //Map<Usuario, Articulo> usuariosYArticulosConChats = conversacionService.findUniqueUsuariosAndArticulosByPropietarioId(usuario.getIdUsuario());
        Map<IntPair, ChatInfo> chatsAgrupados = new HashMap<>();
        for (ChatInfo chatInfo : chatsUnicos) {
            IntPair clave = new IntPair(chatInfo.getArticulo().getIdArticulo(), chatInfo.getUsuario().getIdUsuario());
            chatsAgrupados.putIfAbsent(clave, chatInfo);
        }

        model.addAttribute("nombreUsuario", nombreUsuario);
        model.addAttribute("rutaImagenPerfil", imagenBase64);
        //model.addAttribute("usuariosYArticulosConChats", usuariosYArticulosConChats);
        model.addAttribute("chats", chatsAgrupados.values());

        return "bienvenida";
    }
}