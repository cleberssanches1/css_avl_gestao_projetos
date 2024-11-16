package br.com.sanches.gestao.shared.exceptions;

public class EmployeeNotFoundException extends RuntimeException {
 
	private static final long serialVersionUID = -2780395740179200129L;

	public EmployeeNotFoundException(String message) {
		super(message);
	}
}
