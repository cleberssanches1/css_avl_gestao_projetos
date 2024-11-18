package br.com.sanches.gestao.shared.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusEnum {

	ATIVO("A", "INATIVO"),
	INATIVO("I", "ATIVO");
	 
	private static final String INVALID_STATUS = "Stauts inválido";
	
	private final String abbreviation;
	private final String description;

	public static StatusEnum fromString(String value) {
		for (StatusEnum status : StatusEnum.values()) {
			if (status.abbreviation.equalsIgnoreCase(value) || status.description.equalsIgnoreCase(value)) {
				return status;
			}
		}
		throw new IllegalArgumentException(INVALID_STATUS + value);
	}

	public static StatusEnum parse(String value) {
		for (StatusEnum status : StatusEnum.values()) {
			if (status.abbreviation.equalsIgnoreCase(value) || status.description.equalsIgnoreCase(value)) {
				return status;
			}
		}
		return null;
	}

}