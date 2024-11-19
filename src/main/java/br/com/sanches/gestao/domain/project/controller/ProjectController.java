package br.com.sanches.gestao.domain.project.controller;

import java.util.List;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sanches.gestao.domain.project.model.adapter.ProjectAdapter;
import br.com.sanches.gestao.domain.project.model.dto.ProjectRequestDTO;
import br.com.sanches.gestao.domain.project.model.dto.ProjectResponseDTO;
import br.com.sanches.gestao.domain.project.model.entity.ProjectEntity;
import br.com.sanches.gestao.domain.project.service.ProjectService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/gestao-projeto/projeto")
public class ProjectController {

	private final ProjectService projectService;

	@Autowired
	public ProjectController(ProjectService projectService) {
		this.projectService = projectService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProjectResponseDTO> findProjectById(@Required @PathVariable final String id) {
		return ResponseEntity.ok().body(this.projectService.findProjectById(id));
	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<ProjectResponseDTO>> findProjectByName(@Required @PathVariable final String nome) {
		return ResponseEntity.ok().body(this.projectService.findProjectByName(nome));
	}

	@PostMapping
	public ResponseEntity<ProjectResponseDTO> insertProject(@Required @Valid @RequestBody ProjectRequestDTO request) {
		return ResponseEntity.ok().body(this.projectService.insertProject(request));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProjectResponseDTO> updateProject(@Required @Valid @RequestBody ProjectRequestDTO request,
			@Required @PathVariable final String id) {
		return ResponseEntity.ok().body(this.projectService.updateProject(request, id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProject(@Required @PathVariable final String id) {
		this.projectService.deleteProject(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/all")
	public ResponseEntity<Page<ProjectResponseDTO>> findAllProjects(Pageable pageable) {

		Page<ProjectEntity> page = this.projectService.retrieveAllProjects(pageable);

		return ResponseEntity.ok().body(page.map(ProjectAdapter::fromEntityToDTO));
	}

}