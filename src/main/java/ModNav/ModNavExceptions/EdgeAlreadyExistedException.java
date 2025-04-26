package ModNav.ModNavExceptions;

public class EdgeAlreadyExistedException extends IllegalArgumentException {
    public EdgeAlreadyExistedException(String message) {
        super(message);
    }

    public EdgeAlreadyExistedException(){
        super("Path already existed!");
    }
}
