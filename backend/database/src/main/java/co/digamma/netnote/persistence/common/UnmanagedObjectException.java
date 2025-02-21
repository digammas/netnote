package co.digamma.netnote.persistence.common;

public class UnmanagedObjectException extends RuntimeException {

    public UnmanagedObjectException(String message) {
        super(message);
    }

    public static UnmanagedObjectException of(Object object) {
        return new UnmanagedObjectException("Unmanaged object of type %s.".formatted(object.getClass().getName()));
    }
}
