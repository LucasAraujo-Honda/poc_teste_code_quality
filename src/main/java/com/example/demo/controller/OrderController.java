package com.example.demo.controller;

import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public Map<String, Object> getAllOrders() {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> orders = orderService.getAllOrders();
        response.put("orders", orders);
        response.put("total", orders.size());
        return response;
    }

    @GetMapping("/{id}")
    public Map<String, Object> getOrder(@PathVariable String id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public Map<String, Object> createOrder(@RequestBody Map<String, Object> orderData) {
        String customerId = (String) orderData.get("customerId");
        Double total = orderData.get("total") instanceof Integer 
            ? ((Integer) orderData.get("total")).doubleValue() 
            : (Double) orderData.get("total");
        
        return orderService.createOrder(customerId, total);
    }

    @PatchMapping("/{id}/status")
    public Map<String, Object> updateStatus(@PathVariable String id, @RequestBody Map<String, String> statusData) {
        String status = statusData.get("status");
        orderService.updateOrderStatus(id, status);
        
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Status updated");
        return response;
    }

    @GetMapping("/search")
    public Map<String, Object> searchOrders(@RequestParam String customerName) {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> orders = orderService.searchOrders(customerName);
        response.put("orders", orders);
        response.put("total", orders.size());
        return response;
    }

    @GetMapping("/stats/average")
    public Map<String, Object> getAverageOrderValue() {
        Map<String, Object> response = new HashMap<>();
        double average = orderService.calculateAverageOrderValue();
        response.put("averageOrderValue", average);
        return response;
    }
}
