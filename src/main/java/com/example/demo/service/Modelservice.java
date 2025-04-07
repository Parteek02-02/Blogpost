package com.example.demo.service;

import com.example.demo.model.Model;
import com.example.demo.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class Modelservice {  

    @Autowired
    private ModelRepository modelRepository;

    // Create a new model
    public Model createModel(String title, String content) {
        Model newModel = new Model(title, content);
        return modelRepository.save(newModel); 
    }

    // Retrieve all models
    public List<Model> getAllModels() {
        return modelRepository.findAll();
    }

    // Retrieve a model by ID
    public Optional<Model> getModelById(Long id) {
        return modelRepository.findById(id);
    }

    // Update an existing model
    public Model updateModel(Long id, String title, String content) {
        Optional<Model> existingModel = modelRepository.findById(id);
        if (existingModel.isPresent()) {
            Model updatedModel = existingModel.get();
            updatedModel.setTitle(title);
            updatedModel.setContent(content);
            return modelRepository.save(updatedModel);  
        } else {
            throw new RuntimeException("Model not found");
        }
    }

    // Delete a model by ID
    public void deleteModel(Long id) {
        modelRepository.deleteById(id);  
    }

    // ✅ Swap Title and Content for all records
    @Transactional
    public void swapTitleAndContent() {
        List<Model> models = modelRepository.findAll();
        
        for (Model model : models) {
            String temp = model.getTitle();
            model.setTitle(model.getContent());
            model.setContent(temp);
        }

        modelRepository.saveAll(models);  // Save updated records
    }
}