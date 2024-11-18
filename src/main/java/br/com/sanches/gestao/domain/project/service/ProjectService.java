package br.com.sanches.gestao.domain.project.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.sanches.gestao.domain.employee.model.adapter.EmployeeAdapter;
import br.com.sanches.gestao.domain.employee.model.dto.EmployeeResponseDTO;
import br.com.sanches.gestao.domain.employee.model.entity.EmployeeEntity;
import br.com.sanches.gestao.domain.employee.service.EmployeeService;
import br.com.sanches.gestao.domain.project.model.adapter.ProjectAdapter;
import br.com.sanches.gestao.domain.project.model.dto.ProjectRequestDTO;
import br.com.sanches.gestao.domain.project.model.dto.ProjectResponseDTO;
import br.com.sanches.gestao.domain.project.model.entity.ProjectEntity;
import br.com.sanches.gestao.domain.project.repository.ProjectRepository;
import br.com.sanches.gestao.domain.projectstatus.model.dto.ProjectStatusResponseDTO;
import br.com.sanches.gestao.domain.projectstatus.model.entity.ProjectStatusEntity;
import br.com.sanches.gestao.domain.projectstatus.service.ProjectStatusService;
import br.com.sanches.gestao.shared.exceptions.DataNotFoundException;
import br.com.sanches.gestao.shared.exceptions.ProjectNotSuitableForExclusionException;
import br.com.sanches.gestao.shared.utils.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProjectService {
    
	private final ProjectRepository projectRepository;
	private final EmployeeService employeeService;	
	private final ProjectStatusService projectStatusService;
	
	@Autowired
	public ProjectService(ProjectRepository projectRepository,
			EmployeeService employeeService,
			ProjectStatusService projectStatusService) {
		this.projectRepository = projectRepository;
		this.employeeService = employeeService;
		this.projectStatusService = projectStatusService;
	}

	public ProjectResponseDTO findProjectById(String id) {
		log.info(Constants.BUSCANDO_PROJETO_COM_O_ID, id);
		Optional<ProjectEntity> projectOptional = findProject(id);

		return projectOptional.map(ProjectAdapter::fromEntityToDTO)
				.orElseThrow(() -> new DataNotFoundException(Constants.PROJECT + id + Constants.NOT_FOUND));
	}
 
	public List<ProjectResponseDTO> findProjectByName(String projectName) {
		log.info(Constants.BUSCANDO_PROJETO_COM_O_NOME, projectName);
		List<ProjectEntity> projectList = projectRepository.findProjectEntityByName(projectName);

		if (projectList.isEmpty()) {
			throw new DataNotFoundException(Constants.PROJECT_NOT_FOUND + projectName + Constants.NOT_FOUND);
		}

		return projectList.stream().map(ProjectAdapter::fromEntityToDTO).toList();
	}
	
	public ProjectResponseDTO insertProject(ProjectRequestDTO request) {
		log.info(Constants.INSERINDO_PROJETO, request);
		 
		return ProjectAdapter.fromEntityToDTO(this.projectRepository.saveAndFlush(ProjectAdapter.fromDTOToEntity(request)));	 
	}
		
	public ProjectResponseDTO updateProject(ProjectRequestDTO request, String id) {	
		log.info(Constants.ATUALIZANDO_PROJETO, request, id);
		Optional<ProjectEntity> projectOptional = findProject(id);
		
		if(projectOptional.isEmpty()) {
			throw new DataNotFoundException(Constants.PROJECT + id + Constants.NOT_FOUND);
		}
		 
		EmployeeResponseDTO employeeResponseDTO = this.employeeService.findEmployeeById(Objects.nonNull(request.getResponsibleManager()) ? request.getResponsibleManager().toString() : Constants.ZERO);
		
		ProjectStatusResponseDTO projectStatusResponseDTO = projectStatusService.findProjectStatusById(request.getProjectStatus().toString());
		 
		ProjectStatusEntity status = ProjectStatusEntity.builder().statusCode(projectStatusResponseDTO.getStatusCode()).build();
		EmployeeEntity employee = EmployeeAdapter.fromResponseDTOToEntity(employeeResponseDTO);
 
		ProjectEntity entity = projectOptional.get();
		 
		entity.setForecastEndProject(request.getForecastEndProject());		 
		entity.setProjectDescription(request.getProjectDescription());
		entity.setProjectName(request.getProjectName());
		entity.setProjectStartDate(request.getProjectStartDate());
		entity.setProjectStatus(status);		
		entity.setRealEndProject(request.getRealEndProject());
		entity.setResponsibleManager(employee);
		entity.setRisk(request.getRisk());
		entity.setTotalProjectBudgetValue(request.getTotalProjectBudgetValue());
		
		return ProjectAdapter.fromEntityToDTO(this.projectRepository.saveAndFlush(entity));	 
	}
	
	public void deleteProject(String id) {	
		
		log.info(Constants.DELETANDO_O_PROJETO, id);
		
        Optional<ProjectEntity> projectOptional = findProject(id);
		
		if(projectOptional.isEmpty()) {
			throw new DataNotFoundException(Constants.PROJECT + id + Constants.NOT_FOUND);
		}
		
		if(this.isTheProjectEligibleForDeletion(projectOptional.get())) {
			throw new ProjectNotSuitableForExclusionException(Constants.PROJECT_NOT_SUITABLE_FOR_EXCLUSION + projectOptional.get().getProjectStatus().getStatusDescription());
		}
		 
		this.projectRepository.delete(projectOptional.get());			 
	}

	public Page<ProjectEntity> retrieveAllProjects(Pageable page){	
		log.info(Constants.RETORNANDO_TODOS_OS_PROJETOS, page);
		return this.projectRepository.findAll(page); 
	}
	
	private boolean isTheProjectEligibleForDeletion(ProjectEntity projec) {
		return projec.getProjectStatus().getStatusCode() > 3;
	}
	 
	private Optional<ProjectEntity> findProject(String id) {
		return projectRepository.findById(Integer.valueOf(id));
	}
	
}