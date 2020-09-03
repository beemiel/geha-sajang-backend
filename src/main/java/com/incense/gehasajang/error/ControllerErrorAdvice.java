package com.incense.gehasajang.error;

import com.incense.gehasajang.exception.AccessDeniedException;
import com.incense.gehasajang.exception.CannotConvertException;
import com.incense.gehasajang.exception.FailToAuthenticationException;
import com.incense.gehasajang.exception.NotFoundDataException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerErrorAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundDataException.class)
    public ErrorResponse handleNotFoundData(NotFoundDataException e) {
        return ErrorResponse.buildError(e.getErrorCode());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        List<ErrorResponse.FieldError> fieldErrors = getFieldErrors(e.getBindingResult());
        return buildValidationError(ErrorCode.INPUT_VALUE_INVALID, fieldErrors);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ErrorResponse handleBindException(BindException e) {
        List<ErrorResponse.FieldError> fieldErrors = getFieldErrors(e.getBindingResult());
        return buildValidationError(ErrorCode.INPUT_VALUE_INVALID, fieldErrors);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ErrorResponse handleFileSizeLimitException() {
        return ErrorResponse.buildError(ErrorCode.FILE_SIZE_LIMIT_EXCEED);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(CannotConvertException.class)
    public ErrorResponse handleCannotConvertException() {
        return ErrorResponse.buildError(ErrorCode.CANNOT_CONVERT_FILE);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorResponse handleDuplicateException() {
        return ErrorResponse.buildError(ErrorCode.INPUT_VALUE_INVALID);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponse handleAccessDenied(AccessDeniedException e) {
        return ErrorResponse.buildError(e.getErrorCode());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(FailToAuthenticationException.class)
    public ErrorResponse handleFailToAuth(FailToAuthenticationException e) {
        return ErrorResponse.buildError(e.getErrorCode());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse handleIllegalArgument(IllegalArgumentException e) {
        return ErrorResponse.buildError(ErrorCode.CONSTRUCTOR_VALUE_INVALID);
    }

    private List<ErrorResponse.FieldError> getFieldErrors(BindingResult bindingResult) {
        List<FieldError> errors = bindingResult.getFieldErrors();
        return errors.stream()
                .map(
                        error -> ErrorResponse.FieldError.builder()
                                .reason(error.getDefaultMessage())
                                .field(error.getField())
                                .value((String) error.getRejectedValue())
                                .build()
                )
                .collect(Collectors.toList());
    }

    private ErrorResponse buildValidationError(ErrorCode errorCode, List<ErrorResponse.FieldError> errors) {
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .status(errorCode.getStatus())
                .message(errorCode.getMessage())
                .errors(errors)
                .build();
    }

}
