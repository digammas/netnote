package co.digamma.netnote.graphql;

import co.digamma.netnote.common.Page;
import co.digamma.netnote.common.Pagination;
import co.digamma.netnote.rating.Rating;
import co.digamma.netnote.rating.RatingService;
import graphql.relay.Connection;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;


@Controller
public class RatingController {

    private final RatingService ratingService;
    private final ConnectionMapper<Rating, RatingOutput> ratingMapper = RatingOutput::from;


    public RatingController(
            RatingService ratingService
    ) {
        this.ratingService = ratingService;
    }

    @MutationMapping
    public boolean rate(RatingInput input) {
        this.ratingService.rate(input.toRequest());
        return true;
    }

    @QueryMapping
    public NoteOutput domainNote(String url) {
        return this.ratingService.retrieveNoteForDomain(url)
                .map(NoteOutput::from)
                .orElse(NoteOutput.UNKNOWN);
    }

    @QueryMapping
    public NoteOutput linkNote(String url) {
        return this.ratingService.retrieveNoteForLink(url)
                .map(NoteOutput::from)
                .orElse(NoteOutput.UNKNOWN);
    }

    @QueryMapping
    public Connection<RatingOutput> domainRatings(String url, CursorSubrange subrange) {
        Pagination pagination = subrange.toPagination();
        Page<Rating> page = this.ratingService.retrieveRatingForDomain(url, pagination);
        return this.ratingMapper.from(page);
    }

    @QueryMapping
    public Connection<RatingOutput> linkRatings(String url, CursorSubrange subrange) {
        Pagination pagination = subrange.toPagination();
        Page<Rating> page = this.ratingService.retrieveRatingForLink(url, pagination);
        return this.ratingMapper.from(page);
    }
}
