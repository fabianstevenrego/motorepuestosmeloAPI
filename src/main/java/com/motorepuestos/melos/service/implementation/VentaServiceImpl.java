package com.motorepuestos.melos.service.implementation;

import com.motorepuestos.melos.data.converter.VentaConverter;
import com.motorepuestos.melos.data.entity.Producto;
import com.motorepuestos.melos.data.entity.ProductoVenta;
import com.motorepuestos.melos.data.entity.Venta;
import com.motorepuestos.melos.data.model.VentaDTO;
import com.motorepuestos.melos.repository.VentaRepository;
import com.motorepuestos.melos.service.ProductoService;
import com.motorepuestos.melos.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaServiceImpl implements VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private VentaConverter ventaConverter;

    @Autowired
    private ProductoService productoService;

    @Override
    public VentaDTO createVenta(VentaDTO ventaDTO) {
        Venta venta = ventaConverter.modelToEntity(ventaDTO);
        venta = ventaRepository.save(venta);
        // Actualizar el stock de los productos
        actualizarStockProductos(venta);
        return ventaConverter.entityToModel(venta);
    }

    private void actualizarStockProductos(Venta venta) {
        for (ProductoVenta productoVenta : venta.getProductosVenta()) {
            Long productoId = productoVenta.getProducto().getId();
            int cantidad = productoVenta.getCantidad();
            productoService.actualizarStock(productoId, -cantidad); // Restar cantidad del stock
        }
    }

    @Override
    public List<VentaDTO> getAllVentas() {
        return ventaRepository.findAll().stream()
                .map(ventaConverter::entityToModel)
                .collect(Collectors.toList());
    }

    @Override
    public VentaDTO getVentaById(Long id) {
        return ventaRepository.findById(id)
                .map(ventaConverter::entityToModel)
                .orElse(null);
    }

    @Override
    public VentaDTO updateVenta(Long id, VentaDTO ventaDTO) {
        Venta venta = ventaRepository.findById(id).orElse(null);
        if (venta != null) {
            venta.getProductosVenta().clear();
            venta.getProductosVenta().addAll(ventaConverter.modelToEntity(ventaDTO).getProductosVenta());
            venta.setFecha(ventaDTO.getFecha());
            venta.setTipoVenta(ventaDTO.getTipoVenta());
            venta.setClienteId(ventaDTO.getClienteId());
            venta.setTotalVenta(ventaDTO.getTotalVenta());
            venta = ventaRepository.save(venta);
            return ventaConverter.entityToModel(venta);
        }
        return null;
    }

    @Override
    public void deleteVenta(Long id) {
        ventaRepository.deleteById(id);
    }
}