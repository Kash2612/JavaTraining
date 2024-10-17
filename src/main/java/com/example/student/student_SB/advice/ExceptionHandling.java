package com.example.student.student_SB.advice;

import com.example.student.student_SB.exception.CourseNotFoundException;
import com.example.student.student_SB.exception.ProjectNotFoundException;
import com.example.student.student_SB.exception.StudentNotFoundException;
import com.example.student.student_SB.repository.ProjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.FieldError;



import java.util.HashMap;
import java.util.Map;

@RestController
@ControllerAdvice
public class ExceptionHandling {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> invalidArgument(MethodArgumentNotValidException ex){
        Map<String,String> errorMap=new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->{
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(StudentNotFoundException.class)
    public Map<String,String> handleBusinessException(StudentNotFoundException ex){
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("errorMessage",ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CourseNotFoundException.class)
    public Map<String,String> handleBusinessException(CourseNotFoundException ex){
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("errorMessage",ex.getMessage());
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ProjectNotFoundException.class)
    public Map<String,String> handleBusinessException(ProjectNotFoundException ex){
        Map<String,String> errorMap=new HashMap<>();
        errorMap.put("errorMessage",ex.getMessage());
        return errorMap;
    }
}
