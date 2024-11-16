package br.com.sanches.gestao.domain.employee.service;
 
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.sanches.gestao.domain.employee.model.adapter.EmployeeAdapter;
import br.com.sanches.gestao.domain.employee.model.dto.EmployeeRequestDTO;
import br.com.sanches.gestao.domain.employee.model.dto.EmployeeResponseDTO;
import br.com.sanches.gestao.domain.employee.model.entity.EmployeeEntity;
import br.com.sanches.gestao.domain.employee.repository.EmployeeRepository;
import br.com.sanches.gestao.domain.position.model.entity.PositionEntity;
import br.com.sanches.gestao.domain.position.repository.PositionRepository;
import br.com.sanches.gestao.shared.exceptions.EmployeeNotFoundException;
import br.com.sanches.gestao.shared.exceptions.PositionNotFoundException;

@Service
public class EmployeeService {

	private static final String COLLABORATOR_WITH_CPF = "Colaborador com o CPF ";
	private static final String NOT_FOUND = " não encontrado.";
	private static final String EMPLOYEE = "Colaborador ";
	private static final String POSITION = "Cargo ";

	private final EmployeeRepository employeeRepository;
	private final PositionRepository positionRepository;
	
	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository,
			PositionRepository positionRepository) {
		this.employeeRepository = employeeRepository;
		this.positionRepository = positionRepository;
	}

	public EmployeeResponseDTO findEmployeeById(String id) {

		Optional<EmployeeEntity> employeeOptional = findEmployee(id);

		return employeeOptional.map(EmployeeAdapter::fromEntityToDTO)
				.orElseThrow(() -> new EmployeeNotFoundException(EMPLOYEE + id + NOT_FOUND));

	}

	private Optional<EmployeeEntity> findEmployee(String id) {
		return employeeRepository.findById(Integer.valueOf(id));
	}

	public List<EmployeeResponseDTO> findEmployeeByCpf(String cpf) {

		List<EmployeeEntity> employeeList = employeeRepository.findEmployeeEntityByCpf(cpf);

		if (employeeList.isEmpty()) {
			throw new EmployeeNotFoundException(COLLABORATOR_WITH_CPF + cpf + NOT_FOUND);
		}

		return employeeList.stream().map(EmployeeAdapter::fromEntityToDTO).toList();
	}
	
	public EmployeeResponseDTO insertEmployee(EmployeeRequestDTO request) {		
		return EmployeeAdapter.fromEntityToDTO(this.employeeRepository.saveAndFlush(EmployeeAdapter.fromDTOToEntity(request)));	 
	}
		
	public EmployeeResponseDTO updateEmployee(EmployeeRequestDTO request, String id) {	
		Optional<EmployeeEntity> employeeOptional = findEmployee(id);
		
		if(employeeOptional.isEmpty()) {
			throw new EmployeeNotFoundException(EMPLOYEE + id + NOT_FOUND);
		}
		
		Optional<PositionEntity> positionOptional = positionRepository.findById(request.getPosition());
		
		if(positionOptional.isEmpty()) {
			throw new PositionNotFoundException(POSITION + id + NOT_FOUND);	
		}
				
		employeeOptional.get().setCpf(request.getCpf());
		employeeOptional.get().setPosition(positionOptional.get());
		employeeOptional.get().setEmployeeName(request.getEmployeeName());
		employeeOptional.get().setEmployeeStatus(request.getEmployeeStatus());
		employeeOptional.get().setStartDate(request.getStartDate());
		
		return EmployeeAdapter.fromEntityToDTO(this.employeeRepository.saveAndFlush(employeeOptional.get()));	 
	}
	
	public void deleteEmployee(String id) {		
        Optional<EmployeeEntity> employeeOptional = findEmployee(id);
		
		if(employeeOptional.isEmpty()) {
			throw new EmployeeNotFoundException(EMPLOYEE + id + NOT_FOUND);
		}
		
		employeeOptional.get().setEmployeeStatus("INATIVO");
		
		this.employeeRepository.saveAndFlush(employeeOptional.get());			 
	}
	
	public Page<EmployeeEntity> retrieveAllEmployees(Pageable page){		
		return this.employeeRepository.findAll(page); 
	}
	
}