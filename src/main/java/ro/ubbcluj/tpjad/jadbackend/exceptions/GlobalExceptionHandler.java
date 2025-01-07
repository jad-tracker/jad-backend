package ro.ubbcluj.tpjad.jadbackend.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ro.ubbcluj.tpjad.jadbackend.exceptions.auth.ExistingUserException;
import ro.ubbcluj.tpjad.jadbackend.exceptions.auth.PasswordMismatchException;
import ro.ubbcluj.tpjad.jadbackend.exceptions.auth.UserNotFoundException;
import ro.ubbcluj.tpjad.jadbackend.exceptions.entity.EntityNotFoundException;
import ro.ubbcluj.tpjad.jadbackend.exceptions.entity.InvalidEntityException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({
        EntityNotFoundException.class,
        UserNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorMessageResponse handleNotFoundExceptions(RuntimeException ex) {
        return new ErrorMessageResponse(ex.getMessage(), null);
    }

    @ExceptionHandler(InvalidEntityException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorMessageResponse handleInvalidEntityException(RuntimeException ex) {
        return new ErrorMessageResponse(ex.getMessage(), null);
    }

    @ExceptionHandler(ExistingUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorMessageResponse handleExistingUserException(RuntimeException ex) {
        return new ErrorMessageResponse(ex.getMessage(), null);
    }

    @ExceptionHandler(PasswordMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody ErrorMessageResponse handlePasswordMismatchException(RuntimeException ex) {
        return new ErrorMessageResponse(ex.getMessage(), null);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody ErrorMessageResponse handleBadCredentialsException(RuntimeException ex) {
        return new ErrorMessageResponse("The username or password is incorrect", ex.getMessage());
    }

    @ExceptionHandler(AccountStatusException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public @ResponseBody ErrorMessageResponse handleAccountStatusException(RuntimeException ex) {
        return new ErrorMessageResponse("The account is locked", ex.getMessage());
    }

    @ExceptionHandler({AccessDeniedException.class, InsufficientAuthenticationException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public @ResponseBody ErrorMessageResponse handleAccessDeniedInsufficientAuthenticationException(RuntimeException ex) {
        return new ErrorMessageResponse("You are not authorized to access this resource", ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody ErrorMessageResponse handleAuthenticationException(RuntimeException ex) {
        return new ErrorMessageResponse("Authentication error", ex.getMessage());
    }

    @ExceptionHandler({SignatureException.class, MalformedJwtException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody ErrorMessageResponse handleSignatureMalformedJwtException(RuntimeException ex) {
        return new ErrorMessageResponse("The JWT signature is invalid", ex.getMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public @ResponseBody ErrorMessageResponse handleExpiredJwtException(RuntimeException ex) {
        return new ErrorMessageResponse("The JWT has expired", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessageResponse> handleException(Exception ex) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        return new ResponseEntity<>(
            new ErrorMessageResponse(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage()),
            headers,
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
