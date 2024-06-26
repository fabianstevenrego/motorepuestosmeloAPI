package com.motorepuestos.melos.controller;

import com.motorepuestos.melos.data.model.TipoDTO;
import com.motorepuestos.melos.service.TipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://127.0.0.1:5502")
@RestController
@RequestMapping("/api/tipos")
public class TipoController {

    @Autowired
    private TipoService tipoService;

    @GetMapping
    public List<TipoDTO> getAllTipos() {
        return tipoService.getAllTipos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoDTO> getTipoById(@PathVariable Long id) {
        TipoDTO tipoDTO = tipoService.getTipoById(id);
        return ResponseEntity.ok(tipoDTO);
    }

    @PostMapping
    public ResponseEntity<TipoDTO> createTipo(@RequestBody TipoDTO tipoDTO) {
        TipoDTO newTipo = tipoService.createTipo(tipoDTO);
        return ResponseEntity.ok(newTipo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoDTO> updateTipo(@PathVariable Long id, @RequestBody TipoDTO tipoDTO) {
        TipoDTO updatedTipo = tipoService.updateTipo(id, tipoDTO);
        return ResponseEntity.ok(updatedTipo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTipo(@PathVariable Long id) {
        tipoService.deleteTipo(id);
        return ResponseEntity.noContent().build();
    }
}