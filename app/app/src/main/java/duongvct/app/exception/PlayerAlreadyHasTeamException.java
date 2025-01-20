package duongvct.app.exception;

public class PlayerAlreadyHasTeamException extends RuntimeException{
    public PlayerAlreadyHasTeamException(String message) {
        super(message);
    }
}
