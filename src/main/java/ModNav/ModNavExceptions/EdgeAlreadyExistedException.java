package ModNav.ModNavExceptions;

public class PathAlreadyExistedException extends IllegalArgumentException {
    public PathAlreadyExistedException(String message) {
        super(message);
    }

    public PathAlreadyExistedException(){
        super("Path already existed!");
    }
}
