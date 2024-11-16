package br.com.sanches.gestao.domain.projectstatus.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectStatusResonseDTO {
	private Integer statusCode;

	private String statusDescription;
}