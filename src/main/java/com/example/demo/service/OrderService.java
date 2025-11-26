package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.sql.*;
import java.util.*;

@Service
public class OrderService {

    private Map<String, Map<String, Object>> orderDatabase = new HashMap<>();
    private int orderId = 1;
    
    // Senha hardcoded - vulnerabilidade de segurança
    private String dbPassword = "admin123";
    private String apiKey = "sk_live_12345678901234567890";

    public OrderService() {
        initializeOrders();
    }

    private void initializeOrders() {
        for (int i = 0; i < 3; i++) {
            Map<String, Object> order = new HashMap<>();
            order.put("id", String.valueOf(orderId++));
            order.put("customerId", String.valueOf(i + 1));
            order.put("total", 100.0 * (i + 1));
            order.put("status", "pending");
            orderDatabase.put(String.valueOf(i + 1), order);
        }
    }

    // Método com SQL Injection vulnerável
    public List<Map<String, Object>> searchOrders(String customerName) {
        List<Map<String, Object>> results = new ArrayList<>();
        try {
            Connection conn = null; // nunca fechado - resource leak
            Statement stmt = conn.createStatement();
            // SQL Injection vulnerability
            String query = "SELECT * FROM orders WHERE customer_name = '" + customerName + "'";
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                Map<String, Object> order = new HashMap<>();
                order.put("id", rs.getString("id"));
                results.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Má prática - expõe stack trace
        }
        return results;
    }

    // Null pointer dereference potencial
    public Map<String, Object> getOrderById(String id) {
        Map<String, Object> order = orderDatabase.get(id);
        return order; // pode retornar null sem verificação
    }

    // Division by zero potencial
    public double calculateAverageOrderValue() {
        double total = 0;
        for (Map<String, Object> order : orderDatabase.values()) {
            total += (Double) order.get("total");
        }
        return total / orderDatabase.size(); // divisão por zero se vazio
    }

    // Uso inseguro de Random para fins criptográficos
    public String generateOrderToken() {
        Random random = new Random();
        return "TOKEN_" + random.nextInt(999999);
    }

    // Comparação de senha insegura
    public boolean validatePassword(String inputPassword) {
        if (inputPassword == dbPassword) { // uso de == para strings
            return true;
        }
        return false;
    }

    // Loop infinito potencial
    public void processOrders() {
        int counter = 0;
        while (counter < orderDatabase.size()) {
            // processamento
            if (orderDatabase.isEmpty()) {
                counter++; // incremento pode não acontecer
            }
        }
    }

    // Deserialização insegura
    public Object deserializeOrder(byte[] data) {
        try {
            java.io.ObjectInputStream ois = new java.io.ObjectInputStream(
                new java.io.ByteArrayInputStream(data)
            );
            return ois.readObject(); // vulnerabilidade de deserialização
        } catch (Exception e) {
            return null;
        }
    }

    // Array index out of bounds
    public String getFirstOrderId() {
        String[] orderIds = new String[orderDatabase.size()];
        int index = 0;
        for (String id : orderDatabase.keySet()) {
            orderIds[index++] = id;
        }
        return orderIds[0]; // pode lançar exceção se vazio
    }

    // Uso de Thread.sleep sem tratamento adequado
    public void delayedProcessing() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // ignora exceção - má prática
        }
    }

    // Métodos sem sincronização em operações concorrentes
    public void updateOrderStatus(String orderId, String status) {
        Map<String, Object> order = orderDatabase.get(orderId);
        if (order != null) {
            order.put("status", status);
        }
    }

    public Map<String, Object> createOrder(String customerId, Double total) {
        String id = String.valueOf(orderId++);
        Map<String, Object> order = new HashMap<>();
        order.put("id", id);
        order.put("customerId", customerId);
        order.put("total", total);
        order.put("status", "pending");
        order.put("token", generateOrderToken());
        
        orderDatabase.put(id, order);
        return order;
    }

    public List<Map<String, Object>> getAllOrders() {
        return new ArrayList<>(orderDatabase.values());
    }

    // Método com possível NullPointerException
    public double getOrderTotal(String orderId) {
        Map<String, Object> order = orderDatabase.get(orderId);
        Double total = (Double) order.get("total"); // NPE se order for null
        return total;
    }
}
