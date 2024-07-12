package excepciones;

public class ErrorDeAutorizacionException extends RuntimeException {
    public ErrorDeAutorizacionException(String mensaje) {
        super(mensaje);
    }
}
