package com.github.joao.disaster_response.api.controller;


import com.github.joao.disaster_response.domain.model.Ocorrencia;
import com.github.joao.disaster_response.domain.model.enums.StatusOcorrencia;
import com.github.joao.disaster_response.domain.repository.OcorrenciaRepository;
import com.github.joao.disaster_response.domain.service.OcorrenciaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ocorrencias")
public class OcorrenciaController {

    private final OcorrenciaRepository repository;

    private final OcorrenciaService service;

    public OcorrenciaController(OcorrenciaRepository repository, OcorrenciaService service) {
        this.repository = repository;
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<Ocorrencia> criar(@Valid @RequestBody Ocorrencia ocorrencia){
        Ocorrencia salvo = service.criar(ocorrencia);
        return ResponseEntity.status(201).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ocorrencia> buscarPorId(@PathVariable Long id){
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Ocorrencia>> listar(){
        List<Ocorrencia> ocorrencias = repository.findAll();
        return ResponseEntity.ok(ocorrencias);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Ocorrencia> atualizarStatus(@PathVariable Long id, @RequestBody StatusOcorrencia novoStatus){
        Ocorrencia atualizada = service.atualizarStatus(id, novoStatus);

        return ResponseEntity.ok(atualizada);
    }

}
