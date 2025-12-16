package src.main.exceptions;
public class BoxAlreadyFixedException extends Exception{
    public BoxAlreadyFixedException(){
        super("EXCEPTION: Box is already fixed.");
    }

    public BoxAlreadyFixedException(String msg){
        super(msg);
    }
}
