package com.motorepuestos.melos.service.implementation;

import com.motorepuestos.melos.data.converter.TipoConverter;
import com.motorepuestos.melos.data.entity.Categoria;
import com.motorepuestos.melos.data.entity.Tipo;
import com.motorepuestos.melos.data.model.TipoDTO;
import com.motorepuestos.melos.repository.TipoRepository;
import com.motorepuestos.melos.service.TipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoServiceImpl implements TipoService {

    @Autowired
    private TipoRepository tipoRepository;

    @Autowired
    private TipoConverter tipoConverter;

    @Override
    public List<TipoDTO> getAllTipos() {
        return tipoRepository.findAll().stream()
                .map(tipoConverter::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TipoDTO getTipoById(Long id) {
        Tipo tipo = tipoRepository.findById(id).orElseThrow(() -> new RuntimeException("Tipo not found"));
        return tipoConverter.entityToDto(tipo);
    }

    @Override
    public TipoDTO createTipo(TipoDTO tipoDTO) {
        Tipo tipo = tipoConverter.dtoToEntity(tipoDTO);
        tipo = tipoRepository.save(tipo);
        return tipoConverter.entityToDto(tipo);
    }

    @Override
    public TipoDTO updateTipo(Long id, TipoDTO tipoDTO) {
        Tipo tipo = tipoRepository.findById(id).orElseThrow(() -> new RuntimeException("Tipo not found"));
        tipo.setNombre(tipoDTO.getNombre());
        tipo = tipoRepository.save(tipo);
        return tipoConverter.entityToDto(tipo);
    }

    @Override
    public void deleteTipo(Long id) {
        tipoRepository.deleteById(id);
    }

    @Override
    public Tipo findById(Long id) {
        return tipoRepository.findById(id).orElseThrow(() -> new RuntimeException("Tipo not found"));
    }


}
