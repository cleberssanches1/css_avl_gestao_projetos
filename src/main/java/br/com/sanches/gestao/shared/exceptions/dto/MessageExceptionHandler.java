package br.com.sanches.gestao.shared.exceptions.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import net.minidev.json.JSONObject;

@Data
public class MessageExceptionHandler {

    private Integer status;	
	private String mensagem;
	private LocalDateTime timestamp;
	private List<JSONObject> erros = new ArrayList<>();
	
	public MessageExceptionHandler(LocalDateTime dataHora, Integer status, String mensagem) {
		super();
		this.status = status;
		this.mensagem = mensagem;
		this.timestamp = dataHora;
	}

	public MessageExceptionHandler(LocalDateTime dataHora, String mensagem) {
		super();
		this.mensagem = mensagem;
		this.timestamp = dataHora;
	}
	
	public MessageExceptionHandler() {
		super();
	}
	 
}
