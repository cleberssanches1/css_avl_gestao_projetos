package br.com.sanches.gestao.domain.projectstatus.model.adapter;

import br.com.sanches.gestao.domain.projectstatus.model.dto.ProjectStatusRequestDTO;
import br.com.sanches.gestao.domain.projectstatus.model.dto.ProjectStatusResponseDTO;
import br.com.sanches.gestao.domain.projectstatus.model.entity.ProjectStatusEntity;

public class ProjectStatusAdapter {

	private ProjectStatusAdapter() {
	}

	public static ProjectStatusResponseDTO fromEntityToDTO(ProjectStatusEntity entity) {

		return ProjectStatusResponseDTO.builder()
				.statusCode(entity.getStatusCode())
				.statusDescription(entity.getStatusDescription())
				.build();
	}

	public static ProjectStatusEntity fromDTOToEntity(ProjectStatusRequestDTO request) {

		return ProjectStatusEntity.builder()
				.statusCode(request.getStatusCode())
				.statusDescription(request.getStatusDescription())
				.build();
	}
	
}