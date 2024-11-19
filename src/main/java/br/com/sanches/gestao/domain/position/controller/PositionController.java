package br.com.sanches.gestao.domain.position.controller;

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

import br.com.sanches.gestao.domain.position.model.adapter.PositionAdapter;
import br.com.sanches.gestao.domain.position.model.dto.PositionRequestDTO;
import br.com.sanches.gestao.domain.position.model.dto.PositionResponseDTO;
import br.com.sanches.gestao.domain.position.model.entity.PositionEntity;
import br.com.sanches.gestao.domain.position.service.PositionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/gestao-projeto/cargo")
public class PositionController {
	
    private final PositionService positionService;
	
	@Autowired
	public PositionController(PositionService positionService) {
		this.positionService = positionService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PositionResponseDTO> findPositionById(@Required @PathVariable final String id) { 
		return ResponseEntity.ok().body(this.positionService.findPositionById(id));
	}

	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<PositionResponseDTO>> findPositionByName(@Required @PathVariable final String nome) { 
		return ResponseEntity.ok().body(this.positionService.findPositionByName(nome));
	}

	@PostMapping
	public ResponseEntity<PositionResponseDTO> insertPosition(@Required @Valid @RequestBody PositionRequestDTO request) {
		return ResponseEntity.ok().body(this.positionService.insertPosition(request));
	}

	@PutMapping("/{id}")
	public ResponseEntity<PositionResponseDTO> updatePosition(@Required @Valid @RequestBody PositionRequestDTO request,
			@Required @PathVariable final String id) {
		return ResponseEntity.ok().body(this.positionService.updatePosition(request, id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePosition(@Required @PathVariable final String id) {
		this.positionService.deletePosition(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/all")
	public ResponseEntity<Page<PositionResponseDTO>> findAllPositions(Pageable pageable) {

		Page<PositionEntity> page = this.positionService.retrieveAllPositions(pageable);

		return ResponseEntity.ok().body(page.map(PositionAdapter::fromEntityToDTO));
	}

}