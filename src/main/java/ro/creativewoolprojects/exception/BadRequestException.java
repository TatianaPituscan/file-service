package ro.creativewoolprojects.exception;

public class BadRequestException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Bad Request";
    }
}
