package br.com.sanches.gestao.domain.position.model.adapter;

import br.com.sanches.gestao.domain.position.model.dto.PositionRequestDTO;
import br.com.sanches.gestao.domain.position.model.dto.PositionResponseDTO;
import br.com.sanches.gestao.domain.position.model.entity.PositionEntity;

public class PositionAdapter {

	private PositionAdapter() {
	}

	public static PositionResponseDTO fromEntityToDTO(PositionEntity entity) {

		return PositionResponseDTO.builder()
				.positionCode(entity.getPositionCode())
				.positionName(entity.getPositionName())
				.positionDescription(entity.getPositionDescription())
				.positionStatus(entity.getPositionStatus())
				.build();
	}

	public static PositionEntity fromDTOToEntity(PositionRequestDTO request) {

		return PositionEntity.builder()
				.positionCode(request.getPositionCode())
				.positionDescription(request.getPositionDescription())
				.positionName(request.getPositionName())
				.positionStatus(request.getPositionStatus())
				.build();
	}

}