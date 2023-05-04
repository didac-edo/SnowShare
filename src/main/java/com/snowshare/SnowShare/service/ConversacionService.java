package com.snowshare.SnowShare.service;

import com.snowshare.SnowShare.dto.ConversacionDTO;
import com.snowshare.SnowShare.models.Conversacion;
import com.snowshare.SnowShare.repository.ConversacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

}
