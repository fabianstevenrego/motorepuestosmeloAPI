package com.motorepuestos.melos.service;


import com.motorepuestos.melos.data.model.VentaDTO;

import java.util.List;

public interface VentaService {
    VentaDTO createVenta(VentaDTO ventaDTO);
    List<VentaDTO> getAllVentas();
    VentaDTO getVentaById(Long id);
    VentaDTO updateVenta(Long id, VentaDTO ventaDTO);
    void deleteVenta(Long id);
}
