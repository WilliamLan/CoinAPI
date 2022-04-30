package org.coin.component;

import org.coin.enums.ReturnStatus;
import org.coin.exception.APIException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {
 
	/**
	 * catch common exception
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<APIException> handleCommonException(Exception ex) {
		APIException generic = new APIException(ReturnStatus.ERROR_SERVER_INTERNAL.code,
				ReturnStatus.ERROR_SERVER_INTERNAL.msg);
		log.error("handle common exception", ex);
		return new ResponseEntity<APIException>(generic, HttpStatus.OK);
	}

	/**
	 * catch api exception
	 */
	@ExceptionHandler(APIException.class)
	public ResponseEntity<APIException> handleAPIException(APIException ex) {
		log.error("handle api exception", ex);
		return new ResponseEntity<APIException>(ex, HttpStatus.OK);
	}

}
