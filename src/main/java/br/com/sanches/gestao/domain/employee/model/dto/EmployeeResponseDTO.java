package br.com.sanches.gestao.domain.employee.model.dto;

import java.time.LocalDate;

import br.com.sanches.gestao.domain.position.model.dto.PositionResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDTO {

	private Integer employeeCode;

	private String cpf;

	private PositionResponseDTO position;

	private String employeeName;

	private String employeeStatus;
 
	private LocalDate startDate;

}