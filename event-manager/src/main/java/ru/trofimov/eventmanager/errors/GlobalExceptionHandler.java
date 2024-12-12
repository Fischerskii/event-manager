package ru.trofimov.eventmanager.errors;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.trofimov.common.errors.ErrorMessageResponse;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessageResponse> handleNotFoundException(EntityNotFoundException e) {
        log.error("Global. Entity not found exception handler", e);

        ErrorMessageResponse entityNotFound = new ErrorMessageResponse(
                "Сущность не найдена",
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(entityNotFound);
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<ErrorMessageResponse> handleValidationException(Exception e) {
        log.error("Global. Method argument not valid exception or Illegal argument exception handler", e);

        String detailedMessage = e instanceof MethodArgumentNotValidException
                ? constructMethodArgumentNotValidMessage((MethodArgumentNotValidException) e)
                : e.getMessage();

        ErrorMessageResponse validationException = new ErrorMessageResponse(
                "Некорректный запрос",
                detailedMessage,
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationException);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorMessageResponse> handleAuthenticationException(AuthorizationDeniedException e) {
        log.error("Global. Authentication exception", e);
        ErrorMessageResponse errorDto = new ErrorMessageResponse(
                "Forbidden",
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDto);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorMessageResponse> handleNotFoundException(Exception e) {
        log.error("Global. All other exception handler", e);

        ErrorMessageResponse serverError = new ErrorMessageResponse(
                "Внутренняя ошибка сервера",
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(serverError);
    }

    private static String constructMethodArgumentNotValidMessage(MethodArgumentNotValidException e) {
        return e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
    }
}
