package com.example.demo.utils.MapptingUtils;

import com.example.demo.domain.Course;
import com.example.demo.domain.Module;
import com.example.demo.dto.ModuleDto;

public class ModuleMapper {

    public static Module mapDtoToModule(ModuleDto dto, Course course) {
        return new Module(dto.getId(), dto.getTitle(), dto.getDescription(), course);
    }

    public static ModuleDto mapModuleToDto(Module module) {
        if (module.getCourse() != null) {
            return new ModuleDto(module.getId(), module.getTitle(), module.getDescription(),
                    module.getCourse().getId());
        }
        return new ModuleDto(module.getId(), module.getTitle(), module.getDescription(), null);
    }
}
