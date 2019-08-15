package ro.creativewoolprojects.exception;

public class NotFoundException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Not Found";
    }

}
