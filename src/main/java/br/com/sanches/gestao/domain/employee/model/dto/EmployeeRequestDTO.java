package br.com.sanches.gestao.domain.employee.model.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDTO {
	private Integer employeeCode;

	private String cpf;

	private Integer position;

	private String employeeName;

	private String employeeStatus;

	private LocalDate startDate;
}
