package excepciones;

public class ErrorDeValidacionException extends RuntimeException {
    public ErrorDeValidacionException(String mensaje) {
        super(mensaje);
    }
}
