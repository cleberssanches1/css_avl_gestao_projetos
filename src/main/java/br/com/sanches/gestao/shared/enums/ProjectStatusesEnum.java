package br.com.sanches.gestao.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProjectStatusesEnum {

	EM_ANALISE(1, "EM ANÁLISE"),
	ANALISE_REALIZADA(2, "ANÁLISE REALIZADA"),
	ANALISE_APROVADA(3, "ANÁLISE APROVADA"),
	INICIADO(4, "INICIADO"),
	PLANEJADO(5, "PLANEJADO"),
	EM_ANDAMENTO(6, "EM ANDAMENTO"),
	ENCERRADO(7, "ENCERRADO"),
	CANCELADO(8, "CANCELADO");

	private static final String INVALID_STATUS = "Stauts inválido";

	private final Integer code;
	private final String description;

	public static ProjectStatusesEnum fromString(Integer value) {
		for (ProjectStatusesEnum status : ProjectStatusesEnum.values()) {
			if (status.code.equals(value)) {
				return status;
			}
		}
		throw new IllegalArgumentException(INVALID_STATUS + value);
	}

	public static ProjectStatusesEnum parse(String value) {
		for (ProjectStatusesEnum status : ProjectStatusesEnum.values()) {
			if (status.description.equalsIgnoreCase(value)) {
				return status;
			}
		}
		return null;
	}

}