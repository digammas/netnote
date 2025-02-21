package co.digamma.netnote.graphql;

import co.digamma.netnote.rating.Rating;

public record RatingOutput(
        String id,
        int note,
        String comment
) {

    public static RatingOutput from(Rating rating) {
        return new RatingOutput(
                rating.getId(),
                rating.getNote(),
                rating.getComment()
        );
    }
}
