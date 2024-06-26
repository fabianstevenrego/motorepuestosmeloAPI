package com.motorepuestos.melos.controller;

import com.motorepuestos.melos.data.model.MarcaDTO;
import com.motorepuestos.melos.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public List<MarcaDTO> getAllMarcas() {
        return marcaService.getAllMarcas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaDTO> getMarcaById(@PathVariable Long id) {
        MarcaDTO marcaDTO = marcaService.getMarcaById(id);
        return ResponseEntity.ok(marcaDTO);
    }

    @PostMapping
    public ResponseEntity<MarcaDTO> createMarca(@RequestBody MarcaDTO marcaDTO) {
        MarcaDTO newMarca = marcaService.createMarca(marcaDTO);
        return ResponseEntity.ok(newMarca);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarcaDTO> updateMarca(@PathVariable Long id, @RequestBody MarcaDTO marcaDTO) {
        MarcaDTO updatedMarca = marcaService.updateMarca(id, marcaDTO);
        return ResponseEntity.ok(updatedMarca);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarca(@PathVariable Long id) {
        marcaService.deleteMarca(id);
        return ResponseEntity.noContent().build();
    }
}
