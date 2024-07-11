package com.example.exception;
import java.util.*;
import javax.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
@RestControllerAdvice
public class RestExceptionHandler {
   
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotAuthorisedException.class)
    public ResponseEntity<String> handleNotAuthorised(NotAuthorisedException ex)
    {        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED) // 401
                    .body("");
    }
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateAccountException.class)
    public ResponseEntity<String> handleDuplicateAccountException(DuplicateAccountException ex)
    {        
        return ResponseEntity.status(HttpStatus.CONFLICT) // 409 
                    .body(ex.getMessage());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex)
    {        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST) //400                    
                    .body(ex.getMessage());
                    //.body(null);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResourceNotFoundStatusOkException.class)
    public ResponseEntity<String> handleResourceNotFoundStatusOkException(ResourceNotFoundStatusOkException ex)
    {        
        return ResponseEntity.status(HttpStatus.OK) //200                    
                    //.body(ex.getMessage());
                    .body(null);
    }
    //MethodArgumentNotValidException
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->
            errorMap.put(error.getField(),error.getDefaultMessage()));     
        return ResponseEntity.status(HttpStatus.BAD_REQUEST) //400
                    .body(errorMap);
    }
    //javax.validation.ConstraintViolationException
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex)
    {            
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ex.getConstraintViolations().toArray()[0].toString());
    }
    //DataIntegrityViolationException  
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex)
    {        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleRuntimeException(Exception ex)
    {        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ex.getMessage());
    }
    // @ResponseStatus(HttpStatus.BAD_REQUEST)
    // @ExceptionHandler(Error.class)
    // public ResponseEntity<String> handleError(Error ex)
    // {        
    //     return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    //                 .body(ex.getMessage());
    // }
}
