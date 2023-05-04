package com.snowshare.SnowShare.controller;

import com.snowshare.SnowShare.dto.ConversacionDTO;
import com.snowshare.SnowShare.service.ConversacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatApiController {
    @Autowired
    private ConversacionService conversacionService;

    @GetMapping("/{idArticulo}/messages")
    public ResponseEntity<List<ConversacionDTO>> getChatMessages(@PathVariable Integer idArticulo) {
        List<ConversacionDTO> messages = conversacionService.findAllByArticuloId(idArticulo);
        return ResponseEntity.ok(messages);
    }
}

