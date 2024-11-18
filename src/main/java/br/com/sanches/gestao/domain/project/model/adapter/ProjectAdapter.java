package br.com.sanches.gestao.domain.project.model.adapter;

import br.com.sanches.gestao.domain.employee.model.adapter.EmployeeAdapter;
import br.com.sanches.gestao.domain.employee.model.entity.EmployeeEntity;
import br.com.sanches.gestao.domain.project.model.dto.ProjectRequestDTO;
import br.com.sanches.gestao.domain.project.model.dto.ProjectResponseDTO;
import br.com.sanches.gestao.domain.project.model.entity.ProjectEntity;
import br.com.sanches.gestao.domain.projectstatus.model.adapter.ProjectStatusAdapter;
import br.com.sanches.gestao.domain.projectstatus.model.entity.ProjectStatusEntity;

public class ProjectAdapter {

	private ProjectAdapter() {
	}

	public static ProjectResponseDTO fromEntityToDTO(ProjectEntity entity) {

		return ProjectResponseDTO.builder()
				.forecastEndProject(entity.getForecastEndProject())
				.projectCode(entity.getProjectCode())
				.projectDescription(entity.getProjectDescription())
				.projectName(entity.getProjectName())
				.projectStartDate(entity.getProjectStartDate())
				.projectStatus(ProjectStatusAdapter.fromEntityToDTO(entity.getProjectStatus()))
				.realEndProject(entity.getRealEndProject())
				.responsibleManager(EmployeeAdapter.fromEntityToDTO(entity.getResponsibleManager()))
				.risk(entity.getRisk())
				.totalProjectBudgetValue(entity.getTotalProjectBudgetValue())
				.build();
	}

	public static ProjectEntity fromDTOToEntity(ProjectRequestDTO request) {

		ProjectStatusEntity status = ProjectStatusEntity.builder().statusCode(request.getProjectStatus()).build();

		EmployeeEntity employee = EmployeeEntity.builder().employeeCode(request.getResponsibleManager()).build();

		return ProjectEntity.builder()
				.forecastEndProject(request.getForecastEndProject())
				.projectCode(request.getProjectCode())
				.projectDescription(request.getProjectDescription())
				.projectName(request.getProjectName())
				.projectStartDate(request.getProjectStartDate())
				.projectStatus(status)
				.realEndProject(request.getRealEndProject())
				.responsibleManager(employee)
				.risk(request.getRisk())
				.totalProjectBudgetValue(request.getTotalProjectBudgetValue())
				.build();
	}

}