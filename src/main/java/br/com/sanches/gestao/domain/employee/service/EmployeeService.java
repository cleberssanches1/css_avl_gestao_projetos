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
import br.com.sanches.gestao.shared.enums.StatusEnum;
import br.com.sanches.gestao.shared.exceptions.DataNotFoundException;
import br.com.sanches.gestao.shared.utils.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeService {
  
	private final EmployeeRepository employeeRepository;
	private final PositionRepository positionRepository;
	
	@Autowired
	public EmployeeService(EmployeeRepository employeeRepository,
			PositionRepository positionRepository) {
		this.employeeRepository = employeeRepository;
		this.positionRepository = positionRepository;
	}

	public EmployeeResponseDTO findEmployeeById(String id) {
		log.info(Constants.BUSCANDO_COLABORADOR_POR_ID, id);
		 
		Optional<EmployeeEntity> employeeOptional = findEmployee(id);

		return employeeOptional.map(EmployeeAdapter::fromEntityToDTO)
				.orElseThrow(() -> new DataNotFoundException(Constants.EMPLOYEE + id + Constants.NOT_FOUND));

	}

	private Optional<EmployeeEntity> findEmployee(String id) {
		return employeeRepository.findById(Integer.valueOf(id));
	}

	public List<EmployeeResponseDTO> findEmployeeByCpf(String cpf) {

		log.info(Constants.BUSCANDO_COLABORADOR_POR_CPF, cpf);
		
		List<EmployeeEntity> employeeList = employeeRepository.findEmployeeEntityByCpf(cpf);

		if (employeeList.isEmpty()) {
			throw new DataNotFoundException(Constants.COLLABORATOR_WITH_CPF + cpf + Constants.NOT_FOUND);
		}

		return employeeList.stream().map(EmployeeAdapter::fromEntityToDTO).toList();
	}
	
	public EmployeeResponseDTO insertEmployee(EmployeeRequestDTO request) {
		log.info(Constants.INSERINDO_COLABORADOR, request);
		return EmployeeAdapter.fromEntityToDTO(this.employeeRepository.saveAndFlush(EmployeeAdapter.fromDTOToEntity(request)));	 
	}
		
	public EmployeeResponseDTO updateEmployee(EmployeeRequestDTO request, String id) {	
		log.info(Constants.ATUALIZANDO_COLABORADOR, request, id);
		Optional<EmployeeEntity> employeeOptional = findEmployee(id);
		
		if(employeeOptional.isEmpty()) {
			throw new DataNotFoundException(Constants.EMPLOYEE + id + Constants.NOT_FOUND);
		}
		
		Optional<PositionEntity> positionOptional = positionRepository.findById(request.getPosition());
		
		if(positionOptional.isEmpty()) {
			throw new DataNotFoundException(Constants.POSITION + id + Constants.NOT_FOUND);	
		}
				
		employeeOptional.get().setCpf(request.getCpf());
		employeeOptional.get().setPosition(positionOptional.get());
		employeeOptional.get().setEmployeeName(request.getEmployeeName());
		employeeOptional.get().setEmployeeStatus(request.getEmployeeStatus());
		employeeOptional.get().setStartDate(request.getStartDate());
		
		return EmployeeAdapter.fromEntityToDTO(this.employeeRepository.saveAndFlush(employeeOptional.get()));	 
	}
	
	public void deleteEmployee(String id) {		
		log.info(Constants.EXCLUINDO_COLABORADOR, id);
		
        Optional<EmployeeEntity> employeeOptional = findEmployee(id);
		
		if(employeeOptional.isEmpty()) {
			throw new DataNotFoundException(Constants.EMPLOYEE + id + Constants.NOT_FOUND);
		}
		
		employeeOptional.get().setEmployeeStatus(StatusEnum.INATIVO.getDescription());
		
		this.employeeRepository.saveAndFlush(employeeOptional.get());			 
	}
	
	public Page<EmployeeEntity> retrieveAllEmployees(Pageable page){		
		return this.employeeRepository.findAll(page); 
	}
	
}