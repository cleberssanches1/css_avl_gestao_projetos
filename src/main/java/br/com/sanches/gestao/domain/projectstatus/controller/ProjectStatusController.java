package br.com.sanches.gestao.domain.projectstatus.controller;

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

import br.com.sanches.gestao.domain.projectstatus.model.adapter.ProjectStatusAdapter;
import br.com.sanches.gestao.domain.projectstatus.model.dto.ProjectStatusRequestDTO;
import br.com.sanches.gestao.domain.projectstatus.model.dto.ProjectStatusResponseDTO;
import br.com.sanches.gestao.domain.projectstatus.model.entity.ProjectStatusEntity;
import br.com.sanches.gestao.domain.projectstatus.service.ProjectStatusService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/gestao-projeto/projeto/status")
public class ProjectStatusController {
 
	private final ProjectStatusService projectStatusService;
	
	@Autowired
	public ProjectStatusController(ProjectStatusService projectStatusService) {
		this.projectStatusService = projectStatusService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProjectStatusResponseDTO> findProjectStatusById(@Required @PathVariable final String id) { 
		return ResponseEntity.ok().body(this.projectStatusService.findProjectStatusById(id));
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<ProjectStatusResponseDTO>> findProjectStatusByCpf(@Required @PathVariable final String name) { 
		return ResponseEntity.ok().body(this.projectStatusService.findProjectStatusByName(name));
	}

	@PostMapping
	public ResponseEntity<ProjectStatusResponseDTO> insertProjectStatus(@Required @Valid @RequestBody ProjectStatusRequestDTO request) {
		return ResponseEntity.ok().body(this.projectStatusService.insertProjectStatus(request));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProjectStatusResponseDTO> updateProjectStatus(@Required @Valid @RequestBody ProjectStatusRequestDTO request,
			@Required @PathVariable final String id) {
		return ResponseEntity.ok().body(this.projectStatusService.updateProjectStatus(request, id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProjectStatus(@Required @PathVariable final String id) {
		this.projectStatusService.deleteProjectStatus(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/all")
	public ResponseEntity<Page<ProjectStatusResponseDTO>> findAllProjectStatuss(Pageable pageable) {

		Page<ProjectStatusEntity> page = this.projectStatusService.retrieveAllProjectStatuss(pageable);

		return ResponseEntity.ok().body(page.map(ProjectStatusAdapter::fromEntityToDTO));
	}

}