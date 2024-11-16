package br.com.sanches.gestao.domain.position.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionResponseDTO {

	private Integer positionCode;
	
	private String positionName;

	private String positionDescription;

	private String positionStatus;

}