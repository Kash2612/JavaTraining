package com.example.student.student_SB.service;

import com.example.student.student_SB.dto.ProjectDTO;
import com.example.student.student_SB.entity.ProjectEntity;
import com.example.student.student_SB.exception.ProjectNotFoundException;
import com.example.student.student_SB.repository.ProjectRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ProjectEntity> getAllProjects() {
        return projectRepository.findAll();
    }

    public ProjectEntity getProjectById(Long id) throws ProjectNotFoundException {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + id));
    }

    public ProjectEntity createProject(ProjectDTO projectDTO) {
        ProjectEntity project = modelMapper.map(projectDTO, ProjectEntity.class);
        return projectRepository.save(project);
    }

    public ProjectEntity updateProject(Long id, ProjectDTO projectDTO) throws ProjectNotFoundException {
        ProjectEntity project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id: " + id));
        modelMapper.map(projectDTO, project);
        return projectRepository.save(project);
    }

    public void deleteProject(Long id) throws ProjectNotFoundException {
        if (!projectRepository.existsById(id)) {
            throw new ProjectNotFoundException("Project not found with id: " + id);
        }
        projectRepository.deleteById(id);
    }
}
