package sa.m.builders.api.client.repo;

import javax.validation.ConstraintViolationException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.data.repository.support.QueryMethodParameterConversionException;
import org.springframework.data.rest.webmvc.support.ExceptionMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author msa
 */
@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    
    // security: do not leak error info! QueryMethodParameterConversionException
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ExceptionMessage> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<ExceptionMessage>(new ExceptionMessage(new Exception("requisição inválida")), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ExceptionMessage> handleConstraintViolationException(ConstraintViolationException ex) {
        return new ResponseEntity<ExceptionMessage>(new ExceptionMessage(new Exception("requisição inválida")), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    
        
    @ExceptionHandler(QueryMethodParameterConversionException.class)
    protected ResponseEntity<ExceptionMessage> handleQueryMethodParameterConversionException(QueryMethodParameterConversionException ex) {
        return new ResponseEntity<ExceptionMessage>(new ExceptionMessage(new Exception("requisição inválida")), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
    
}
