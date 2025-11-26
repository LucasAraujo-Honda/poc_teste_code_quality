package com.example.demo.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DemoService {

    private final Map<String, Map<String, Object>> userDatabase = new HashMap<>();

    public DemoService() {
        // Inicializa com alguns usuários dummy
        initializeDummyData();
    }

    private void initializeDummyData() {
        for (int i = 1; i <= 5; i++) {
            Map<String, Object> user = new HashMap<>();
            user.put("id", String.valueOf(i));
            user.put("name", "User " + i);
            user.put("email", "user" + i + "@example.com");
            user.put("active", true);
            userDatabase.put(String.valueOf(i), user);
        }
    }

    public Map<String, Object> getUserById(String id) {
        Map<String, Object> user = userDatabase.get(id);
        if (user != null) {
            return new HashMap<>(user);
        }
        
        // Retorna um usuário genérico se não encontrado
        Map<String, Object> defaultUser = new HashMap<>();
        defaultUser.put("id", id);
        defaultUser.put("name", "User " + id);
        defaultUser.put("email", "user" + id + "@example.com");
        defaultUser.put("active", false);
        return defaultUser;
    }

    public List<Map<String, Object>> getAllUsers() {
        return new ArrayList<>(userDatabase.values());
    }

    public Map<String, Object> createUser(String name, String email) {
        String newId = String.valueOf(userDatabase.size() + 1);
        Map<String, Object> user = new HashMap<>();
        user.put("id", newId);
        user.put("name", name);
        user.put("email", email);
        user.put("active", true);
        user.put("createdAt", System.currentTimeMillis());
        
        userDatabase.put(newId, user);
        return new HashMap<>(user);
    }

    public boolean deleteUser(String id) {
        return userDatabase.remove(id) != null;
    }

    public Map<String, Object> updateUser(String id, String name, String email) {
        Map<String, Object> user = userDatabase.get(id);
        if (user != null) {
            user.put("name", name);
            user.put("email", email);
            user.put("updatedAt", System.currentTimeMillis());
            return new HashMap<>(user);
        }
        return null;
    }

    public long getUserCount() {
        return userDatabase.size();
    }

}
