package co.digamma.netnote.graphql;

import co.digamma.netnote.rating.RatingRequest;
import jakarta.annotation.Nonnull;

public record RatingInput(
        @Nonnull String url,
        int note,
        String comment
) {

    RatingRequest toRequest() {
        return new RatingRequest(url, note, comment);
    }
}
