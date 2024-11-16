package br.com.sanches.gestao.shared.exceptions;

public class PositionNotFoundException extends RuntimeException {
 
	private static final long serialVersionUID = -2780395740179200129L;

	public PositionNotFoundException(String message) {
		super(message);
	}
}
