package br.com.sanches.gestao.domain.projectstatus.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.sanches.gestao.domain.projectstatus.model.adapter.ProjectStatusAdapter;
import br.com.sanches.gestao.domain.projectstatus.model.dto.ProjectStatusRequestDTO;
import br.com.sanches.gestao.domain.projectstatus.model.dto.ProjectStatusResponseDTO;
import br.com.sanches.gestao.domain.projectstatus.model.entity.ProjectStatusEntity;
import br.com.sanches.gestao.domain.projectstatus.repository.ProjectStatusRepository;
import br.com.sanches.gestao.shared.exceptions.ProjectStatusNotFoundException;
import br.com.sanches.gestao.shared.utils.Constants;

@Service
public class ProjectStatusService {

	private final ProjectStatusRepository projectStatusRepository;

	@Autowired
	public ProjectStatusService(ProjectStatusRepository projectStatusRepository) {
		this.projectStatusRepository = projectStatusRepository;
	}

	public ProjectStatusResponseDTO findProjectStatusById(String id) {

		Optional<ProjectStatusEntity> ProjectStatusOptional = findProjectStatus(id);

		return ProjectStatusOptional.map(ProjectStatusAdapter::fromEntityToDTO).orElseThrow(
				() -> new ProjectStatusNotFoundException(Constants.PROJECT_STATUS + id + Constants.NOT_FOUND));
	}

	public List<ProjectStatusResponseDTO> findProjectStatusByName(String name) {

		List<ProjectStatusEntity> projectStatusList = projectStatusRepository.findProjectStatusEntityByName(name);

		if (projectStatusList.isEmpty()) {
			throw new ProjectStatusNotFoundException(Constants.PROJECT_STATUS_WITH_NAME + name + Constants.NOT_FOUND);
		}

		return projectStatusList.stream().map(ProjectStatusAdapter::fromEntityToDTO).toList();
	}

	public ProjectStatusResponseDTO insertProjectStatus(ProjectStatusRequestDTO request) {
		return ProjectStatusAdapter.fromEntityToDTO(
				this.projectStatusRepository.saveAndFlush(ProjectStatusAdapter.fromDTOToEntity(request)));
	}

	public ProjectStatusResponseDTO updateProjectStatus(ProjectStatusRequestDTO request, String id) {
		Optional<ProjectStatusEntity> projectStatusOptional = findProjectStatus(id);

		if (projectStatusOptional.isEmpty()) {
			throw new ProjectStatusNotFoundException(Constants.PROJECT_STATUS + id + Constants.NOT_FOUND);
		}

		projectStatusOptional.get().setStatusDescription(request.getStatusDescription());

		return ProjectStatusAdapter
				.fromEntityToDTO(this.projectStatusRepository.saveAndFlush(projectStatusOptional.get()));
	}

	public void deleteProjectStatus(String id) {
		Optional<ProjectStatusEntity> projectStatusOptional = findProjectStatus(id);

		if (projectStatusOptional.isEmpty()) {
			throw new ProjectStatusNotFoundException(Constants.PROJECT_STATUS + id + Constants.NOT_FOUND);
		}

		this.projectStatusRepository.delete(projectStatusOptional.get());
	}

	public Page<ProjectStatusEntity> retrieveAllProjectStatuss(Pageable page) {
		return this.projectStatusRepository.findAll(page);
	}

	private Optional<ProjectStatusEntity> findProjectStatus(String id) {
		return projectStatusRepository.findById(Integer.valueOf(id));
	}

}