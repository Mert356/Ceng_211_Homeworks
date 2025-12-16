package src.main.exceptions;
public class EmptyBoxException extends Exception{
    public EmptyBoxException(String msg){
        super(msg);
    }
    public EmptyBoxException(){
        super("EXCEPTION: Box is empty.");
    }
}
