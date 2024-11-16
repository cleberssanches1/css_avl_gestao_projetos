package br.com.sanches.gestao.domain.employee.model.adapter;

import br.com.sanches.gestao.domain.employee.model.dto.EmployeeRequestDTO;
import br.com.sanches.gestao.domain.employee.model.dto.EmployeeResponseDTO;
import br.com.sanches.gestao.domain.employee.model.entity.EmployeeEntity;
import br.com.sanches.gestao.domain.position.model.dto.PositionResponseDTO;
import br.com.sanches.gestao.domain.position.model.entity.PositionEntity;

public class EmployeeAdapter {

	private EmployeeAdapter() {
	}

	public static EmployeeResponseDTO fromEntityToDTO(EmployeeEntity entity) {

		PositionResponseDTO position = PositionResponseDTO.builder()
				.positionCode(entity.getPosition().getPositionCode())
				.positionName(entity.getPosition().getPositionName())
				.positionDescription(entity.getPosition().getPositionDescription())
				.build();

		return EmployeeResponseDTO.builder()
				.employeeCode(entity.getEmployeeCode())
				.cpf(entity.getCpf())
				.position(position)
				.employeeName(entity.getEmployeeName())
				.employeeStatus(entity.getEmployeeStatus())
				.startDate(entity.getStartDate())
				.build();
	}

	public static EmployeeEntity fromDTOToEntity(EmployeeRequestDTO request) {

		PositionEntity position = PositionEntity.builder().positionCode(request.getPosition()).build();

		return EmployeeEntity.builder()
				.cpf(request.getCpf())
				.employeeCode(request.getEmployeeCode())
				.employeeName(request.getEmployeeName())
				.employeeStatus(request.getEmployeeStatus())
				.position(position)
				.startDate(request.getStartDate()).build();

	}

}