package co.digamma.netnote.rating;

import jakarta.annotation.Nonnull;

public interface Rated {

    @Nonnull
    Note getNote();
}
