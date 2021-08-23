package com.example.demo.service;

import com.example.demo.dao.ModuleRepository;
import com.example.demo.domain.Module;
import com.example.demo.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    @Autowired
    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public Module findById(Long id) {
        return moduleRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void saveModule(Module module) {
        moduleRepository.save(module);
    }
}
