package br.com.sanches.gestao.service;
 
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import br.com.sanches.gestao.domain.employee.model.dto.EmployeeResponseDTO;
import br.com.sanches.gestao.domain.employee.service.EmployeeService;
import br.com.sanches.gestao.domain.position.model.dto.PositionResponseDTO;
import br.com.sanches.gestao.domain.project.model.adapter.ProjectAdapter;
import br.com.sanches.gestao.domain.project.model.dto.ProjectRequestDTO;
import br.com.sanches.gestao.domain.project.model.dto.ProjectResponseDTO;
import br.com.sanches.gestao.domain.project.model.entity.ProjectEntity;
import br.com.sanches.gestao.domain.project.repository.ProjectRepository;
import br.com.sanches.gestao.domain.project.service.ProjectService;
import br.com.sanches.gestao.domain.projectstatus.model.dto.ProjectStatusResponseDTO;
import br.com.sanches.gestao.domain.projectstatus.model.entity.ProjectStatusEntity;
import br.com.sanches.gestao.domain.projectstatus.service.ProjectStatusService;
import br.com.sanches.gestao.shared.enums.ProjectStatusesEnum;
import br.com.sanches.gestao.shared.exceptions.DataNotFoundException;
import br.com.sanches.gestao.shared.exceptions.ProjectNotSuitableForExclusionException;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    private static final String ALTO = "ALTO";

	private static final String TESTE = "Teste";

	@InjectMocks
    private ProjectService projectService;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private ProjectStatusService projectStatusService;

    @Test
    void testFindProjectById_Success() {
       
        String projectId = "1";
        ProjectRequestDTO request = getProjectRequest();
        ProjectEntity projectEntity = ProjectAdapter.fromDTOToEntity(request);
        projectEntity.setProjectCode(1);
        when(projectRepository.findById(1)).thenReturn(Optional.of(projectEntity));
 
        ProjectResponseDTO result = projectService.findProjectById(projectId);
 
        assertNotNull(result);
        verify(projectRepository, times(1)).findById(1);
    }

    @Test
    void testFindProjectById_NotFound() {
   
        String projectId = "1";
        when(projectRepository.findById(1)).thenReturn(Optional.empty());
 
        assertThrows(DataNotFoundException.class, () -> projectService.findProjectById(projectId));
        verify(projectRepository, times(1)).findById(1);
    }

    @Test
    void testFindProjectByName_Success() {
    	ProjectRequestDTO request = getProjectRequest();
        String projectName = "Test Project";
        ProjectEntity projectEntity = ProjectAdapter.fromDTOToEntity(request);
      
        when(projectRepository.findProjectEntityByName(projectName)).thenReturn(List.of(projectEntity));
 
        List<ProjectResponseDTO> result = projectService.findProjectByName(projectName);
 
        assertFalse(result.isEmpty());
        verify(projectRepository, times(1)).findProjectEntityByName(projectName);
    }

    @Test
    void testFindProjectByName_NotFound() {
     
        String projectName = "Teste Project";
        when(projectRepository.findProjectEntityByName(projectName)).thenReturn(List.of());
 
        assertThrows(DataNotFoundException.class, () -> projectService.findProjectByName(projectName));
        verify(projectRepository, times(1)).findProjectEntityByName(projectName);
    }

    @Test
    void testInsertProject_Success() {
        
        ProjectRequestDTO request = getProjectRequest();
         
        ProjectEntity projectEntity = ProjectAdapter.fromDTOToEntity(request);
         
        when(projectRepository.saveAndFlush(any())).thenReturn(projectEntity);
 
        ProjectResponseDTO result = projectService.insertProject(request);
 
        assertNotNull(result);
        verify(projectRepository, times(1)).saveAndFlush(any(ProjectEntity.class));
    }

	private ProjectRequestDTO getProjectRequest() {
		ProjectRequestDTO request = new ProjectRequestDTO();
        request.setForecastEndProject(LocalDate.now());
        request.setProjectCode(1);
        request.setProjectDescription(TESTE);
        request.setProjectName(TESTE);
        request.setProjectStartDate(LocalDate.now());
        request.setProjectStatus(ProjectStatusesEnum.EM_ANALISE.getCode());
        request.setRealEndProject(LocalDate.now().plusDays(10L));
        request.setResponsibleManager(1);
        request.setRisk(ALTO);
        request.setTotalProjectBudgetValue(BigDecimal.ONE);
		return request;
	}

    @Test
    void testUpdateProject_Success() {
         
        String projectId = "1";
        ProjectRequestDTO request = getProjectRequest();
        request.setResponsibleManager(1);
        request.setProjectStatus(1);
        
        ProjectEntity projectEntity = ProjectAdapter.fromDTOToEntity(request);
       
        when(projectRepository.findById(1)).thenReturn(Optional.of(projectEntity));
      
        PositionResponseDTO position = PositionResponseDTO.builder()
				.positionCode(1)
				.positionName(TESTE)
				.positionDescription(TESTE)
				.positionStatus("ATIVA")
				.build();

        EmployeeResponseDTO employee = EmployeeResponseDTO.builder()
				.employeeCode(1)
				.cpf("30039779890")
				.position(position)
				.employeeName(TESTE)
				.employeeStatus("ATIVO")
				.startDate(LocalDate.now())
				.build();
        
        when(employeeService.findEmployeeById(anyString())).thenReturn(employee);
       
        ProjectStatusResponseDTO projectStatusResponseDTO = ProjectStatusResponseDTO.builder()
        .statusCode(1)
        .statusDescription("Status").build();
        
        
        when(projectStatusService.findProjectStatusById(anyString())).thenReturn(projectStatusResponseDTO);
       
        when(projectRepository.saveAndFlush(any(ProjectEntity.class))).thenReturn(projectEntity);
 
        ProjectResponseDTO result = projectService.updateProject(request, projectId);
 
        assertNotNull(result);
        verify(projectRepository, times(1)).findById(1);
        verify(projectRepository, times(1)).saveAndFlush(any(ProjectEntity.class));
    }

    @Test
    void testDeleteProject_Success() {
       
        String projectId = "1";
        ProjectRequestDTO request = getProjectRequest();
        ProjectEntity projectEntity = ProjectAdapter.fromDTOToEntity(request);
        
        when(projectRepository.findById(1)).thenReturn(Optional.of(projectEntity));
      
        projectService.deleteProject(projectId);
 
        verify(projectRepository, times(1)).findById(1);
        verify(projectRepository, times(1)).delete(projectEntity);
    }

    @Test
    void testDeleteProject_NotSuitableForExclusion() {
        
        String projectId = "1";
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setProjectStatus(ProjectStatusEntity.builder().statusCode(ProjectStatusesEnum.INICIADO.getCode()).build());
        when(projectRepository.findById(1)).thenReturn(Optional.of(projectEntity));
 
        assertThrows(ProjectNotSuitableForExclusionException.class, () -> projectService.deleteProject(projectId));
        verify(projectRepository, times(1)).findById(1);
    }

    @Test
    void testRetrieveAllProjects_Success() {
  
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProjectEntity> projectPage = new PageImpl<>(List.of(new ProjectEntity()));
        when(projectRepository.findAll(pageable)).thenReturn(projectPage);
 
        Page<ProjectEntity> result = projectService.retrieveAllProjects(pageable);
 
        assertNotNull(result);
        verify(projectRepository, times(1)).findAll(pageable);
    }
}

