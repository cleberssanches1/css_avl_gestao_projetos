package br.com.sanches.gestao.domain.project.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.sanches.gestao.domain.employee.model.dto.EmployeeResponseDTO;
import br.com.sanches.gestao.domain.projectstatus.model.dto.ProjectStatusResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponseDTO {

	private Integer projectCode;
 
	private String projectName;
 
	private LocalDate projectStartDate;
 
	private EmployeeResponseDTO responsibleManager;
 
	private LocalDate forecastEndProject;
 
	private LocalDate realEndProject;
 
	private BigDecimal totalProjectBudgetValue;
 
	private String projectDescription;
	
	private String risk;
 
	private ProjectStatusResponseDTO projectStatus;
	
}