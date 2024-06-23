package com.motorepuestos.melos.controller;

import com.motorepuestos.melos.data.model.ProductoDTO;
import com.motorepuestos.melos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://127.0.0.1:5501")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<ProductoDTO> getAllProductos() {
        return productoService.getAllProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable Long id) {
        ProductoDTO productoDTO = productoService.getProductoById(id);
        return productoDTO != null ? ResponseEntity.ok(productoDTO) : ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ProductoDTO createProducto(@RequestBody ProductoDTO productoDTO) {
        return productoService.createProducto(productoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> updateProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        ProductoDTO updatedProductoDTO = productoService.updateProducto(id, productoDTO);
        return updatedProductoDTO != null ? ResponseEntity.ok(updatedProductoDTO) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/stock-bajo/{limit}")
    public List<ProductoDTO> getProductosStockBajo(@PathVariable int limit) {
        return productoService.checkStockBelowLimit(limit);
    }

    @GetMapping("/list/{categoriaId}")
    public List<ProductoDTO> getProductosByCategoria(@PathVariable Long categoriaId) {
        return productoService.getProductosByCategoria(categoriaId);
    }
}
