package com.motorepuestos.melos.controller;
import com.motorepuestos.melos.data.model.CitaDTO;
import com.motorepuestos.melos.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @PostMapping
    public CitaDTO createCita(@RequestBody CitaDTO citaDTO) {
        return citaService.createCita(citaDTO);
    }

    @PutMapping("/{id}")
    public CitaDTO updateCita(@PathVariable Long id, @RequestBody CitaDTO citaDTO) {
        return citaService.updateCita(id, citaDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCita(@PathVariable Long id) {
        citaService.deleteCita(id);
    }

    @GetMapping("/{id}")
    public CitaDTO getCitaById(@PathVariable Long id) {
        return citaService.getCitaById(id);
    }

    @GetMapping
    public List<CitaDTO> getAllCitas() {
        return citaService.getAllCitas();
    }
}
