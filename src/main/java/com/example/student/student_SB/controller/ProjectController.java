package com.example.student.student_SB.controller;

import com.example.student.student_SB.dto.ProjectDTO;
import com.example.student.student_SB.entity.ProjectEntity;
import com.example.student.student_SB.exception.ProjectNotFoundException;
import com.example.student.student_SB.service.ProjectService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<ProjectDTO> getAllProjects() {
        return projectService.getAllProjects().stream()
                .map(project -> modelMapper.map(project, ProjectDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) throws ProjectNotFoundException {
        ProjectEntity project = projectService.getProjectById(id);
        return ResponseEntity.ok(modelMapper.map(project, ProjectDTO.class));
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody @Valid ProjectDTO projectDTO) {
        ProjectEntity project = projectService.createProject(projectDTO);
        return ResponseEntity.ok(modelMapper.map(project, ProjectDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long id, @RequestBody @Valid ProjectDTO projectDTO) throws ProjectNotFoundException {
        ProjectEntity updatedProject = projectService.updateProject(id, projectDTO);
        return ResponseEntity.ok(modelMapper.map(updatedProject, ProjectDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) throws ProjectNotFoundException {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
