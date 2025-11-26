package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ProductService {

    private final Map<String, Map<String, Object>> productDatabase = new HashMap<>();
    private int nextId = 1;

    public ProductService() {
        // Inicializa com alguns produtos dummy
        initializeDummyProducts();
    }

    private void initializeDummyProducts() {
        createProduct("Laptop", "Notebook Dell", 3500.00, 10);
        createProduct("Mouse", "Mouse sem fio Logitech", 150.00, 50);
        createProduct("Teclado", "Teclado mecânico RGB", 450.00, 30);
        createProduct("Monitor", "Monitor 24 polegadas", 800.00, 15);
        createProduct("Headset", "Fone de ouvido gamer", 280.00, 25);
    }

    public Map<String, Object> createProduct(String name, String description, Double price, Integer stock) {
        String id = String.valueOf(nextId++);
        Map<String, Object> product = new HashMap<>();
        product.put("id", id);
        product.put("name", name);
        product.put("description", description);
        product.put("price", price);
        product.put("stock", stock);
        product.put("active", true);
        product.put("createdAt", getCurrentTimestamp());
        
        productDatabase.put(id, product);
        return new HashMap<>(product);
    }

    public Map<String, Object> getProductById(String id) {
        Map<String, Object> product = productDatabase.get(id);
        if (product != null) {
            return new HashMap<>(product);
        }
        return null;
    }

    public List<Map<String, Object>> getAllProducts() {
        return new ArrayList<>(productDatabase.values());
    }

    public List<Map<String, Object>> getActiveProducts() {
        List<Map<String, Object>> activeProducts = new ArrayList<>();
        for (Map<String, Object> product : productDatabase.values()) {
            if (Boolean.TRUE.equals(product.get("active"))) {
                activeProducts.add(new HashMap<>(product));
            }
        }
        return activeProducts;
    }

    public Map<String, Object> updateProduct(String id, String name, String description, Double price, Integer stock) {
        Map<String, Object> product = productDatabase.get(id);
        if (product != null) {
            if (name != null) product.put("name", name);
            if (description != null) product.put("description", description);
            if (price != null) product.put("price", price);
            if (stock != null) product.put("stock", stock);
            product.put("updatedAt", getCurrentTimestamp());
            return new HashMap<>(product);
        }
        return null;
    }

    public boolean deleteProduct(String id) {
        return productDatabase.remove(id) != null;
    }

    public Map<String, Object> updateStock(String id, Integer quantity) {
        Map<String, Object> product = productDatabase.get(id);
        if (product != null) {
            Integer currentStock = (Integer) product.get("stock");
            product.put("stock", currentStock + quantity);
            product.put("updatedAt", getCurrentTimestamp());
            return new HashMap<>(product);
        }
        return null;
    }

    public boolean deactivateProduct(String id) {
        Map<String, Object> product = productDatabase.get(id);
        if (product != null) {
            product.put("active", false);
            product.put("updatedAt", getCurrentTimestamp());
            return true;
        }
        return false;
    }

    public Map<String, Object> getProductStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalProducts", productDatabase.size());
        stats.put("activeProducts", getActiveProducts().size());
        
        double totalValue = 0;
        int totalStock = 0;
        for (Map<String, Object> product : productDatabase.values()) {
            if (Boolean.TRUE.equals(product.get("active"))) {
                Double price = (Double) product.get("price");
                Integer stock = (Integer) product.get("stock");
                totalValue += price * stock;
                totalStock += stock;
            }
        }
        
        stats.put("totalStockValue", totalValue);
        stats.put("totalItemsInStock", totalStock);
        stats.put("timestamp", getCurrentTimestamp());
        
        return stats;
    }

    public List<Map<String, Object>> searchProducts(String keyword) {
        List<Map<String, Object>> results = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();
        
        for (Map<String, Object> product : productDatabase.values()) {
            String name = ((String) product.get("name")).toLowerCase();
            String description = ((String) product.get("description")).toLowerCase();
            
            if (name.contains(lowerKeyword) || description.contains(lowerKeyword)) {
                results.add(new HashMap<>(product));
            }
        }
        
        return results;
    }

    private String getCurrentTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public long getProductCount() {
        return productDatabase.size();
    }
}
