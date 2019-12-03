package com.stelo.simpleops.common.exception;

import com.stelo.simpleops.common.dto.BaseResponse;
import com.stelo.simpleops.common.enums.BusinessError;
import com.stelo.simpleops.common.enums.CustomHttpStatus;
import com.stelo.simpleops.utils.LanguageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Resource
    private LanguageHelper languageHelper;

    private static final Integer ARGUMENT_NOT_VALID_CODE = 100005;

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<BaseResponse> process(Throwable exception) {
        loggerException(exception);
        String message = languageHelper.getMessage("system.error");
        BaseResponse baseResponse = BaseResponse.newBuilder()
                .code(BusinessError.GENERAL_SERVER_ERR.getCode())
                .httpStatus(CustomHttpStatus.INTERNAL_SERVER_ERROR)
                .message(message).build();
        return ResponseEntity.status(baseResponse.getHttpStatus().value()).body(baseResponse);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseEntity<BaseResponse> process(BusinessException exception) {
        return generateBaseResponse(exception);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<BaseResponse> process(AuthenticationException exception) {
        BusinessException result = new BusinessException(
                exception.getMessage(),
                BusinessError.CLIENT_PARAM_ERR
        );
        return generateBaseResponse(result);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<BaseResponse> process(AccessDeniedException exception) {
        BusinessException result = new BusinessException(
                exception.getMessage(),
                BusinessError.GENERAL_FORBID_ERR
        );
        return generateBaseResponse(result);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public ResponseEntity<BaseResponse> processHttpRequestMethodNotSupportedException() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

    @ExceptionHandler(HttpMediaTypeException.class)
    @ResponseBody
    public ResponseEntity<BaseResponse> processHttpMediaTypeException() {
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<BaseResponse> process(HttpMessageNotReadableException exception) {
        loggerException(exception);
        String message = languageHelper.getMessage("system.error.param");
        BaseResponse baseResponse = BaseResponse.newBuilder()
                .code(BusinessError.CLIENT_PARAM_ERR.getCode())
                .httpStatus(CustomHttpStatus.BAD_REQUEST)
                .message(message).build();
        return ResponseEntity.status(baseResponse.getHttpStatus().value()).body(baseResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<BaseResponse> process(MethodArgumentNotValidException exception) {
        return this.processBindingResult(exception.getBindingResult());
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseEntity<BaseResponse> process(BindException exception) {
        return this.processBindingResult(exception.getBindingResult());
    }

    private ResponseEntity<BaseResponse> processBindingResult(BindingResult bindingResult) {
        if (bindingResult.getErrorCount() > 0) {
            List<ObjectError> list = bindingResult.getAllErrors();
            StringBuilder sb = new StringBuilder();
            for (ObjectError tmp : list) {
                if (tmp instanceof FieldError) {
                    FieldError fieldError = (FieldError) tmp;
                    sb.append(languageHelper
                            .getMessage(fieldError.getDefaultMessage(), fieldError.getArguments()));
                    sb.append("\n");
                }
            }
            return generateBaseResponse(sb.toString(), CustomHttpStatus.BAD_REQUEST,
                    ARGUMENT_NOT_VALID_CODE);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    private void loggerException(Throwable e) {
        LOGGER.error("exception process", e);
    }

    private ResponseEntity<BaseResponse> generateBaseResponse(BusinessException exception) {
        String message = languageHelper
                .getMessage(exception.getMessage(), exception.getParameters());
        BaseResponse baseResponse = BaseResponse.newBuilder()
                .code(exception.getBusinessError().getCode())
                .httpStatus(exception.getBusinessError().getHttpStatus())
                .message(message).build();
        return ResponseEntity.status(baseResponse.getHttpStatus().value()).body(baseResponse);
    }

    private ResponseEntity<BaseResponse> generateBaseResponse(String msg, CustomHttpStatus status,
                                                              int code) {
        BaseResponse baseResponse = BaseResponse.newBuilder()
                .code(code)
                .httpStatus(status)
                .message(msg).build();
        return ResponseEntity.status(baseResponse.getHttpStatus().value()).body(baseResponse);
    }
}


