package com.example.demo.controller;

import com.example.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DemoController {

    @Autowired
    private DemoService demoService;

    @GetMapping("/hello")
    public Map<String, String> hello() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello, World!");
        response.put("status", "success");
        return response;
    }

    @GetMapping("/status")
    public Map<String, Object> status() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "running");
        response.put("timestamp", System.currentTimeMillis());
        response.put("service", "Demo API");
        return response;
    }

    @PostMapping("/echo")
    public Map<String, Object> echo(@RequestBody Map<String, Object> payload) {
        Map<String, Object> response = new HashMap<>();
        response.put("received", payload);
        response.put("timestamp", System.currentTimeMillis());
        return response;
    }

    @GetMapping("/user/{id}")
    public Map<String, Object> getUser(@PathVariable String id) {
        return demoService.getUserById(id);
    }

    @GetMapping("/users")
    public Map<String, Object> getAllUsers() {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> users = demoService.getAllUsers();
        response.put("users", users);
        response.put("total", users.size());
        return response;
    }

    @PostMapping("/user")
    public Map<String, Object> createUser(@RequestBody Map<String, String> userData) {
        String name = userData.get("name");
        String email = userData.get("email");
        return demoService.createUser(name, email);
    }

    @DeleteMapping("/user/{id}")
    public Map<String, Object> deleteUser(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        boolean deleted = demoService.deleteUser(id);
        response.put("success", deleted);
        response.put("message", deleted ? "User deleted successfully" : "User not found");
        return response;
    }

}
