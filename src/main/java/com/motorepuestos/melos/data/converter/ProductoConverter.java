package com.motorepuestos.melos.data.converter;

import com.motorepuestos.melos.data.entity.Categoria;
import com.motorepuestos.melos.data.entity.Producto;
import com.motorepuestos.melos.data.model.ProductoDTO;
import com.motorepuestos.melos.service.implementation.CategoriaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductoConverter {

    @Autowired
    private CategoriaServiceImpl categoriaService;

    public ProductoDTO entityToDto(Producto producto) {
        if (producto == null) {
            return null;
        }

        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setCodigo(producto.getCodigo());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecioCosto(producto.getPrecioCosto());
        dto.setPrecioVenta(producto.getPrecioVenta());
        dto.setCategoriaId(producto.getCategoria().getId()); // Cambiado a producto.getCategoria().getId()
        dto.setMarcaId(producto.getMarcaId());
        dto.setTipoId(producto.getTipoId());
        dto.setStock(producto.getStock());
        return dto;
    }

    public Producto dtoToEntity(ProductoDTO dto) {
        if (dto == null) {
            return null;
        }

        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setCodigo(dto.getCodigo());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecioCosto(dto.getPrecioCosto());
        producto.setPrecioVenta(dto.getPrecioVenta());

        Categoria categoria = categoriaService.findById(dto.getCategoriaId());
        producto.setCategoria(categoria); // Asignar la entidad Categoria en lugar de solo el ID

        producto.setMarcaId(dto.getMarcaId());
        producto.setTipoId(dto.getTipoId());
        producto.setStock(dto.getStock());
        return producto;
    }
}
