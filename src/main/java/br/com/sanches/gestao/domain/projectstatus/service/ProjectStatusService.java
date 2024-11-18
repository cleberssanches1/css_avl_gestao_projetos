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
import br.com.sanches.gestao.shared.exceptions.DataNotFoundException;
import br.com.sanches.gestao.shared.utils.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProjectStatusService {
 
	private final ProjectStatusRepository projectStatusRepository;

	@Autowired
	public ProjectStatusService(ProjectStatusRepository projectStatusRepository) {
		this.projectStatusRepository = projectStatusRepository;
	}

	public ProjectStatusResponseDTO findProjectStatusById(String id) {

		log.info(Constants.BUSCANDO_STATUS_DO_PROJETO_COM_O_ID + id);
		
		Optional<ProjectStatusEntity> projectStatusOptional = findProjectStatus(id);

		return projectStatusOptional.map(ProjectStatusAdapter::fromEntityToDTO).orElseThrow(
				() -> new DataNotFoundException(Constants.PROJECT_STATUS + id + Constants.NOT_FOUND));
	}

	public List<ProjectStatusResponseDTO> findProjectStatusByName(String name) {

		log.info(Constants.BUSCANDO_STATUS_DO_PROJETO_COM_O_NOME + name);
		
		List<ProjectStatusEntity> projectStatusList = projectStatusRepository.findProjectStatusEntityByName(name);

		if (projectStatusList.isEmpty()) {
			throw new DataNotFoundException(Constants.PROJECT_STATUS_WITH_NAME + name + Constants.NOT_FOUND);
		}

		return projectStatusList.stream().map(ProjectStatusAdapter::fromEntityToDTO).toList();
	}

	public ProjectStatusResponseDTO insertProjectStatus(ProjectStatusRequestDTO request) {
		log.info(Constants.INSERINDO_STATUS_DO_PROJETO, request);
		return ProjectStatusAdapter.fromEntityToDTO(
				this.projectStatusRepository.saveAndFlush(ProjectStatusAdapter.fromDTOToEntity(request)));
	}

	public ProjectStatusResponseDTO updateProjectStatus(ProjectStatusRequestDTO request, String id) {
		log.info(Constants.ATUALIZANDO_STATUS_DO_PROJETO, request, id);
		Optional<ProjectStatusEntity> projectStatusOptional = findProjectStatus(id);

		if (projectStatusOptional.isEmpty()) {
			throw new DataNotFoundException(Constants.PROJECT_STATUS + id + Constants.NOT_FOUND);
		}

		projectStatusOptional.get().setStatusDescription(request.getStatusDescription());

		return ProjectStatusAdapter
				.fromEntityToDTO(this.projectStatusRepository.saveAndFlush(projectStatusOptional.get()));
	}

	public void deleteProjectStatus(String id) {
		log.info(Constants.DELETANDO_STATUS_DO_PROJETO + id);
		Optional<ProjectStatusEntity> projectStatusOptional = findProjectStatus(id);

		if (projectStatusOptional.isEmpty()) {
			throw new DataNotFoundException(Constants.PROJECT_STATUS + id + Constants.NOT_FOUND);
		}

		this.projectStatusRepository.delete(projectStatusOptional.get());
	}

	public Page<ProjectStatusEntity> retrieveAllProjectStatuss(Pageable page) {
		log.info(Constants.BUSCANDO_STATUS_DE_PROJETOS);
		return this.projectStatusRepository.findAll(page);
	}

	private Optional<ProjectStatusEntity> findProjectStatus(String id) {
		return projectStatusRepository.findById(Integer.valueOf(id));
	}

}