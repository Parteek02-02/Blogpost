package com.example.demo.controller;

import com.example.demo.model.Model;
import com.example.demo.service.Modelservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173") 
@RestController
@RequestMapping("/api/models")  
public class Controller {  

    @Autowired
    private Modelservice modelService;  

    // Create a new model
    @PostMapping
    public ResponseEntity<Model> createModel(@RequestBody Model newModel) {
        Model createdModel = modelService.createModel(newModel.getTitle(), newModel.getContent());
        return ResponseEntity.ok(createdModel);  
    }
    @GetMapping
    public ResponseEntity<List<Model>> getAllModels() {
        List<Model> models = modelService.getAllModels();
        return ResponseEntity.ok(models);
    }

    // Retrieve a model by ID
    @GetMapping("/{id}")
    public ResponseEntity<Model> getModelById(@PathVariable Long id) {
        Optional<Model> existingModel = modelService.getModelById(id);
        return existingModel.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Update a model
    @PutMapping("/{id}")
    public ResponseEntity<Model> updateModel(@PathVariable Long id, @RequestBody Model updatedData) {
        try {
            Model updatedModel = modelService.updateModel(id, updatedData.getTitle(), updatedData.getContent());
            return ResponseEntity.ok(updatedModel);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a model
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModel(@PathVariable Long id) {
        modelService.deleteModel(id);
        return ResponseEntity.noContent().build();
    }
}