package com.app.ws.exceptionHandler;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.app.ws.ui.model.response.ErrorMessage;

@ControllerAdvice
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {
		
		/*
		 * Handler methods which are annotated with @ExceptionHandler annotation are allowed to
		 * have very flexible signatures. They may have parameters in arbitrary order.
		 * handleAnyException(WebRequest request,Exception ex)
		 * handleAnyException(Exception ex,WebRequest request) both are valid.
		 * */
		@ExceptionHandler(value={Exception.class}) 
        public ResponseEntity<Object> handleAnyException(Exception ex,WebRequest request){		
			String errorMsg=ex.getLocalizedMessage()!=null?ex.getLocalizedMessage():ex.toString();
			ErrorMessage errorMessage=new ErrorMessage(new Date(),errorMsg);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
		
		}
		@ExceptionHandler(value={NullPointerException.class,UserServiceException.class}) 
        public ResponseEntity<Object> handleSpecificException(Exception ex,WebRequest request){
			System.out.println("handleSpecificException method has been called:-------------");
			String errorMsg=ex.getLocalizedMessage()!=null?ex.getLocalizedMessage():ex.toString();
			ErrorMessage errorMessage=new ErrorMessage(new Date(),errorMsg);
			return new ResponseEntity<>(errorMessage,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
		
		}		
		
}
