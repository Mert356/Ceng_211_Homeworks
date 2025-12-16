package src.main.exceptions;
public class UnmovableFixedBoxException extends Exception {
    public UnmovableFixedBoxException() {
        super("EXCEPTION: The fixed box cannot be moved.");
    }
    public UnmovableFixedBoxException(String msg){
        super(msg);
    }
}
