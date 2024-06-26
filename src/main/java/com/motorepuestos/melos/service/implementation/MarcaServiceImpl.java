package com.motorepuestos.melos.service.implementation;

import com.motorepuestos.melos.data.converter.MarcaConverter;
import com.motorepuestos.melos.data.entity.Marca;
import com.motorepuestos.melos.data.entity.Tipo;
import com.motorepuestos.melos.data.model.MarcaDTO;
import com.motorepuestos.melos.repository.MarcaRepository;
import com.motorepuestos.melos.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarcaServiceImpl implements MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    @Autowired
    private MarcaConverter marcaConverter;

    @Override
    public List<MarcaDTO> getAllMarcas() {
        return marcaRepository.findAll().stream()
                .map(marcaConverter::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public MarcaDTO getMarcaById(Long id) {
        Marca marca = marcaRepository.findById(id).orElseThrow(() -> new RuntimeException("Marca not found"));
        return marcaConverter.entityToDto(marca);
    }

    @Override
    public MarcaDTO createMarca(MarcaDTO marcaDTO) {
        Marca marca = marcaConverter.dtoToEntity(marcaDTO);
        marca = marcaRepository.save(marca);
        return marcaConverter.entityToDto(marca);
    }

    @Override
    public MarcaDTO updateMarca(Long id, MarcaDTO marcaDTO) {
        Marca marca = marcaRepository.findById(id).orElseThrow(() -> new RuntimeException("Marca not found"));
        marca.setNombre(marcaDTO.getNombre());
        marca = marcaRepository.save(marca);
        return marcaConverter.entityToDto(marca);
    }

    @Override
    public void deleteMarca(Long id) {
        marcaRepository.deleteById(id);
    }

    @Override
    public Marca findById(Long id) {
        return marcaRepository.findById(id).orElseThrow(() -> new RuntimeException("Marca not found"));
    }


}