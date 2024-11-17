package br.com.sanches.gestao.shared.exceptions;

public class ProjectStatusNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2780395740179200129L;

	public ProjectStatusNotFoundException(String message) {
		super(message);
	}
}