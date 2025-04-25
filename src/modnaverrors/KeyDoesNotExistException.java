package modnaverrors;

public class KeyDoesNotExistException extends IllegalArgumentException {
    public KeyDoesNotExistException(String message) {
        super(message);
    }

    public KeyDoesNotExistException(){
        super("Specified key does not existed!");
    }
}
