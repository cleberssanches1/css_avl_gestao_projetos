package br.com.sanches.gestao.shared.exceptions;

import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.sanches.gestao.shared.exceptions.dto.MessageExceptionHandler;
import br.com.sanches.gestao.shared.utils.Constants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;

@Slf4j
@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandlingController {
 
	@ResponseBody
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<MessageExceptionHandler> invalidArguments(MethodArgumentNotValidException notValid) {

		BindingResult result = notValid.getBindingResult();

		StringBuilder sb = new StringBuilder(Constants.ERROR_IN_INPUT_FIELDS);

		MessageExceptionHandler error = new MessageExceptionHandler(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
				sb.toString());

		for (FieldError erros : result.getFieldErrors()) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(erros.getField(), erros.getDefaultMessage());
			error.getErros().add(jsonObject);
		}

		log.error(error + Constants.ERROR_IN_INPUT_FIELDS);

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<MessageExceptionHandler> standardError(Exception e, HttpServletRequest request) {

		StringBuilder sb = new StringBuilder(Constants.UNEXPECTED_ERROR);

		MessageExceptionHandler error = new MessageExceptionHandler(LocalDateTime.now(),
				HttpStatus.INTERNAL_SERVER_ERROR.value(), sb.toString());

		String translatedMessage = Constants.UNEXPECTED_ERROR + Constants.DASH + e.getMessage();

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(Constants.UNEXPECTED_ERROR, translatedMessage);
		error.getErros().add(jsonObject);

		log.error(error + Constants.UNEXPECTED_ERROR);

		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<MessageExceptionHandler> errorWhenMappingJson(HttpMessageNotReadableException e,
			HttpServletRequest request) {

		MessageExceptionHandler error = new MessageExceptionHandler(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
				Constants.DATA_ENTRY_ERROR);

		String translatedMessage = Constants.DATA_ENTRY_ERROR + Constants.DASH + e.getMessage();

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(Constants.REQUEST_ERROR, translatedMessage);
		error.getErros().add(jsonObject);

		log.error(error + Constants.DATA_ENTRY_ERROR);

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<MessageExceptionHandler> dataNotFoundError(DataNotFoundException e,
			HttpServletRequest request) {

		StringBuilder sb = new StringBuilder(Constants.INTEGRATION_ERROR);

		MessageExceptionHandler error = new MessageExceptionHandler(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				sb.toString());

		String translatedMessage = Constants.RECORD_NOT_FOUND + Constants.DASH + e.getMessage();

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(Constants.INTEGRATION_ERROR, translatedMessage);
		error.getErros().add(jsonObject);

		log.error(error + Constants.RECORD_NOT_FOUND);

		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ProjectNotSuitableForExclusionException.class)
	public ResponseEntity<MessageExceptionHandler> projectNotSuitableForExclusion(
			ProjectNotSuitableForExclusionException e, HttpServletRequest request) {

		StringBuilder sb = new StringBuilder(Constants.INTEGRATION_ERROR);

		MessageExceptionHandler error = new MessageExceptionHandler(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				sb.toString());

		String translatedMessage = Constants.THE_RECORD_CAN_NOT_BE_DELETED + Constants.DASH + e.getMessage();

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(Constants.INTEGRATION_ERROR, translatedMessage);
		error.getErros().add(jsonObject);

		log.error(error + Constants.THE_RECORD_CAN_NOT_BE_DELETED);

		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(SQLException.class)
	public ResponseEntity<MessageExceptionHandler> sqlError(SQLException e, HttpServletRequest request) {

		StringBuilder sb = new StringBuilder(Constants.DATABASE_INSTABILITY);

		MessageExceptionHandler error = new MessageExceptionHandler(LocalDateTime.now(),
				HttpStatus.INTERNAL_SERVER_ERROR.value(), sb.toString());

		String translatedMessage = Constants.DATABASE_INSTABILITY + Constants.DASH + e.getMessage();

		JSONObject jsonObject = new JSONObject();
		jsonObject.put(Constants.DATABASE_INSTABILITY, translatedMessage);
		error.getErros().add(jsonObject);

		log.error(error + Constants.DATABASE_INSTABILITY);

		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}