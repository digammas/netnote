package co.digamma.netnote.common;

import java.util.Optional;

public interface Repository<T extends Identifiable<I>, I> {

    Optional<? extends T> read(I id);
    T write(T entity);
}
