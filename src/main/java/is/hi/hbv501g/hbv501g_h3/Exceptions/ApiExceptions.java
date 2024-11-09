package is.hi.hbv501g.hbv501g_h3.Exceptions;

import org.springframework.http.HttpStatus;

public class ApiExceptions {

    // Base API exception class
    public static class ApiException extends RuntimeException {
        private final HttpStatus status;

        public ApiException(String message, HttpStatus status) {
            super(message);
            this.status = status;
        }

        public HttpStatus getStatus() {
            return status;
        }
    }

    public static class PatternNotFoundException extends ApiException {
        public PatternNotFoundException(Long id) {
            super("Pattern not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }
    public static class UserNotFoundException extends ApiException {
        public UserNotFoundException(Long id) {
            super("User not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    public static class UsernameNotFoundException extends ApiException {
        public UsernameNotFoundException(String username) {
            super("User not found with username: " + username, HttpStatus.NOT_FOUND);
        }
    }

    public static class UserAlreadyExists extends ApiException {
        public UserAlreadyExists() {
            super("User with that username already exists.", HttpStatus.BAD_REQUEST);
        }
    }

    public static class InvalidCredentialsException extends ApiException {
        public InvalidCredentialsException() {
            super("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }

    public static class InvalidTokenException extends ApiException {
        public InvalidTokenException() {
            super("Invalid or expired token", HttpStatus.UNAUTHORIZED);
        }
    }

    public static class UserInvalidAccess extends ApiException {
        public UserInvalidAccess(String message) {
            super(message, HttpStatus.UNAUTHORIZED);
        }
    }

    public static class ProfilePictureUploadException extends ApiException {
        public ProfilePictureUploadException() {
            super("Failed to upload profile picture", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
