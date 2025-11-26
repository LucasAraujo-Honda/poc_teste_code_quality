package com.example.demo.controller;

import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public Map<String, Object> getAllProducts() {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> products = productService.getAllProducts();
        response.put("products", products);
        response.put("total", products.size());
        return response;
    }

    @GetMapping("/active")
    public Map<String, Object> getActiveProducts() {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> products = productService.getActiveProducts();
        response.put("products", products);
        response.put("total", products.size());
        return response;
    }

    @GetMapping("/{id}")
    public Map<String, Object> getProduct(@PathVariable String id) {
        Map<String, Object> product = productService.getProductById(id);
        if (product != null) {
            return product;
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Product not found");
        response.put("id", id);
        return response;
    }

    @PostMapping
    public Map<String, Object> createProduct(@RequestBody Map<String, Object> productData) {
        String name = (String) productData.get("name");
        String description = (String) productData.get("description");
        Double price = productData.get("price") instanceof Integer 
            ? ((Integer) productData.get("price")).doubleValue() 
            : (Double) productData.get("price");
        Integer stock = (Integer) productData.get("stock");
        
        return productService.createProduct(name, description, price, stock);
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateProduct(@PathVariable String id, @RequestBody Map<String, Object> productData) {
        String name = (String) productData.get("name");
        String description = (String) productData.get("description");
        Double price = productData.get("price") instanceof Integer 
            ? ((Integer) productData.get("price")).doubleValue() 
            : (Double) productData.get("price");
        Integer stock = (Integer) productData.get("stock");
        
        Map<String, Object> updated = productService.updateProduct(id, name, description, price, stock);
        if (updated != null) {
            return updated;
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Product not found");
        response.put("id", id);
        return response;
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteProduct(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        boolean deleted = productService.deleteProduct(id);
        response.put("success", deleted);
        response.put("message", deleted ? "Product deleted successfully" : "Product not found");
        return response;
    }

    @PatchMapping("/{id}/stock")
    public Map<String, Object> updateStock(@PathVariable String id, @RequestBody Map<String, Integer> stockData) {
        Integer quantity = stockData.get("quantity");
        Map<String, Object> updated = productService.updateStock(id, quantity);
        
        if (updated != null) {
            return updated;
        }
        
        Map<String, Object> response = new HashMap<>();
        response.put("error", "Product not found");
        response.put("id", id);
        return response;
    }

    @PatchMapping("/{id}/deactivate")
    public Map<String, Object> deactivateProduct(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        boolean deactivated = productService.deactivateProduct(id);
        response.put("success", deactivated);
        response.put("message", deactivated ? "Product deactivated successfully" : "Product not found");
        return response;
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        return productService.getProductStats();
    }

    @GetMapping("/search")
    public Map<String, Object> searchProducts(@RequestParam String keyword) {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> products = productService.searchProducts(keyword);
        response.put("products", products);
        response.put("total", products.size());
        response.put("keyword", keyword);
        return response;
    }
}
