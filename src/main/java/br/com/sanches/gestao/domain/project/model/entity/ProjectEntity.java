package br.com.sanches.gestao.domain.project.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import br.com.sanches.gestao.domain.employee.model.entity.EmployeeEntity;
import br.com.sanches.gestao.domain.projectstatus.model.entity.ProjectStatusEntity;
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
@Table(name = "TB_PROJETO", schema = "gestao_projetos")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectEntity {

	@Id
	@Column(name = "cd_projeto")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer projectCode;

	@Column(name = "nm_projeto")
	private String projectName;

	@Column(name = "dt_inicio_projeto")
	private LocalDate projectStartDate;

	@ManyToOne
	@JoinColumn(name = "cd_gerente_responsavel", referencedColumnName = "cd_colaborador")
	private EmployeeEntity responsibleManager;

	@Column(name = "dt_previsao_termino_projeto")
	private LocalDate forecastEndProject;

	@Column(name = "dt_real_termino_projeto")
	private LocalDate realEndProject;

	@Column(name = "vr_orcamento_total")
	private BigDecimal totalProjectBudgetValue;

	@Column(name = "ds_projeto")
	private String projectDescription;

	@ManyToOne
	@JoinColumn(name = "cd_status", referencedColumnName = "cd_status")
	private ProjectStatusEntity projectStatus;
	
	@Column(name = "ds_risco")
	private String risk;

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