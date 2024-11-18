package br.com.sanches.gestao.shared.exceptions;
 
public class ProjectNotSuitableForExclusionException extends RuntimeException {
	 
	private static final long serialVersionUID = -2780395740179200129L;

	public ProjectNotSuitableForExclusionException(String message) {
		super(message);
	}
}