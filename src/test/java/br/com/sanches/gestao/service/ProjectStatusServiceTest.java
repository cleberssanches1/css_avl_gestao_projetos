package br.com.sanches.gestao.service;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import br.com.sanches.gestao.domain.projectstatus.model.adapter.ProjectStatusAdapter;
import br.com.sanches.gestao.domain.projectstatus.model.dto.ProjectStatusRequestDTO;
import br.com.sanches.gestao.domain.projectstatus.model.dto.ProjectStatusResponseDTO;
import br.com.sanches.gestao.domain.projectstatus.model.entity.ProjectStatusEntity;
import br.com.sanches.gestao.domain.projectstatus.repository.ProjectStatusRepository;
import br.com.sanches.gestao.domain.projectstatus.service.ProjectStatusService;
import br.com.sanches.gestao.shared.exceptions.DataNotFoundException;

@ExtendWith(MockitoExtension.class)
class ProjectStatusServiceTest {

    @InjectMocks
    private ProjectStatusService projectStatusService;

    @Mock
    private ProjectStatusRepository projectStatusRepository;
 
    @Test
    void testFindProjectStatusById_Success() {
        ProjectStatusEntity entity = new ProjectStatusEntity(1, "Active", null);
        
        when(projectStatusRepository.findById(any())).thenReturn(Optional.of(entity));

        ProjectStatusResponseDTO result = projectStatusService.findProjectStatusById("1");

        assertEquals(1, result.getStatusCode());
        assertEquals("Active", result.getStatusDescription());
        verify(projectStatusRepository, times(1)).findById(1);
    }

    @Test
    void testFindProjectStatusById_NotFound() {
        when(projectStatusRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> projectStatusService.findProjectStatusById("1"));
        verify(projectStatusRepository, times(1)).findById(1);
    }

    @Test
    void testFindProjectStatusByName_Success() {
        ProjectStatusEntity entity = new ProjectStatusEntity(1, "Active", null);
        when(projectStatusRepository.findProjectStatusEntityByName(any())).thenReturn(List.of(entity));

        List<ProjectStatusResponseDTO> result = projectStatusService.findProjectStatusByName("Active");

        assertEquals(1, result.size());
        assertEquals("Active", result.get(0).getStatusDescription());
        verify(projectStatusRepository, times(1)).findProjectStatusEntityByName("Active");
    }

    @Test
    void testFindProjectStatusByName_NotFound() {
        when(projectStatusRepository.findProjectStatusEntityByName(any())).thenReturn(List.of());

        assertThrows(DataNotFoundException.class, () -> projectStatusService.findProjectStatusByName("Inactive"));
        verify(projectStatusRepository, times(1)).findProjectStatusEntityByName("Inactive");
    }

    @Test
    void testInsertProjectStatus() {
        ProjectStatusRequestDTO request = new ProjectStatusRequestDTO(1, "Active");
        ProjectStatusEntity entity = ProjectStatusAdapter.fromDTOToEntity(request);
        when(projectStatusRepository.saveAndFlush(any(ProjectStatusEntity.class))).thenReturn(entity);

        ProjectStatusResponseDTO result = projectStatusService.insertProjectStatus(request);

        assertEquals("Active", result.getStatusDescription());
        verify(projectStatusRepository, times(1)).saveAndFlush(any(ProjectStatusEntity.class));
    }

    @Test
    void testUpdateProjectStatus_Success() {
        ProjectStatusRequestDTO request = new ProjectStatusRequestDTO(1, "Updated Status");
        ProjectStatusEntity entity = new ProjectStatusEntity(1, "Old Status", null);
        when(projectStatusRepository.findById(any())).thenReturn(Optional.of(entity));
        when(projectStatusRepository.saveAndFlush(any(ProjectStatusEntity.class))).thenReturn(entity);

        ProjectStatusResponseDTO result = projectStatusService.updateProjectStatus(request, "1");

        assertEquals("Updated Status", result.getStatusDescription());
        verify(projectStatusRepository, times(1)).findById(1);
        verify(projectStatusRepository, times(1)).saveAndFlush(entity);
    }

    @Test
    void testUpdateProjectStatus_NotFound() {
        ProjectStatusRequestDTO request = new ProjectStatusRequestDTO(1, "Updated Status");
        when(projectStatusRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> projectStatusService.updateProjectStatus(request, "1"));
        verify(projectStatusRepository, times(1)).findById(1);
    }

    @Test
    void testDeleteProjectStatus_Success() {
        ProjectStatusEntity entity = new ProjectStatusEntity(1, "Active", null);
        when(projectStatusRepository.findById(any())).thenReturn(Optional.of(entity));

        projectStatusService.deleteProjectStatus("1");

        verify(projectStatusRepository, times(1)).findById(1);
        verify(projectStatusRepository, times(1)).delete(entity);
    }

    @Test
    void testDeleteProjectStatus_NotFound() {
        when(projectStatusRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> projectStatusService.deleteProjectStatus("1"));
        verify(projectStatusRepository, times(1)).findById(1);
    }

    @Test
    void testRetrieveAllProjectStatuss() {
        ProjectStatusEntity entity = new ProjectStatusEntity(1, "Active", null);
        Pageable pageable = PageRequest.of(0, 10);
        Page<ProjectStatusEntity> page = new PageImpl<>(List.of(entity));
        when(projectStatusRepository.findAll(any(Pageable.class))).thenReturn(page);

        Page<ProjectStatusEntity> result = projectStatusService.retrieveAllProjectStatuss(pageable);

        assertEquals(1, result.getContent().size());
        verify(projectStatusRepository, times(1)).findAll(pageable);
    }
}