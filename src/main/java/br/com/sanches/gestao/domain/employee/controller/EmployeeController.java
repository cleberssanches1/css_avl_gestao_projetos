package br.com.sanches.gestao.domain.employee.controller;

import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gestao/projeto/colaborador")
public class EmployeeController {
 
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@Required @PathVariable final String id) throws NumberFormatException, Exception{		 
		return ResponseEntity.ok().body("OK");	
	}
}