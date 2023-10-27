package com.amway.commerce.exception;

import com.amway.commerce.http.CommonHttpStatus;
import com.amway.commerce.http.ResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(value = CommonBusinessException.class)
    @ResponseBody
    public ResponseEntity<ResultResponse> handleException(CommonBusinessException e) {
        return handleException(e, e.getErrorCode(), e.getErrorParams(), e.getErrorMessage(), CommonHttpStatus.CLIENT_BUSINESS_ERROR);
    }

    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public ResponseEntity<ResultResponse> handleException(BindException e) {
        String errorMsg = e.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining());
        return handleException(e,CommonErrorCode.CLIENT_PARAMETER_PARSE_ERROR,null,errorMsg, CommonHttpStatus.CLIENT_PARAMETER_PARSE_ERROR);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ResultResponse> handleException(ConstraintViolationException e) {
        String errorMsg = e.getConstraintViolations().stream().map(ConstraintViolation::getMessage).collect(Collectors.joining());
        return handleException(e,CommonErrorCode.CLIENT_PARAMETER_PARSE_ERROR,null,errorMsg, CommonHttpStatus.CLIENT_PARAMETER_PARSE_ERROR);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ResultResponse> handleException(MethodArgumentNotValidException e) {
        String errorMsg = e.getBindingResult().getAllErrors().stream().map(error->{
            FieldError fError = (FieldError)error;
            return fError.getObjectName()+"."+fError.getField()+":"+error.getDefaultMessage()+";";
        }).collect(Collectors.joining());
        return handleException(e,CommonErrorCode.CLIENT_PARAMETER_PARSE_ERROR,null,errorMsg, CommonHttpStatus.CLIENT_PARAMETER_PARSE_ERROR);
    }

    /**
     *
     * @param e Media格式不正确
     * @return
     */
    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<ResultResponse> handleException(HttpMediaTypeNotSupportedException e) {
        return handleException(e,CommonErrorCode.CLIENT_UNSUPPORTED_MEDIA_TYPE,null,e.getMessage(), CommonHttpStatus.CLIENT_UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * 后台是Post，前台用get方式提交
     * @param e
     * @return
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<ResultResponse> handleException(HttpRequestMethodNotSupportedException e) {
        return handleException(e,CommonErrorCode.CLIENT_METHOD_ERROR,null,e.getMessage(), CommonHttpStatus.CLIENT_METHOD_ERROR);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<ResultResponse> handleException(HttpMessageNotReadableException e) {
        return handleException(e,CommonErrorCode.CLIENT_JSON_PARSE_ERROR,null,e.getMessage(), CommonHttpStatus.CLIENT_JSON_PARSE_ERROR);
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public ResponseEntity<ResultResponse> handleException(RuntimeException e) {
        return handleException(e,CommonErrorCode.SYSTEM_RUNTIME_ERROR,null,CommonHttpStatus.SYSTEM_RUNTIME_ERROR.getReasonPhrase(), CommonHttpStatus.SYSTEM_RUNTIME_ERROR);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResponseEntity<ResultResponse> handleException(Exception e) {
        return handleException(e,CommonErrorCode.SYSTEM_OTHER_ERROR,null,e.getMessage(), CommonHttpStatus.SYSTEM_OTHER_ERROR);
    }

    private ResponseEntity<ResultResponse> handleException(Exception e, CommonErrorCode errorCode, List<String> errorParams, String message, CommonHttpStatus status) {
        return handleException(e,errorCode.getCode(),errorParams,message,status);
    }

    private ResponseEntity<ResultResponse> handleException(Exception e, String errorCode, List<String> errorParams, String message, CommonHttpStatus status) {
        ResultResponse resultResponse = new ResultResponse();

        resultResponse.setCode(errorCode);
        resultResponse.setMessage(message);
        resultResponse.setMsgData(errorParams);
        resultResponse.setSuccess(false);
        log.error("[Exception Handler][{}] ", e.getMessage(), e);

        return ResponseEntity.status(status.value()).body(resultResponse);
    }


}
