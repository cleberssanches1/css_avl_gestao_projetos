package br.com.sanches.gestao.domain.employee.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.sanches.gestao.domain.employee.model.adapter.EmployeeAdapter;
import br.com.sanches.gestao.domain.employee.model.dto.EmployeeResponseDTO;
import br.com.sanches.gestao.domain.employee.model.entity.EmployeeEntity;
import br.com.sanches.gestao.domain.employee.repository.EmployeeRepository;
import br.com.sanches.gestao.shared.exceptions.EmployeeNotFoundException;

@Service
public class EmployeeService {

	private static final String NOT_FOUND = " não encontrado.";
	private static final String EMPLOYEE = "Colaborador ";
	
	private final EmployeeRepository employeeRepository;

	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public EmployeeResponseDTO findEmployeeById(String id) {

		Optional<EmployeeEntity> employeeOptional = employeeRepository.findById(Integer.valueOf(id));

		return employeeOptional.map(EmployeeAdapter::fromEntityToDTO)
				.orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE + id + NOT_FOUND));

	}

}
