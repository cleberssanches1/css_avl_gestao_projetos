package br.com.sanches.gestao.domain.employee.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.sanches.gestao.domain.position.model.entity.PositionEntity;
import br.com.sanches.gestao.shared.utils.Utils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "TB_COLABORADOR", schema = "gestao_projetos")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntity {
	@Id
	@Column(name = "cd_colaborador")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer employeeCode;

	@Column(name = "cd_cpf")
	private String cpf;
 
	@ManyToOne
    @JoinColumn(name = "cd_cargo", referencedColumnName = "cd_cargo")
    private PositionEntity position;
	
	@Column(name = "nm_colaborador")
	private String employeeName;

	@Column(name = "st_colaborador")
	private String employeeStatus;

	@Column(name = "dt_inicio_colaborador")
	private LocalDate startDate;

	@Column(name = "ts_modificacao")
	private LocalDateTime updateDate;

	@PrePersist
	public void prePersist() {
		this.updateDate = Utils.getInstant();
	}

	@PreUpdate
	public void preUpdate() {
		this.updateDate = Utils.getInstant();
	}
}