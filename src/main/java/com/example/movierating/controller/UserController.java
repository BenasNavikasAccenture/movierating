package com.example.movierating.controller;

import com.example.movierating.model.User;
import com.example.movierating.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getOne(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        user.setId(null);
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }
    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User updated) {
        User user = userRepository.findById(id).orElseThrow();
        user.setUsername(updated.getUsername());
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userRepository.deleteById(id);
    }
}
