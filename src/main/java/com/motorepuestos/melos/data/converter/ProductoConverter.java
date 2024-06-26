package com.motorepuestos.melos.data.converter;

import com.motorepuestos.melos.data.entity.Categoria;
import com.motorepuestos.melos.data.entity.Marca;
import com.motorepuestos.melos.data.entity.Producto;
import com.motorepuestos.melos.data.entity.Tipo;
import com.motorepuestos.melos.data.model.ProductoDTO;
import com.motorepuestos.melos.service.CategoriaService;
import com.motorepuestos.melos.service.MarcaService;
import com.motorepuestos.melos.service.TipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductoConverter {

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private TipoService tipoService;

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
        dto.setCategoriaId(producto.getCategoria().getId());
        dto.setMarcaId(producto.getMarca().getId());
        dto.setTipoId(producto.getTipo().getId());
        dto.setStock(producto.getStock());
        dto.setCategoria(producto.getCategoria().getNombre());
        dto.setMarca(producto.getMarca().getNombre());
        dto.setTipo(producto.getTipo().getNombre());
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
        producto.setCategoria(categoria);

        Marca marca = marcaService.findById(dto.getMarcaId());
        producto.setMarca(marca);

        Tipo tipo = tipoService.findById(dto.getTipoId());
        producto.setTipo(tipo);

        producto.setStock(dto.getStock());
        return producto;
    }
}
