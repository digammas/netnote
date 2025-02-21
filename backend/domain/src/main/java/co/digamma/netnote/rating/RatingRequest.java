package co.digamma.netnote.rating;

import jakarta.annotation.Nonnull;

public record RatingRequest(
        @Nonnull String url,
        int note,
        String comment
) {}
