package br.com.sanches.gestao.domain.employee.controller;

import java.util.List;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

import br.com.sanches.gestao.domain.employee.model.adapter.EmployeeAdapter;
import br.com.sanches.gestao.domain.employee.model.dto.EmployeeRequestDTO;
import br.com.sanches.gestao.domain.employee.model.dto.EmployeeResponseDTO;
import br.com.sanches.gestao.domain.employee.model.entity.EmployeeEntity;
import br.com.sanches.gestao.domain.employee.service.EmployeeService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/gestao-projeto/colaborador")
public class EmployeeController {

	private final EmployeeService employeeService;
	
	@Autowired
	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EmployeeResponseDTO> findEmployeeById(@Required @PathVariable final String id) { 
		return ResponseEntity.ok().body(employeeService.findEmployeeById(id));
	}

	@GetMapping("/cpf/{cpf}")
	public ResponseEntity<EmployeeResponseDTO> findEmployeeByCpf(@Required @PathVariable final String cpf) {

		EmployeeResponseDTO dto = new EmployeeResponseDTO();

		return ResponseEntity.ok().body(dto);
	}

	@PostMapping
	public ResponseEntity<EmployeeResponseDTO> insertEmployee(@Required @Valid @RequestBody EmployeeRequestDTO request) {

		return ResponseEntity.ok().body(new EmployeeResponseDTO());
	}

	@PutMapping("/{id}")
	public ResponseEntity<EmployeeResponseDTO> updateEmployee(@Required @Valid @RequestBody EmployeeRequestDTO request,
			@Required @PathVariable final String id) {

		return ResponseEntity.ok().body(new EmployeeResponseDTO());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmployee(@Required @PathVariable final String id) {

		return ResponseEntity.noContent().build();
	}

	@GetMapping("/all")
	public ResponseEntity<Page<EmployeeResponseDTO>> findAllEmployees(Pageable pageable) {

		Page<EmployeeEntity> page = new PageImpl<>(List.of(new EmployeeEntity()));

		return ResponseEntity.ok().body(page.map(EmployeeAdapter::fromEntityToDTO));
	}

}