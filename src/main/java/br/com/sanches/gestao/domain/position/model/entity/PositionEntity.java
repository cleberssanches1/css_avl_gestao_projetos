package br.com.sanches.gestao.domain.position.model.entity;

import java.time.LocalDateTime;

import br.com.sanches.gestao.shared.utils.Utils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "TB_CARGO", schema = "gestao_projetos")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionEntity {

	@Id
	@Column(name = "cd_cargo")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer positionCode;
	
	@Column(name = "nm_cargo")
	private String positionName;
	 
	@Column(name = "ds_cargo")
	private String positionDescription;

	@Column(name = "st_cargo")
	private String positionStatus;

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
