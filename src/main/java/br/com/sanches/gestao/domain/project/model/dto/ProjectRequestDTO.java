package br.com.sanches.gestao.domain.project.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequestDTO {

	private Integer projectCode;
 
	private String projectName;
 
	private LocalDate projectStartDate;
 
	private Integer responsibleManager;
 
	private LocalDate forecastEndProject;
 
	private LocalDate realEndProject;
 
	private BigDecimal totalProjectBudgetValue;
 
	private String projectDescription;
	
	private String risk;
 
	private Integer projectStatus;
	
}