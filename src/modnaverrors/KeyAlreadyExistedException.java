package modnaverrors;

public class KeyAlreadyExistedException extends IllegalArgumentException {
    public KeyAlreadyExistedException(String message) {
        super(message);
    }

    public KeyAlreadyExistedException(){
        super("Key already existed!");
    }
}
