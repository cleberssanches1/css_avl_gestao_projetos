package br.com.sanches.gestao.shared.exceptions;

public class DataNotFoundException extends RuntimeException {
 
	private static final long serialVersionUID = -2780395740179200129L;

	public DataNotFoundException(String message) {
		super(message);
	}
}
