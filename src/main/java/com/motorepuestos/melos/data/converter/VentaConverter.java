package com.motorepuestos.melos.data.converter;

import com.motorepuestos.melos.data.entity.ProductoVenta;
import com.motorepuestos.melos.data.entity.Venta;
import com.motorepuestos.melos.data.model.VentaDTO;
import com.motorepuestos.melos.data.model.ProductoVentaModel;
import com.motorepuestos.melos.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class VentaConverter {

    @Autowired
    private ProductoRepository productoRepository;

    public Venta modelToEntity(VentaDTO ventaDTO) {
        Venta venta = new Venta();
        venta.setFecha(ventaDTO.getFecha());
        venta.setTipoVenta(ventaDTO.getTipoVenta());
        venta.setClienteId(ventaDTO.getClienteId());
        venta.setTotalVenta(ventaDTO.getTotalVenta());
        venta.setProductosVenta(ventaDTO.getProductosVenta().stream()
                .map(productoVentaModel -> {
                    ProductoVenta productoVenta = new ProductoVenta();
                    productoVenta.setProducto(productoRepository.findById(productoVentaModel.getProductoId()).orElse(null));
                    productoVenta.setCantidad(productoVentaModel.getCantidad());
                    productoVenta.setPrecioVenta(productoRepository.findById(productoVentaModel.getProductoId()).orElse(null).getPrecioVenta());
                    productoVenta.setVenta(venta);
                    return productoVenta;
                }).collect(Collectors.toList()));
        return venta;
    }

    public VentaDTO entityToModel(Venta entity) {
        VentaDTO ventaDTO = new VentaDTO();
        ventaDTO.setId(entity.getId());
        ventaDTO.setFecha(entity.getFecha());
        ventaDTO.setTipoVenta(entity.getTipoVenta());
        ventaDTO.setClienteId(entity.getClienteId());
        ventaDTO.setTotalVenta(entity.getTotalVenta());
        ventaDTO.setProductosVenta(entity.getProductosVenta().stream()
                .map(productoVenta -> {
                    ProductoVentaModel productoVentaModel = new ProductoVentaModel();
                    productoVentaModel.setProductoId(productoVenta.getProducto().getId());
                    productoVentaModel.setCantidad(productoVenta.getCantidad());
                    return productoVentaModel;
                }).collect(Collectors.toList()));
        return ventaDTO;
    }
}