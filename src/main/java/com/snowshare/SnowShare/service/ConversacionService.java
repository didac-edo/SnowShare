package com.snowshare.SnowShare.service;

import com.snowshare.SnowShare.dto.ConversacionDTO;
import com.snowshare.SnowShare.models.Articulo;
import com.snowshare.SnowShare.models.ChatInfo;
import com.snowshare.SnowShare.models.Conversacion;
import com.snowshare.SnowShare.models.Usuario;
import com.snowshare.SnowShare.repository.ConversacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConversacionService {

    @Autowired
    private ConversacionRepository conversacionRepository;

    public ConversacionDTO convertToDto(Conversacion conversacion) {
        ConversacionDTO conversacionDto = new ConversacionDTO();
        conversacionDto.setId(conversacion.getIdConversacion());
        conversacionDto.setMensaje(conversacion.getMensaje());
        conversacionDto.setFechaHora(conversacion.getFechaHora());
        conversacionDto.setId_articulo(conversacion.getArticulo().getIdArticulo());
        conversacionDto.setId_propietario(conversacion.getPropietario().getIdUsuario());
        conversacionDto.setId_usuario(conversacion.getUsuario().getIdUsuario());
        return conversacionDto;
    }

    public List<ConversacionDTO> findAllByArticuloId(Integer articuloId) {
        List<Conversacion> conversaciones = conversacionRepository.findAllByArticuloId(articuloId);
        return conversaciones.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Map<Usuario, Articulo> findUniqueUsuariosAndArticulosByPropietarioId(Integer propietarioId) {
        List<Conversacion> conversaciones = conversacionRepository.findAllByPropietarioIdUsuario(propietarioId);
        Map<Usuario, Articulo> usuariosYArticulosConChats = new LinkedHashMap<>();

        for (Conversacion conversacion : conversaciones) {
            usuariosYArticulosConChats.put(conversacion.getUsuario(), conversacion.getArticulo());
        }

        return usuariosYArticulosConChats;
    }

    public List<Conversacion> findAllByUsuarioId(Integer usuarioId) {
        return conversacionRepository.findAllByUsuarioIdUsuario(usuarioId);
    }

    public List<ChatInfo> findAllChatsByPropietarioId(Integer propietarioId) {
        List<Conversacion> conversaciones = conversacionRepository.findAllByPropietarioIdUsuario(propietarioId);
        Set<ChatInfo> chats = new HashSet<>();

        for (Conversacion conversacion : conversaciones) {
            ChatInfo chatInfo = new ChatInfo(conversacion.getUsuario(), conversacion.getArticulo());
            chats.add(chatInfo);
        }

        return new ArrayList<>(chats);
    }

    public List<ChatInfo> findAllChatsIniciadosPorUsuarioId(Integer usuarioId) {
        List<Conversacion> conversaciones = conversacionRepository.findAllByUsuarioIdUsuario(usuarioId);
        Set<ChatInfo> chats = new HashSet<>();

        for (Conversacion conversacion : conversaciones) {
            ChatInfo chatInfo = new ChatInfo(conversacion.getPropietario(), conversacion.getArticulo());
            chats.add(chatInfo);
        }

        return new ArrayList<>(chats);
    }


}
